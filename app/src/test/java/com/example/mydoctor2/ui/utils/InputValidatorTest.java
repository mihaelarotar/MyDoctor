package com.example.mydoctor2.ui.utils;

import org.junit.Assert;
import org.junit.Test;

public class InputValidatorTest {
    private final InputValidator inputValidator = new InputValidator();

    @Test
    public void testValidateInputs_height_too_small() {
        float height = 20;
        Assert.assertFalse(inputValidator.validateHeight(height));
    }

    @Test
    public void testValidateInputs_height_too_big() {
        float height = 300;
        Assert.assertFalse(inputValidator.validateHeight(height));
    }

    @Test
    public void testValidateInputs_height_valid() {
        float height = 150;
        Assert.assertTrue(inputValidator.validateHeight(height));
    }

    @Test
    public void testValidateInputs_height_bva_edge_cases() {
        float height1 = 29;
        float height2 = 251;
        Assert.assertFalse(inputValidator.validateHeight(height1));
        Assert.assertFalse(inputValidator.validateHeight(height2));
    }

    @Test
    public void testValidateInputs_height_bva() {
        float height1 = 30;
        float height2 = 250;
        Assert.assertTrue(inputValidator.validateHeight(height1));
        Assert.assertTrue(inputValidator.validateHeight(height2));
    }

    @Test
    public void testValidateInputs_temperature_too_small() {
        double temperature = 35;
        Assert.assertFalse(inputValidator.validateTemperature(temperature));
    }

    @Test
    public void testValidateInputs_temperature_too_big() {
        double temperature = 45;
        Assert.assertFalse(inputValidator.validateTemperature(temperature));
    }

    @Test
    public void testValidateInputs_temperature_valid() {
        double temperature = 40;
        Assert.assertTrue(inputValidator.validateTemperature(temperature));
    }

    @Test
    public void testValidateInputs_temperature_bva_edge_cases() {
        double temperature1 = 35.9;
        double temperature2 = 42.1;
        Assert.assertFalse(inputValidator.validateTemperature(temperature1));
        Assert.assertFalse(inputValidator.validateTemperature(temperature2));
    }

    @Test
    public void testValidateInputs_temperature_bva() {
        double temperature1 = 36.0;
        double temperature2 = 42.0;
        Assert.assertTrue(inputValidator.validateTemperature(temperature1));
        Assert.assertTrue(inputValidator.validateTemperature(temperature2));
    }

    @Test
    public void testValidateInputs_pulse_too_small() {
        float pulse = 20;
        Assert.assertFalse(inputValidator.validatePulse(pulse));
    }

    @Test
    public void testValidateInputs_pulse_too_big() {
        float pulse = 200;
        Assert.assertFalse(inputValidator.validatePulse(pulse));
    }

    @Test
    public void testValidateInputs_pulse_valid() {
        float pulse = 100;
        Assert.assertTrue(inputValidator.validatePulse(pulse));
    }

    @Test
    public void testValidateInputs_pulse_bva_edge_cases() {
        float pulse1 = 29;
        float pulse2 = 151;
        Assert.assertFalse(inputValidator.validatePulse(pulse1));
        Assert.assertFalse(inputValidator.validatePulse(pulse2));
    }

    @Test
    public void testValidateInputs_pulse_bva() {
        float pulse1 = 30;
        float pulse2 = 150;
        Assert.assertTrue(inputValidator.validatePulse(pulse1));
        Assert.assertTrue(inputValidator.validatePulse(pulse2));
    }

}