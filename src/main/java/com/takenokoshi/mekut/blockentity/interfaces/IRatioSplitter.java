package com.takenokoshi.mekut.blockentity.interfaces;

import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableInt;

public interface IRatioSplitter {
    void setRatio1(int v);

    void setRatio2(int v);

    int getRatio1();

    int getRatio2();

    default void trackRatio(MekanismContainer container){
        container.track(SyncableInt.create(this::getRatio1, this::setRatio1));
        container.track(SyncableInt.create(this::getRatio2, this::setRatio2));
    }
}
