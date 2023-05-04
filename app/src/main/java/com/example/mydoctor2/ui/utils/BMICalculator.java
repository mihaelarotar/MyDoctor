package com.example.mydoctor2.ui.utils;

import com.example.mydoctor2.data.BMIStatus;
import com.example.mydoctor2.data.User;

public class BMICalculator {

    public BMIStatus calculateBMIStatus(User user) {
        if(user.getKg() != 0 && user.getHeight() != 0) {
            float BMI;
            BMIStatus status;
            BMI = (user.getKg() * 10000) / (user.getHeight() * user.getHeight());
            if (BMI < 18.5) {
                status = BMIStatus.UNDERWEIGHT;
            } else if (18.5 <= BMI && BMI <= 24.9) {
                status = BMIStatus.NORMAL;
            } else if (25 <= BMI && BMI <= 29.9) {
                status = BMIStatus.OVERWEIGHT;
            } else if (30 <= BMI && BMI <= 34.9) {
                status = BMIStatus.OBESE;
            } else {
                status = BMIStatus.EXTREMELY_OBESE;
            }
            return status;
        }
        else {
            throw new ArithmeticException();
        }
    }
}
