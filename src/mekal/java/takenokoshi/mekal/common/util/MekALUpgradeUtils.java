package takenokoshi.mekal.common.util;

import java.util.EnumSet;
import java.util.Set;

import mekanism.api.Upgrade;
import net.neoforged.fml.ModList;
import takenokoshi.mekal.common.MekAL;

public class MekALUpgradeUtils {
    private static final Set<Upgrade> SPEED_UPGRADES = EnumSet.of(Upgrade.SPEED);
    private static final Set<Upgrade> ENERGY_UPGRADES = EnumSet.of(Upgrade.ENERGY);

    public static void registerSpeedModifier(Upgrade upgrade) {
        SPEED_UPGRADES.add(upgrade);
    }

    public static void registerEnergyModifier(Upgrade upgrade) {
        ENERGY_UPGRADES.add(upgrade);
    }

    public static boolean isSpeedModifier(Upgrade upgrade) {
        return SPEED_UPGRADES.contains(upgrade);
    }

    public static boolean isEnergyModifier(Upgrade upgrade) {
        return ENERGY_UPGRADES.contains(upgrade);
    }

    public static void registerCompats() {
        if (ModList.get().isLoaded("mekanism_empowered")) {
            try {
                registerSpeedModifier(Upgrade.valueOf("EMPOWERED_SPEED"));
                registerEnergyModifier(Upgrade.valueOf("EMPOWERED_SPEED"));
            } catch (Exception e) {
                MekAL.LOGGER.warn("Failed to register Mekanism: Empowered compatibility on MekALUpgradeUtils.");
            }
        }
        if (ModList.get().isLoaded("mekanism_extras")) {
            try {
                registerSpeedModifier(Upgrade.valueOf("STACK"));
            } catch (Exception e) {
                MekAL.LOGGER.warn("Failed to register Mekanism Extras compatibility on MekALUpgradeUtils.");
            }
        }
    }

}
