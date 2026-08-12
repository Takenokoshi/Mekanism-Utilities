package com.takenokoshi.mekut.utils;

@FunctionalInterface
public interface QuadPredicate<P, Q, R, S> {
    boolean test(P p, Q q, R r, S s);
}
