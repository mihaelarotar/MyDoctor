package com.example.mydoctor2.data;

import androidx.annotation.NonNull;

public enum BMIStatus {
    UNDERWEIGHT("Subponderal"),
    NORMAL("Normal"),
    OVERWEIGHT("Supraponderal"),
    OBESE("Obez"),
    EXTREMELY_OBESE("Extrem de obez");

    private final String label;

    BMIStatus(String label) {
        this.label = label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}
