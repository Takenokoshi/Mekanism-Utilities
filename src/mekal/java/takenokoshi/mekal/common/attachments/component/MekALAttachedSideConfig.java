package takenokoshi.mekal.common.attachments.component;

import java.util.EnumMap;
import java.util.Map;

import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.lib.transmitter.TransmissionType;

public class MekALAttachedSideConfig {
    public static final AttachedSideConfig ITEM_FLUID_TO_ITEM_MACHINE = createItemFluidToItemMachine();
    public static final AttachedSideConfig ITEM_TO_FLUID_MACHINE = createItemToFluidMachine();
    public static final AttachedSideConfig FLUID_TO_ITEM_MACHINE = createFluidToItemMachine();

    private static AttachedSideConfig createItemFluidToItemMachine() {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, LightConfigInfo.MACHINE);
        configInfo.put(TransmissionType.FLUID, LightConfigInfo.RIGHT_OUTPUT);
        configInfo.put(TransmissionType.ENERGY, LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    }

    private static AttachedSideConfig createItemToFluidMachine() {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, LightConfigInfo.EXTRA_MACHINE);
        configInfo.put(TransmissionType.FLUID, LightConfigInfo.OUT_EJECT);
        configInfo.put(TransmissionType.ENERGY, LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    }

    private static AttachedSideConfig createFluidToItemMachine() {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, LightConfigInfo.EXTRA_MACHINE);
        configInfo.put(TransmissionType.FLUID, LightConfigInfo.INPUT_ONLY);
        configInfo.put(TransmissionType.ENERGY, LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    }
}
