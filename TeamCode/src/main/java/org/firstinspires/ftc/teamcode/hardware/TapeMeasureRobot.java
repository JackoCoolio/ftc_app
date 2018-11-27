package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;
import org.firstinspires.ftc.teamcode.utility.Utility;

public class TapeMeasureRobot extends Robot {

    public MotorGroup leftDrive, rightDrive;
    public DcMotor tapeMeasure, arm;

    public Servo grabber1, grabber2;

    /*
    Config
     */
    private final double servoSpeed = .01d;
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

        grabber1 = hardwareMap.servo.get("grabber1");
        grabber2 = hardwareMap.servo.get("grabber2");

    }

    public void extend(double speed) {
        tapeMeasure.setPower(speed);
    }

    public void retract(double speed) {
        tapeMeasure.setPower(-speed);
    }

    public void grab() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), closedPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
    }

    public void release() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), openPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
    }

}
