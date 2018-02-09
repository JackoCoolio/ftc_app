package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    static final double COUNTS_PER_REV = 560;
    static final double DRIVE_GEAR_REDUCTION = 2; // ?
    static final double WHEEL_DIAMETER_INCHES = 3.75;

    public ElapsedTime timer;

    public MotorGroup leftMotors, rightMotors, liftMotors, lift;
    public Servo lrServo, udServo;

    public ColorSensor colorSensor;
    public RelicArm relicArm;
    private HardwareMap hardwareMap;

    public Robot(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        timer = new ElapsedTime(15);

        //this.relicArm = new RelicArm(hardwareMap);

        init();

    }

    public void init() {
        if(timer.seconds() < 10) return;
        timer.reset();

        lift = new MotorGroup(hardwareMap,"lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotors = new MotorGroup(hardwareMap, "front_left", "rear_left");
        rightMotors = new MotorGroup(hardwareMap, "front_right", "rear_right");
        (liftMotors = new MotorGroup(hardwareMap, "left_lift", "right_lift")).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotors.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotors.setDirection((DcMotorSimple.Direction.REVERSE));

        MotorGroup.EncoderParameters params = new MotorGroup.EncoderParameters(
                COUNTS_PER_REV,
                DRIVE_GEAR_REDUCTION,
                WHEEL_DIAMETER_INCHES
        );
        leftMotors.setEncoderParameters(params);
        rightMotors.setEncoderParameters(params);

        liftMotors.getMotor("left_lift").setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotors.getMotor("right_lift").setDirection(DcMotorSimple.Direction.REVERSE);

        lrServo = hardwareMap.servo.get("left_right");
        udServo = hardwareMap.servo.get("up_down");
        colorSensor = hardwareMap.colorSensor.get("color");
    }

}
