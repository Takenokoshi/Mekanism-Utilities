package com.takenokoshi.mekut.enums;

import com.takenokoshi.mekut.lang.MekUtLang;

import mekanism.api.text.IHasTranslationKey;
import mekanism.api.text.ILangEntry;
import mekanism.common.tile.component.config.DataType;

public enum MekUtDataType implements IHasTranslationKey {
    INPUT1_OUTPUT1("input1_output1"),
    INPUT2_OUTPUT2("input2_output2"),
    INPUT_OUTPUT1("input_output1"),
    INPUT_OUTPUT2("input_output2"),
    ;

    private MekUtDataType(ILangEntry descKey) {
        this.descKey = descKey;
    }

    private MekUtDataType(String name){
        this(new MekUtLang("datatype", name));
    }

    public final ILangEntry descKey;

    private DataType value;

    public void setValue(DataType value) {
        if (this.value == null) {
            this.value = value;
        }
    }

    public DataType getValue() {
        return value;
    }

    public boolean is(DataType dataType){
        return dataType==this.value;
    }

    @Override
    public String getTranslationKey() {
        return descKey.getTranslationKey();
    }
}
