package takenokoshi.mekut.common.registries;

import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.resources.ResourceLocation;
import takenokoshi.mekut.common.MekUt;

public class MekUtFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MekUt.MODID);

    public static final FluidRegistryObject<?, ?, ?, ?, ?> GLOWSTONE_SOLVENT = FLUIDS.register(
            "glowstone_solvent", props -> props.texture(
                    ResourceLocation.withDefaultNamespace("block/lava_still"),
                    ResourceLocation.withDefaultNamespace("block/lava_flow"))
                    .tint(0xFFBC5E));
}
