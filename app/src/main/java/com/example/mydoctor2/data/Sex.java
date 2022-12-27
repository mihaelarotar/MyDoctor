package com.example.mydoctor2.data;

import androidx.annotation.NonNull;

public enum Sex {
    F("Feminin"),
    M("Masculin");

    private final String gen;

    Sex(String gen) {
        this.gen = gen;
    }

    @NonNull
    @Override
    public String toString() {
        return gen;
    }
}
