package com.takenokoshi.mekut.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MekUtGeneralConfig extends BaseMekanismConfig {

    public final CachedDoubleValue standardMachinePerformance;

    private final ModConfigSpec configSpec;

    MekUtGeneralConfig() {

        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        standardMachinePerformance = CachedDoubleValue.wrap(this, MekUtConfigTranslation.STANDARD_MACHINE_PERFORMANCE
                .applyToBuilder(builder).defineInRange("standardMachinePerformance", 200, 200, 48000D));

        configSpec = builder.build();
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }

    @Override
    public String getFileName() {
        return "mekut-general";
    }

    @Override
    public String getTranslation() {
        return "MekUt General Config";
    }

}
