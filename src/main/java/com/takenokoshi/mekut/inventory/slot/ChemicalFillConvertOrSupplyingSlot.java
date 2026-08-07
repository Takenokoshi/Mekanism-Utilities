package com.takenokoshi.mekut.inventory.slot;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.item.ChemicalSupplierItem;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.ItemStackToChemicalRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.util.MekanismUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChemicalFillConvertOrSupplyingSlot extends BasicInventorySlot
        implements ISingleRecipeLookupHandler.ItemRecipeLookupHandler<ItemStackToChemicalRecipe> {

    public static final RecipeError FAKE_ERROR = RecipeError.create();

    private static Predicate<ItemStack> getExtractPredicate(IChemicalTank chemicalTank,
            Supplier<Level> levelSupplier) {
        return stack -> {
            if (stack.getItem() instanceof ChemicalSupplierItem) {
                return false;
            }
            IChemicalHandler handler = Capabilities.CHEMICAL.getCapability(stack);
            if (handler != null) {
                for (int tank = 0; tank < handler.getChemicalTanks(); tank++) {
                    if (chemicalTank.isValid(handler.getChemicalInTank(tank))) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        };
    }

    private static Predicate<ItemStack> getInsertPredicate(IChemicalTank chemicalTank,
            Supplier<Level> levelSupplier) {
        return stack -> {
            if (stack.getItem() instanceof ChemicalSupplierItem supplierItem) {
                return chemicalTank
                        .insert(supplierItem.getStack().copyWithAmount(1), Action.SIMULATE, AutomationType.INTERNAL)
                        .isEmpty();
            }
            IChemicalHandler handler = Capabilities.CHEMICAL.getCapability(stack);
            if (handler != null) {
                for (int tank = 0; tank < handler.getChemicalTanks(); tank++) {
                    ChemicalStack chemicalInTank = handler.getChemicalInTank(tank);
                    if (!chemicalInTank.isEmpty()
                            && chemicalTank.insert(chemicalInTank, Action.SIMULATE, AutomationType.INTERNAL)
                                    .getAmount() < chemicalInTank.getAmount()) {
                        return true;
                    }
                }
            }
            if (MekanismRecipeType.CHEMICAL_CONVERSION.getInputCache().containsInput(levelSupplier.get(), stack)) {
                ChemicalStack recipeOutput = MekanismRecipeType.CHEMICAL_CONVERSION.getInputCache()
                        .findFirstRecipe(levelSupplier.get(), stack).getOutput(stack);
                return chemicalTank.insert(recipeOutput, Action.SIMULATE, AutomationType.INTERNAL)
                        .getAmount() < recipeOutput.getAmount();
            }
            return false;
        };
    }

    public static ChemicalFillConvertOrSupplyingSlot create(IChemicalTank chemicalTank, Supplier<Level> levelSupplier,
            @NotNull IContentsListener recipeCacheListener, int x, int y) {
        return new ChemicalFillConvertOrSupplyingSlot(chemicalTank, levelSupplier, recipeCacheListener, x, y);
    }

    protected final IChemicalTank chemicalTank;
    protected final Supplier<Level> levelSupplier;
    protected final IInputHandler<ItemStack> itemInputHandler;
    protected final IOutputHandler<ChemicalStack> outputHandler;

    protected final RecipeCacheLookupMonitor<ItemStackToChemicalRecipe> recipeCacheLookupMonitor;

    private Consumer<ChemicalStack> supplyingStackSetter = (stack) -> {
    };
    private boolean isSupplying = false;

    protected ChemicalFillConvertOrSupplyingSlot(IChemicalTank chemicalTank, Supplier<Level> levelSupplier,
            @NotNull IContentsListener recipeCacheListener, int x, int y) {
        super(getExtractPredicate(chemicalTank, levelSupplier),
                getInsertPredicate(chemicalTank, levelSupplier),
                getInsertPredicate(chemicalTank, levelSupplier),
                recipeCacheListener, x, y);
        this.chemicalTank = chemicalTank;
        this.levelSupplier = levelSupplier;
        this.itemInputHandler = InputHelper.getInputHandler(this, FAKE_ERROR);
        this.outputHandler = OutputHelper.getOutputHandler(chemicalTank, FAKE_ERROR);
        this.recipeCacheLookupMonitor = new RecipeCacheLookupMonitor<>(this);
        this.setSlotType(ContainerSlotType.EXTRA);
    }

    private void updateSupplyingStackAndLookupMonitor() {
        if (isEmpty()) {
            supplyingStackSetter.accept(ChemicalStack.EMPTY);
            recipeCacheLookupMonitor.onChange();
            isSupplying = false;
        } else if (getStack().getItem() instanceof ChemicalSupplierItem supplierItem) {
            supplyingStackSetter.accept(supplierItem.getStack());
            recipeCacheLookupMonitor.setHasNoRecipe(0);
            isSupplying = true;
        } else {
            supplyingStackSetter.accept(ChemicalStack.EMPTY);
            recipeCacheLookupMonitor.onChange();
            isSupplying = false;
        }
    }

    @Override
    public void onContentsChanged() {
        super.onContentsChanged();
        updateSupplyingStackAndLookupMonitor();
    }

    public void setSupplyingStackSetter(Consumer<ChemicalStack> value) {
        supplyingStackSetter = value;
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackToChemicalRecipe, SingleItem<ItemStackToChemicalRecipe>> getRecipeType() {
        return MekanismRecipeType.CHEMICAL_CONVERSION;
    }

    @Override
    public @NotNull CachedRecipe<ItemStackToChemicalRecipe> createNewCachedRecipe(
            @NotNull ItemStackToChemicalRecipe recipe, int cacheIndex) {
        return OneInputCachedRecipe.itemToChemical(recipe, () -> true, itemInputHandler, outputHandler)
                .setBaselineMaxOperations(() -> 0x7fffffff)
                .setOnFinish(this::onContentsChanged);

    }

    @Override
    public @Nullable Level getLevel() {
        return levelSupplier.get();
    }

    @Override
    public @Nullable ItemStackToChemicalRecipe getRecipe(int cacheIndex) {
        return MekanismRecipeType.CHEMICAL_CONVERSION.findFirst(getLevel(), recipe -> {
            if (!recipe.getInput().testType(current)) {
                return false;
            }
            ChemicalStack output = recipe.getOutput(getStack());
            if (output.isEmpty()) {
                return false;
            }
            return chemicalTank.insert(output.copyWithAmount(1), Action.SIMULATE, AutomationType.INTERNAL).isEmpty();
        });
    }

    public void fillTankOrConvert() {
        if (!isSupplying && !isEmpty() && chemicalTank.getNeeded() > 0) {
            if (!fillChemicalTankFromItem(this, chemicalTank, getCapability())) {
                recipeCacheLookupMonitor.updateAndProcess();
            }
        }
    }

    @Nullable
    protected IChemicalHandler getCapability() {
        return Capabilities.CHEMICAL.getCapability(current);
    }

    private static boolean fillChemicalTankFromItem(IInventorySlot slot, IChemicalTank chemicalTank,
            @Nullable IChemicalHandler handler) {
        if (handler != null) {
            ChemicalStack toExtract;
            if (chemicalTank.isEmpty()) {
                toExtract = handler.extractChemical(chemicalTank.getCapacity(), Action.SIMULATE);
            } else {
                ChemicalStack stack = chemicalTank.getStack();
                long amount = chemicalTank.getNeeded();
                toExtract = handler.extractChemical(stack.copyWithAmount(amount), Action.SIMULATE);
            }
            if (!toExtract.isEmpty()) {
                ChemicalStack simulatedRemainder = chemicalTank.insert(toExtract, Action.SIMULATE,
                        AutomationType.INTERNAL);
                toExtract.shrink(simulatedRemainder.getAmount());
                if (!toExtract.isEmpty()) {
                    ChemicalStack extractedChemical = handler.extractChemical(toExtract, Action.EXECUTE);
                    if (!extractedChemical.isEmpty()) {
                        MekanismUtils.logMismatchedStackSize(chemicalTank
                                .insert(extractedChemical, Action.EXECUTE, AutomationType.INTERNAL).getAmount(), 0);
                        slot.onContentsChanged();
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
