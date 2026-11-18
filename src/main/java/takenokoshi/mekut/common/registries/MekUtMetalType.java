package takenokoshi.mekut.common.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum MekUtMetalType {
    // 銀の加工はMekanism: MoreMachine,ナクアダの加工はMekanism Extrasに任せる。
    // Mekanismのアドオンでは対応していない金属でサポートを拡張するのが目的。
    // 鉄周辺の遷移金属といくつかのよく使われる重金属、アルミニウムをピックアップしてサポート。
    // ここに値を追加してDatagen回せばサポート対象を追加できるようにしておく。

    ALUMINUM(0xC7D0D8),
    TITANIUM(0xA99BCB),
    CHROMIUM(0x70A987),
    COBALT(0x2376DD),
    NICKEL(0xA6B58A),
    ZINC(0xA6C6C9),
    TUNGSTEN(0x41434A),
    IRIDIUM(0xC5A5D8),
    PLATINUM(0xD8C5A1),
    REDSTONE(0xB51F35, 6),
    ;

    /**
     * 懸濁液の色に使う。
     */
    public final int color;
    /**
     * 鉱石倍化の追加倍率
     * レッドストーンはMekanismの濃縮室での処理が12倍なのでそれに合わせる必要がある
     */
    public final int multiplier;
    /**
     * idの決定に使う。
     */
    public final String name;
    public final TagKey<Item> oreTag, rawTag, rawBlockTag, crystalTag, shardTag, clumpTag, dirtyDustTag, dustTag,
            ingotTag;

    private MekUtMetalType(int color, int multiplier) {
        this.color = color;
        this.multiplier = multiplier;
        // id用なので小文字に変換する
        this.name = name().toLowerCase();
        this.oreTag = itemTag("ores/");
        this.rawTag = itemTag("raw_materials/");
        this.rawBlockTag = itemTag("storage_blocks/raw_");
        this.crystalTag = itemTag("crystals/");
        this.shardTag = itemTag("shards/");
        this.clumpTag = itemTag("clumps/");
        this.dirtyDustTag = itemTag("dirty_dusts/");
        this.dustTag = itemTag("dusts/");
        this.ingotTag = itemTag("ingots/");
    }

    private MekUtMetalType(int color) {
        this(color, 1);
    }

    private TagKey<Item> itemTag(String loc) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", loc + name));
    }
}
