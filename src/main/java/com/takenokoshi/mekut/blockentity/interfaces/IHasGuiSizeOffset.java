package com.takenokoshi.mekut.blockentity.interfaces;

public interface IHasGuiSizeOffset {
    default int getExtraWidth() {
        return 0;
    };

    default int getExtraHeight() {
        return 0;
    };
}
