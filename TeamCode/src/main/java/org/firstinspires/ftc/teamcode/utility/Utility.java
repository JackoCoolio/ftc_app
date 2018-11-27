package org.firstinspires.ftc.teamcode.utility;

public class Utility {

    public static double lerp(double start, double target, double speed) {

        if (start == target) return start; // If we are already at the target position, don't change anything.

        double result = start;

        if (target > start) {
            result += speed;
            if (result > target)
                result = target;
        } else {
            result -= speed;
            if (result < target)
                result = target;
        }

        return result;

    }

}
