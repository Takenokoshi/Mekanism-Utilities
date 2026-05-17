package com.takenokoshi.mekut.core;

import mekanism.api.Upgrade;
import mekanism.common.tile.component.TileComponentUpgrade;

public class MekUtUpgradeUtils {

    public static boolean isEmpoweredSpeed(Upgrade upgrade) {
        if (upgrade.toString().equals("EMPOWERED_SPEED")) {
            return true;
        }
        return false;
    }

    public static int getEmpoweredSpeed(TileComponentUpgrade componentUpgrade) {
        return componentUpgrade.getUpgrades(Upgrade.SPEED) < 8
                ? 0
                : componentUpgrade.getUpgrades(Upgrade.valueOf("EMPOWERED_SPEED"));
    }
}
