package com.takenokoshi.mekut.client.model;

import mekanism.client.model.baked.ExtensionBakedModel.TransformedBakedModel;
import mekanism.client.render.lib.QuadTransformation;
import net.minecraft.client.resources.model.BakedModel;

public class GreenHouseBakedModel extends TransformedBakedModel<Void> {

    public GreenHouseBakedModel(BakedModel original) {
        super(original,  QuadTransformation.translate(0, 1, 0));
    }

    @Override
    protected GreenHouseBakedModel wrapModel(BakedModel model) {
        return new GreenHouseBakedModel(model);
    }
    
}
