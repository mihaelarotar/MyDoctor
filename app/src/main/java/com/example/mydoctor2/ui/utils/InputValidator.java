package com.example.mydoctor2.ui.utils;

public class InputValidator {
    public boolean validateHeight(float height) {
        return !(height < 30 || height > 250);
    }

    public boolean validateTemperature(double temperature) {
        return !(temperature < 36.0 || temperature > 42.0);
    }

    public boolean validatePulse(float pulse) {
        return !(pulse < 30 || pulse > 150);
    }
}
