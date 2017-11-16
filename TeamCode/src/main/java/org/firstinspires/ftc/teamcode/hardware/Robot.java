package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

/**
 * Created by jacktwamb52 on 11/15/2017.
 */

public class Robot {

    public MotorGroup leftMotors;
    public MotorGroup rightMotors;
    public MotorGroup liftMotors;
    public Servo servo;

    private HardwareMap hardwareMap;

    public Robot() {}

    public void init(HardwareMap hwMap) {
        hardwareMap = hwMap;

        //leftMotors = new MotorGroup(hwMap, "front_left", "rear_left").setDirection(DcMotor.Direction.REVERSE);
        //rightMotors = new MotorGroup(hwMap, "front_right", "rear_right");

        liftMotors = new MotorGroup(hwMap, "left_lift", "right_lift");

        servo = hwMap.get(Servo.class, "servo");

    }

    public static class MotorGroup {

        private HashMap<String, DcMotor> motors;

        public MotorGroup(HardwareMap _hm, String... _names) {
            motors = new HashMap<>();

            for (String m : _names)
                motors.put(m, _hm.get(DcMotor.class, m));
        }

        public MotorGroup setDirection(DcMotor.Direction _dir) {
            for (DcMotor motor : motors.values()) {
                motor.setDirection(_dir);
            }
            return this; // Returns itself for easy chaining
        }

        public void setPower(double _power) {
            for (DcMotor motor : motors.values()) {
                motor.setPower(_power);
            }
        }

        public void setPower(double power, String... _motors) {
            for (String m : _motors)
                motors.get(m).setPower(power);
        }

        public DcMotor getMotor(String _name) {
            return motors.get(_name);
        }

    }

}

