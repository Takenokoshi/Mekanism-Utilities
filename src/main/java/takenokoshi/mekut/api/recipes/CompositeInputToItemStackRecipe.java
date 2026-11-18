package takenokoshi.mekut.api.recipes;

import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class CompositeInputToItemStackRecipe extends CompositeInputRecipe {

    public abstract List<ItemStack> getOutputDefinition();

    public abstract ItemStack getOutput(List<ItemStack> itemInputs, FluidStack fluidInput, ChemicalStack chemicalInput);
}
