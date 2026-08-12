package com.takenokoshi.mekut.recipe.recipe.util;

@FunctionalInterface
public interface TriTypePredicate<P, Q, R> {
    boolean testType(P p, Q q, R r);
}
