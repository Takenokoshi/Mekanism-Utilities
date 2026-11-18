package takenokoshi.mekal.datagen.loot.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.Mekanism;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.Attributes.AttributeInventory;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.lib.frequency.FrequencyType;
import mekanism.common.lib.frequency.IFrequencyItem;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import takenokoshi.mekal.common.registration.MachineRegistryObject;
/**
 * Some methods & fields were copied from
 * https://github.com/mekanism/Mekanism/blob/1.21.x/src/datagen/main/java/mekanism/common/loot/table/BaseBlockLootTables.java
 */
public abstract class BaseBlockLootSubProvider extends BlockLootSubProvider {

    protected final Set<Block> knownBlocks, toSkip;

    protected BaseBlockLootSubProvider(Provider registries) {
        super(Collections.emptySet(), FeatureFlags.VANILLA_SET, registries);
        this.knownBlocks = new ReferenceOpenHashSet<>();
        this.toSkip = new ReferenceArraySet<>();
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        super.add(block, table);
        knownBlocks.add(block);
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    @SafeVarargs
    protected final void skip(Holder<Block>... blockProviders) {
        for (Holder<Block> blockProvider : blockProviders) {
            toSkip.add(blockProvider.value());
        }
    }

    protected boolean skipBlock(Holder<Block> holder) {
        Block block = holder.value();
        return knownBlocks.contains(block) || toSkip.contains(block);
    }

    protected void add(Holder<Block> block, Function<Block, LootTable.Builder> factory) {
        add(block.value(), factory);
    }

    protected void dropSelf(Collection<? extends Holder<Block>> blocks) {
        for (Holder<Block> block : blocks) {
            if (!skipBlock(block)) {
                dropSelf(block.value());
            }
        }
    }

    protected void add(Function<Block, LootTable.Builder> factory, Collection<? extends Holder<Block>> blockProviders) {
        for (Holder<Block> blockProvider : blockProviders) {
            add(blockProvider.value(), factory);
        }
    }

    @SafeVarargs
    protected final void add(Function<Block, LootTable.Builder> factory, Holder<Block>... blockProviders) {
        for (Holder<Block> blockProvider : blockProviders) {
            add(blockProvider.value(), factory);
        }
    }

    protected void dropSelfWithContents(Collection<MachineRegistryObject<?, ?, ?, ?>> machineRegistryObjects) {
        machineRegistryObjects.forEach(this::dropSelfWithContents);
    }

    protected <BE extends TileEntityMekanism, BLOCK extends BlockTileModel<BE, Machine<BE>>> void dropSelfWithContents(
            MachineRegistryObject<BE, BLOCK, ?, ?> machineRegistryObject) {
        if (skipBlock(machineRegistryObject.getBlock())) {
            return;
        }
        Block block = machineRegistryObject.getBlock().value();
        boolean hasComponents = false;
        CopyComponentsFunction.Builder componentsBuilder = CopyComponentsFunction
                .copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY);
        boolean hasContents = false;
        ItemStack stack = new ItemStack(machineRegistryObject, 1);
        LootItem.Builder<?> itemLootPool = LootItem.lootTableItem(machineRegistryObject);
        DelayedLootItemBuilder delayedPool = new DelayedLootItemBuilder();
        BE tile = machineRegistryObject.getTile().value().create(BlockPos.ZERO, block.defaultBlockState());

        Set<FrequencyType<?>> customFrequencies = tile.getFrequencyComponent().getCustomFrequencies();
        if (!customFrequencies.isEmpty() && machineRegistryObject.asItem() instanceof IFrequencyItem frequencyItem) {
            FrequencyType<?> frequencyType = frequencyItem.getFrequencyType();
            if (!customFrequencies.contains(frequencyType)) {
                Mekanism.logger.warn("Block missing frequency type '{}' expected by item: {}",
                        frequencyType.getName(), machineRegistryObject.getId());
            }
        }

        List<DataComponentType<?>> components = tile.getRemapEntries();
        if (!components.isEmpty()) {
            Set<DataComponentType<?>> skipTypes = ContainerType.TYPES.stream()
                    .map(type -> type.getComponentType().get()).collect(Collectors.toSet());
            hasComponents = true;
            components.sort(Comparator.comparing(BuiltInRegistries.DATA_COMPONENT_TYPE::getKey,
                    ResourceLocation::compareNamespaced));
            for (DataComponentType<?> remapEntry : components) {
                if (!skipTypes.contains(remapEntry)) {
                    componentsBuilder.include(remapEntry);
                }
            }
        }
        if (tile.isNameable()) {
            itemLootPool.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY));
        }
        for (ContainerType<?, ?, ?> type : ContainerType.TYPES) {
            List<?> containers = tile.persists(type) ? type.getContainers(tile)
                    : Collections.emptyList();
            int attachmentContainers = type.getContainerCount(stack);
            if (containers.size() == attachmentContainers) {
                if (!containers.isEmpty()) {
                    componentsBuilder.include(type.getComponentType().get());
                    hasComponents = true;
                    if (type != ContainerType.ENERGY && type != ContainerType.HEAT) {
                        hasContents = true;
                    }
                }
            } else if (attachmentContainers == 0) {
                Mekanism.logger.warn("Container type: {}, item missing attachments: {}",
                        type.getComponentName(), machineRegistryObject.getId());
            } else if (containers.isEmpty()) {
                Mekanism.logger.warn(
                        "Container type: {}, item has attachments but block doesn't have containers: {}",
                        type.getComponentName(), machineRegistryObject.getId());
            } else {
                Mekanism.logger.warn(
                        "Container type: {}, has {} item attachments and block has {} containers: {}",
                        type.getComponentName(), attachmentContainers,
                        containers.size(), machineRegistryObject.getId());
            }
        }
        @SuppressWarnings("unchecked")
        AttributeInventory<DelayedLootItemBuilder> attributeInventory = Attribute.get(machineRegistryObject.getBlock(),
                AttributeInventory.class);
        if (attributeInventory != null) {
            hasContents |= attributeInventory.applyLoot(delayedPool);
        }
        if (hasComponents) {
            itemLootPool.apply(componentsBuilder);
        }
        for (LootItemFunction.Builder function : delayedPool.functions) {
            itemLootPool.apply(function);
        }
        for (LootItemCondition.Builder condition : delayedPool.conditions) {
            itemLootPool.when(condition);
        }
        add(block, LootTable.lootTable().withPool(applyExplosionCondition(hasContents, LootPool.lootPool()
                .name("main")
                .setRolls(ConstantValue.exactly(1))
                .add(itemLootPool))));
    }

    private static <T extends ConditionUserBuilder<T>> T applyExplosionCondition(boolean explosionResistant,
            ConditionUserBuilder<T> condition) {
        return explosionResistant ? condition.unwrap() : condition.when(ExplosionCondition.survivesExplosion());
    }

    @NothingNullByDefault
    public static class DelayedLootItemBuilder
            implements ConditionUserBuilder<DelayedLootItemBuilder>, FunctionUserBuilder<DelayedLootItemBuilder> {

        private final List<LootItemFunction.Builder> functions = new ArrayList<>();
        private final List<LootItemCondition.Builder> conditions = new ArrayList<>();

        @Override
        public DelayedLootItemBuilder apply(LootItemFunction.Builder pFunctionBuilder) {
            functions.add(pFunctionBuilder);
            return this;
        }

        @Override
        public DelayedLootItemBuilder when(LootItemCondition.Builder pConditionBuilder) {
            conditions.add(pConditionBuilder);
            return this;
        }

        @Override
        public DelayedLootItemBuilder unwrap() {
            return this;
        }
    }

}
