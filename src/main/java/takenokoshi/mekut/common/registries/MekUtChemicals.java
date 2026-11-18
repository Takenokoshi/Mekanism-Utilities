package takenokoshi.mekut.common.registries;

import java.util.EnumMap;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import takenokoshi.mekut.common.MekUt;

public class MekUtChemicals {
    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekUt.MODID);

    // SlurryRegistryObject を使わない理由は、命名規則の都合
    public static final EnumMap<MekUtMetalType, DeferredChemical<Chemical>> METAL_DIRTY_SLURRIES = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, DeferredChemical<Chemical>> METAL_CLEAN_SLURRIES = new EnumMap<>(
            MekUtMetalType.class);
    static {
        for (final MekUtMetalType metalType : MekUtMetalType.values()) {
            METAL_DIRTY_SLURRIES.put(metalType, CHEMICALS.register(
                    "dirty_" + metalType.name + "_sulrry",
                    () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(metalType.color))));
            METAL_CLEAN_SLURRIES.put(metalType, CHEMICALS.register(
                    "clean_" + metalType.name + "_sulrry",
                    () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(metalType.color))));
        }
    }

}
