package com.takenokoshi.mekut.core;

import java.util.function.LongSupplier;

import com.takenokoshi.mekut.config.MekUtConfig;

import mekanism.api.math.MathUtils;

public class MekUtMathUtils {
    public static int getTicksAccelerated(int baseticks, double multiply) {
        return Math.max(1, MathUtils
                .clampToInt(baseticks / (multiply
                        * MekUtConfig.general.standardMachinePerformance.get())));
    }

    public static int getBaselineAccelerated(int baseticks, double multiply) {
        return Math.max(1, MathUtils
                .clampToInt((multiply
                        * MekUtConfig.general.standardMachinePerformance.get()) / baseticks));
    }

    public static LongSupplier getUsageAccelerated(LongSupplier baseUsage, double multiply) {
        return () -> MathUtils.clampToLong(baseUsage.getAsLong() * multiply
                * MekUtConfig.general.standardMachinePerformance.get());
    }

    public static LongSupplier getStorageAccelerated(LongSupplier baseStorage, double multiply) {
        return () -> MathUtils.clampToLong(baseStorage.getAsLong() * multiply
                * Math.pow(MekUtConfig.general.standardMachinePerformance.get(), 2));
    }

}
