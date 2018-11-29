package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;
import org.firstinspires.ftc.teamcode.utility.Utility;

/*

    HARDWARE:
    -left_front, left_rear
    -right_front, right_rear
    -arm
    -grabber1, grabber2
    -tape_measure

 */

public class TapeMeasureRobot extends Robot {

    public MotorGroup leftDrive, rightDrive;
    public DcMotor tapeMeasure, arm;

    public Servo grabber1, grabber2;

    /*
    Config
     */
    public final double servoSpeed = .01d;
//    public final double openPosition = 1d;
//    public final double closedPosition = .5d;

    public final double grabber1_closed = 1d;
    public final double grabber1_open = 0d;

    public final double grabber2_closed = 1d;
    public final double grabber2_open = 0d;
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
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        tapeMeasure = hardwareMap.dcMotor.get("tape_measure");

        arm = hardwareMap.dcMotor.get("arm");
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        grabber1 = hardwareMap.servo.get("grabber1");
        grabber2 = hardwareMap.servo.get("grabber2");

        grabber1.setDirection(Servo.Direction.REVERSE);

        grabber1.setPosition(grabber1_open);
        grabber2.setPosition(grabber2_open);



    }

    public double mapServoPosition(double value, double min, double max) {
        return value*(max-min) + min;
    }

    public void extend(double speed) {
        tapeMeasure.setPower(speed);
    }

    public void retract(double speed) {
        tapeMeasure.setPower(-speed);
    }

//    public void grab() {
//        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), closedPosition, servoSpeed));
//        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
//    }

//    public double[] release() {
//        double p1, p2;
//        p1 = Utility.lerp(grabber1.getPosition(), openPosition, servoSpeed);
//        p2 = Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed);
//        grabber1.setPosition(p1);
//        grabber2.setPosition(p2);
//        return new double[] {p1,p2};
//    }

}
