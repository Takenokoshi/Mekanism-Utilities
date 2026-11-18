package takenokoshi.mekut.common.registries;

import java.util.EnumMap;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import takenokoshi.mekut.common.MekUt;

public class MekUtChemicals {
    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekUt.MODID);
    
    public static final DeferredChemical<?> GLOWSTONE = CHEMICALS.registerInfuse("glowstone", 0xFFBC5E);

    public static final DeferredChemical<?> GLOWSTONE_SOLVENT = CHEMICALS.registerPigment("glostone_solvent", 0xFFBC5E);
    public static final DeferredChemical<?> SPENT_GLOWSTONE_SOLVENT = CHEMICALS.register("spent_glostone_solvent", 0xCF8C2E);
    
    public static final DeferredChemical<?> ACETYLENE = CHEMICALS.register("acetylene", 0xD6C9B8);
    public static final DeferredChemical<?> BENZENE = CHEMICALS.register("benzene", 0xB8C7D8);
    public static final DeferredChemical<?> CYCLOHEXANE = CHEMICALS.register("cyclohexane", 0x9FBFC2);
    public static final DeferredChemical<?> KA_OIL = CHEMICALS.register("ka_oil", 0x80602F);
    public static final DeferredChemical<?> ADIPIC_ACID = CHEMICALS.register("adipic_acid", 0xEDE7D3);
    public static final DeferredChemical<?> ADIPAMIDE = CHEMICALS.register("adipamide", 0xD4D0BD);
    public static final DeferredChemical<?> ADIPONITRILE = CHEMICALS.register("adiponitrile", 0x9BB9D1);
    public static final DeferredChemical<?> HEXAMETHYLENE_DIAMINE = CHEMICALS
            .register("hexamethylene_diamine", 0xD8BFA3);
    public static final DeferredChemical<?> POLYAMIDE_FIBER = CHEMICALS.register("polyamide_fiber", 0xF5F0DF);
    public static final DeferredChemical<?> I_II_DICHLOROETHANE = CHEMICALS.register("1.2-dichloroethane", 0xC7D9D2);
    public static final DeferredChemical<?> CHLOROETHYLENE = CHEMICALS.register("chloroethylene", 0xB8C9D8);
    public static final DeferredChemical<?> POLYVINYL_CHLORIDE = CHEMICALS.register("polyvinyl_chloride", 0xD5D2C7);
    public static final DeferredChemical<?> PROPYLENE = CHEMICALS.register("propylene", 0xD4B8A6);
    public static final DeferredChemical<?> POLYPROPYLENE = CHEMICALS.register("polypropylene", 0xE0D2B8);
    public static final DeferredChemical<?> HYDROGEN_CHLORIDE_OXIGEN_MIXED_GAS = CHEMICALS
            .register("hydrogen_chloride-oxigen_mixed_gas", 0xB8B6C8);
    public static final DeferredChemical<?> CARBON_MONOXIDE = CHEMICALS.register("carbon_monoxide", 0x6E7278);
    public static final DeferredChemical<?> METHANOL = CHEMICALS.register("methanol", 0xAFC8D8);

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
