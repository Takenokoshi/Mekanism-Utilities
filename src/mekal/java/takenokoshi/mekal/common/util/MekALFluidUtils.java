package takenokoshi.mekal.common.util;

import java.util.HashMap;
import java.util.Map;

import mekanism.common.registries.MekanismFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.common.MekAL;

public class MekALFluidUtils {
    private static final Map<Item, FluidStack> INFINITE_FLUID_SOURCES = new HashMap<>();

    public static void registerFluidSource(ItemLike item, FluidStack fluidStack) {
        if (!fluidStack.isEmpty()) {
            INFINITE_FLUID_SOURCES.put(item.asItem(), fluidStack.copyWithAmount(Integer.MAX_VALUE));
        }
    }

    /**
     * @apiNote RETURN VALUE IS READ ONLY. DO NOT MODIFY RETURN VALUE.
     */
    public static FluidStack getSuppliedFluid(ItemLike item) {
        return INFINITE_FLUID_SOURCES.getOrDefault(item.asItem(), FluidStack.EMPTY);
    }

    public static void registerCompats() {
        if (ModList.get().isLoaded("mwgr")) {
            try {
                registerFluidSource(
                        BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mwgr", "water_generator")),
                        new FluidStack(Fluids.WATER, Integer.MAX_VALUE));
                registerFluidSource(
                        BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mwgr", "lava_generator")),
                        new FluidStack(Fluids.LAVA, Integer.MAX_VALUE));
                registerFluidSource(
                        BuiltInRegistries.ITEM
                                .get(ResourceLocation.fromNamespaceAndPath("mwgr", "heavywater_generator")),
                        new FluidStack(MekanismFluids.HEAVY_WATER, Integer.MAX_VALUE));
            } catch (Exception e) {
                MekAL.LOGGER
                        .warn("Failed to register Mekanism: Water Generator Rebuild compatibility on MekALFluidUtils.");
            }
        }
    }
}
