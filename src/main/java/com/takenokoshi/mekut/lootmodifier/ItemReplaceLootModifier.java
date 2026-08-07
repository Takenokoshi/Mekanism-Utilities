package com.takenokoshi.mekut.lootmodifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ItemReplaceLootModifier extends LootModifier {

    public static final MapCodec<ItemReplaceLootModifier> MAP_CODEC = RecordCodecBuilder
            .mapCodec(instance -> codecStart(instance)
                    .and(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("from")
                            .forGetter(modifier -> modifier.from))
                    .and(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("to")
                            .forGetter(modifier -> modifier.to))
                    .apply(instance, ItemReplaceLootModifier::new));

    private final Holder<Item> from;
    private final Holder<Item> to;

    public ItemReplaceLootModifier(LootItemCondition[] conditionsIn, Holder<Item> from, Holder<Item> to) {
        super(conditionsIn);
        this.from = from;
        this.to = to;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return MAP_CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (int i = 0; i < generatedLoot.size(); i++) {
            ItemStack stack = generatedLoot.get(i);
            if (stack.is(from)) {
                generatedLoot.set(i, new ItemStack(to, stack.getCount(), stack.getComponentsPatch()));
            }
        }
        return generatedLoot;
    }

}
