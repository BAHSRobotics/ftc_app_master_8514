package org.firstinspires.ftc.teamcode;

public class HelperMath {
    private static final double EPSILON = 1E-5;

    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
}
