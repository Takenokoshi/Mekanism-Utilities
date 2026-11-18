package takenokoshi.mekal.common.block;

import org.jetbrains.annotations.NotNull;

import mekanism.api.text.ILangEntry;
import mekanism.common.block.interfaces.IHasDescription;
import net.minecraft.world.level.block.Block;

public class BlockDescription extends Block implements IHasDescription {
    
    private final String descriptionTranslationKey;

    public BlockDescription(Properties properties, String descriptionTranslationKey) {
        super(properties);
        this.descriptionTranslationKey = descriptionTranslationKey;
    }

    @NotNull
    public String getDescriptionTranslationKey(){
        return descriptionTranslationKey;
    }

    @Override
    public @NotNull ILangEntry getDescription() {
        return this::getDescriptionTranslationKey;
    }
    
}
