package com.takenokoshi.mekut.recipe.cached;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;

public final class OperationTracker2 {
    private static final int RESET_PROGRESS = -1;
    private static final int MISMATCHED_RECIPE = -2;
    private final Set<RecipeError> lastErrors;
    Set<RecipeError> errors = Collections.emptySet();
    private boolean checkAll;
    private boolean checkedErrors = true;
    int currentMax;
    int maxForEnergy;

    protected OperationTracker2(Set<RecipeError> lastErrors, boolean checkAll, int startingMax) {
        this.lastErrors = lastErrors;
        this.checkAll = checkAll;
        this.currentMax = startingMax;
        this.maxForEnergy = this.currentMax;
    }

    public boolean hasErrorsToCopy() {
        if (this.currentMax == MISMATCHED_RECIPE) {
            this.errors = Collections.emptySet();
            return true;
        } else if (!this.checkAll && this.currentMax <= 0) {
            return !this.checkedErrors && !this.lastErrors.containsAll(this.errors);
        } else {
            return true;
        }
    }

    public boolean shouldContinueChecking() {
        if (this.currentMax > 0) {
            return true;
        } else {
            if (this.currentMax == 0) {
                if (this.checkAll) {
                    return true;
                }

                if (!this.checkedErrors) {
                    if (!this.lastErrors.containsAll(this.errors)) {
                        this.checkAll = true;
                        return true;
                    }

                    this.checkedErrors = true;
                }
            }

            return false;
        }
    }

    public boolean updateOperations(int max) {
        if (max < this.currentMax) {
            this.currentMax = max;
            return true;
        } else {
            return false;
        }
    }

    public boolean capAtMaxForEnergy() {
        return this.updateOperations(this.maxForEnergy);
    }

    public void mismatchedRecipe() {
        this.updateOperations(MISMATCHED_RECIPE);
    }

    public void resetProgress(RecipeError error) {
        this.updateOperations(RESET_PROGRESS);
        this.addError(error);
    }

    public void addError(RecipeError error) {
        Objects.requireNonNull(error, "Error cannot be null.");
        if (this.errors.isEmpty()) {
            this.errors = new ObjectArraySet<>();
        }

        if (this.errors.add(error)) {
            this.checkedErrors = false;
        }

    }
}
