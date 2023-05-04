package com.example.mydoctor2.ui.utils;

import com.example.mydoctor2.data.BMIStatus;
import com.example.mydoctor2.data.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BMICalculatorTest {

    private BMICalculator bmiCalculator;
    private final User user = new User();
    private final User underweightUser = new User();
    private final User normalUser = new User();
    private final User overweightUser = new User();
    private final User obeseUser = new User();
    private final User extremelyObeseUser = new User();

    @Before
    public void setUp() {
        bmiCalculator = new BMICalculator();
        user.setHeight(0);
        user.setKg(0);

        underweightUser.setKg(40);
        underweightUser.setHeight(175);

        normalUser.setKg(60);
        normalUser.setHeight(170);

        overweightUser.setKg(80);
        overweightUser.setHeight(175);

        obeseUser.setKg(100);
        obeseUser.setHeight(180);

        extremelyObeseUser.setKg(150);
        extremelyObeseUser.setHeight(162);
    }

    @Test
    public void testCalculateBMI_throwExceptionWhenHeightOrWeightNull() {
        Assert.assertThrows(ArithmeticException.class, () -> bmiCalculator.calculateBMIStatus(user));
    }

    @Test
    public void testCalculateBMI_underweightUser() {
        Assert.assertEquals(BMIStatus.UNDERWEIGHT, bmiCalculator.calculateBMIStatus(underweightUser));
    }

    @Test
    public void testCalculateBMI_normalUser() {
        Assert.assertEquals(BMIStatus.NORMAL, bmiCalculator.calculateBMIStatus(normalUser));
    }

    @Test
    public void testCalculateBMI_overweightUser() {
        Assert.assertEquals(BMIStatus.OVERWEIGHT, bmiCalculator.calculateBMIStatus(overweightUser));
    }

    @Test
    public void testCalculateBMI_obeseUser() {
        Assert.assertEquals(BMIStatus.OBESE, bmiCalculator.calculateBMIStatus(obeseUser));
    }

    @Test
    public void testCalculateBMI_extremelyObeseUser() {
        Assert.assertEquals(BMIStatus.EXTREMELY_OBESE, bmiCalculator.calculateBMIStatus(extremelyObeseUser));
    }
}