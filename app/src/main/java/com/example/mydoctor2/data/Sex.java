package com.example.mydoctor2.data;

public enum Sex {
    F("Feminin"),
    M("Masculin");

    private String gen;

    Sex(String gen) {
        this.gen = gen;
    }

    @Override
    public String toString() {
        return gen;
    }
}
