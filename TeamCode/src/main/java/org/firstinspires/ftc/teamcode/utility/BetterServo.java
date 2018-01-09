package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoImplEx;

/**
 * Created by jacktwamb52 on 1/9/2018.
 */

public class BetterServo extends ServoImplEx {

    public static BetterServo get(HardwareMap hardwareMap, String name) {
        Servo base = hardwareMap.servo.get(name);
        BetterServo servo = new BetterServo(base.getController(), base.getPortNumber(), base.getDirection());
        return servo;
    }

    private BetterServo(ServoController controller, int portNumber)
    {
        super(controller, portNumber, Direction.FORWARD);
    }

    private BetterServo(ServoController controller, int portNumber, Direction direction)
    {
        super(controller, portNumber, direction);
    }

    private double lerp(double target, double speed) {

        double start = getPosition();

        if (start == target) return start;

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
