package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 1/9/2018.
 */

public class Robot {

    /*
    Mapping:
      Drivetrain:
        -front_left
        -front_right
        -rear_left
        -rear_right
      Lift:
        -lift
        -left_lift
        -right_lift
      Servo arm:
        -up_down
        -left_right
      Sensors:
        -color
        -imu
    */

    public MotorGroup leftMotors, rightMotors, liftMotors;
    public DcMotor lift;

    public Servo lrServo, udServo;

    public ColorSensor colorSensor;

    private HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        init();

    }

    private void init() {
        lift = hardwareMap.dcMotor.get("lift");

        leftMotors = new MotorGroup(hardwareMap, "front_left", "rear_left");
        rightMotors = new MotorGroup(hardwareMap, "front_right", "rear_right");
        (liftMotors = new MotorGroup(hardwareMap, "left_lift", "right_lift")).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotors.getMotor("front_left").setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotors.getMotor("front_right").setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotors.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotors.setDirection((DcMotorSimple.Direction.REVERSE));
        liftMotors.getMotor("left_lift").setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotors.getMotor("right_lift").setDirection(DcMotorSimple.Direction.REVERSE);

        lrServo = hardwareMap.servo.get("left_right");
        udServo = hardwareMap.servo.get("up_down");

        colorSensor = hardwareMap.colorSensor.get("color");
    }

}
