package com.takenokoshi.mekut.core;

import java.util.Arrays;
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

    public static LongSupplier getMultiplied(LongSupplier base, double multiply) {
        return () -> MathUtils.clampToLong(base.getAsLong() * multiply);
    }

    public static LongSupplier getMultiplied(LongSupplier base, LongSupplier multiply) {
        return () -> MathUtils.clampToLong(1.0 * base.getAsLong() * multiply.getAsLong());
    }

    public static LongSupplier getMultiplied(LongSupplier base, LongSupplier... multiply) {
        return () -> MathUtils.clampToLong(Arrays.stream(multiply).mapToDouble(LongSupplier::getAsLong)
                .reduce(1d * base.getAsLong(), (a, b) -> a * b));
    }

}
