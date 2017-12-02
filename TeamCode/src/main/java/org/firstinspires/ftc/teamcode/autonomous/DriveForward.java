package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by jacktwamb52 on 12/1/2017.
 */

public class DriveForward extends LinearOpMode {

    DcMotor frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    DcMotor leftLiftMotor, rightLiftMotor, winchMotor;
    Servo servo;

    ElapsedTime runtime = new ElapsedTime();

    private final double driveTime = 2.5;

    @Override
    public void runOpMode() {

        initHardware();

        while (opModeIsActive() && runtime.seconds() < driveTime) {
            frontLeftMotor.setPower(0.95);
            rearLeftMotor.setPower(0.95);

            frontRightMotor.setPower(0.95);
            rearRightMotor.setPower(0.95);
        }

    }

    private void initHardware() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "rear_left");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right");
        rearRightMotor = hardwareMap.get(DcMotor.class, "rear_right");

        leftLiftMotor = hardwareMap.get(DcMotor.class, "left_lift");
        rightLiftMotor = hardwareMap.get(DcMotor.class, "right_lift");
        //winchMotor = hardwareMap.get(DcMotor.class, "winch");

        servo = hardwareMap.get(Servo.class, "servo");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
