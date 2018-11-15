package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;
import org.firstinspires.ftc.teamcode.utility.Utility;

public class TapeMeasureRobot extends Robot {

    MotorGroup leftDrive, rightDrive;
    DcMotor tapeMeasure, arm;

    Servo grabber;

    /*
    Config
     */
    private final double servoSpeed = .001d;
    private final double openPosition = 0d;
    private final double closedPosition = 1d;
    /*
    Config
     */

    public TapeMeasureRobot(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    @Override
    public void init() {

        leftDrive = new MotorGroup(hardwareMap, "left_front", "left_rear");
        rightDrive = new MotorGroup(hardwareMap, "right_front", "right_rear");

        tapeMeasure = hardwareMap.dcMotor.get("tape_measure");

        arm = hardwareMap.dcMotor.get("arm");

        grabber = hardwareMap.servo.get("grabber");

    }

    public void extend(double speed) {
        tapeMeasure.setPower(speed);
    }

    public void retract(double speed) {
        tapeMeasure.setPower(-speed);
    }

    public void grab() {
        grabber.setPosition(Utility.lerp(grabber.getPosition(), closedPosition, servoSpeed));
    }

    public void release() {
        grabber.setPosition(Utility.lerp(grabber.getPosition(), openPosition, servoSpeed));
    }

}
