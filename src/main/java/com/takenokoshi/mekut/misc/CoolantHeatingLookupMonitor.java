package com.takenokoshi.mekut.misc;

import java.util.function.BooleanSupplier;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributes;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.CooledCoolant;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.HeatUtils;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.fluids.FluidStack;

public final class CoolantHeatingLookupMonitor {

    private final MergedTank inputTank;
    private final IChemicalTank outputTank;
    private final BooleanSupplier externalActive;

    public double boilEfficiency;
    private long lastBoilRate;
    private ChemicalStack heatedCoolantStack = ChemicalStack.EMPTY;
    private double thermalEnthalpy = 0.0d;
    private double conductivity = 0.0d;
    private boolean isActive = false;
    private MergedTank.CurrentType currentType = MergedTank.CurrentType.EMPTY;

    public CoolantHeatingLookupMonitor(MergedTank inputTank, IChemicalTank outputTank, BooleanSupplier externalActive) {
        this.inputTank = inputTank;
        this.outputTank = outputTank;
        this.externalActive = externalActive;
    }

    public void onInputTankChanged() {
        currentType = inputTank.getCurrentType();
        if (currentType == MergedTank.CurrentType.EMPTY) {
            clearCache();
            return;
        } else if (currentType == MergedTank.CurrentType.CHEMICAL) {
            ChemicalStack stack = inputTank.getChemicalTank().getStack();
            CooledCoolant cooledCoolant = stack.getData(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant());
            if (cooledCoolant == null) {// - 1.22: Remove this handling of legacy data
                ChemicalAttributes.CooledCoolant legacyCoolant = stack
                        .getLegacy(ChemicalAttributes.CooledCoolant.class);
                if (legacyCoolant != null) {
                    cooledCoolant = legacyCoolant.asModern();
                }
            }
            if (cooledCoolant == null) {
                clearCache();
                return;
            }
            heatedCoolantStack = cooledCoolant.heat(1);
            thermalEnthalpy = cooledCoolant.thermalEnthalpy();
            conductivity = cooledCoolant.conductivity();
            refreshActiveState();
        } else {
            FluidStack fluidStack = inputTank.getFluidTank().getFluid();
            if (!fluidStack.is(FluidTags.WATER)) {
                clearCache();
                return;
            }
            heatedCoolantStack = MekanismChemicals.STEAM.asStack(1);
            thermalEnthalpy = HeatUtils.getWaterThermalEnthalpy();
            conductivity = 0.5d;
            refreshActiveState();
        }
    }

    private void clearCache() {
        heatedCoolantStack = ChemicalStack.EMPTY;
        thermalEnthalpy = 0.0d;
        conductivity = 0.0d;
        lastBoilRate = 0l;
        setInactive();
    }

    public void setInactive() {
        isActive = false;
    }

    public void refreshActiveState() {
        isActive = !heatedCoolantStack.isEmpty() &&
                (outputTank.isEmpty() || outputTank.getStack().is(heatedCoolantStack.getChemical()));
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * @return Temperature decrease caused by coolant heating.
     */
    public double updateAndProcess(IHeatCapacitor heatCapacitor) {
        if (!isActive || !externalActive.getAsBoolean() || heatCapacitor.isAmbientTemperature()
                || thermalEnthalpy <= 0) {
            lastBoilRate = 0l;
            return 0.0d;
        }
        double heat = boilEfficiency
                * (heatCapacitor.getHeat() - HeatUtils.BASE_BOIL_TEMP * heatCapacitor.getHeatCapacity());
        if (heat <= 0) {
            lastBoilRate = 0l;
            return 0.0d;
        }
        double caseCoolantHeat = heat * conductivity;
        boolean chemical = currentType == MergedTank.CurrentType.CHEMICAL;
        long stored = chemical
                ? inputTank.getChemicalTank().getStored()
                : inputTank.getFluidTank().getFluidAmount();
        lastBoilRate = Math.min(stored, outputTank.getNeeded());
        lastBoilRate = Math.min(lastBoilRate,
                MathUtils.clampToLong((chemical
                        ? caseCoolantHeat
                        : caseCoolantHeat * HeatUtils.getSteamEnergyEfficiency()) / thermalEnthalpy));
        if (lastBoilRate < 1) {
            lastBoilRate = 0l;
            return 0.0d;
        }
        if (chemical) {
            inputTank.getChemicalTank().shrinkStack(lastBoilRate, Action.EXECUTE);
        } else {
            inputTank.getFluidTank().shrinkStack(MathUtils.clampToInt(lastBoilRate), Action.EXECUTE);
        }
        outputTank.insert(heatedCoolantStack.copyWithAmount(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
        double beforeTemp = heatCapacitor.getTemperature();
        heatCapacitor.handleHeat(-(chemical
                ? thermalEnthalpy * lastBoilRate
                : thermalEnthalpy * lastBoilRate / HeatUtils.getSteamEnergyEfficiency()));
        return beforeTemp - heatCapacitor.getTemperature();
    }

    public long getLastBoilRate() {
        return lastBoilRate;
    }

    public void trackContainer(MekanismContainer container){
        container.track(SyncableLong.create(this::getLastBoilRate, v->lastBoilRate=v));
    }

}
