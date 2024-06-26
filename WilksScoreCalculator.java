package com.alexkim.powerliftingperformancetrackerv2;

import static java.lang.Math.round;
public class WilksScoreCalculator {
    // Wilks coefficients for male lifters
    private static final double[] MALE_COEFFICIENTS = {-216.0475144, 16.2606339, -0.002388645, -0.00113732, 7.01863E-06, -1.291E-08};
    // Wilks coefficients for female lifters
    private static final double[] FEMALE_COEFFICIENTS = {594.31747775582, -27.23842536447, 0.82112226871, -0.00930733913, 4.731582E-05, -9.054E-08};

    public static double calculateWilksScore(double bodyWeight, double sbd, boolean isMale) {
        double[] coefficients = isMale ? MALE_COEFFICIENTS : FEMALE_COEFFICIENTS;
        double wilks = sbd * (500 / calculateWilksCoefficient(bodyWeight, coefficients));
        return wilks-210;
    }

    public static double calculateWilksCoefficient(double bodyWeight, double[] coefficients) {
        double wilksCoefficient = 0;
        for (int i=0; i<coefficients.length; i++) {
            wilksCoefficient += coefficients[i] * Math.pow(bodyWeight, i);
        }
        return wilksCoefficient;
    }
}

