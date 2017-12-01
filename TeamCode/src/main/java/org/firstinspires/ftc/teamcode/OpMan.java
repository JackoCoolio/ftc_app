package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jacktwamb52 on 12/1/2017.
 */

@TeleOp(name = "WorkingDrive")
public class OpMan extends OpMode {

    DcMotor frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    DcMotor leftLiftMotor, rightLiftMotor, winchMotor;
    Servo servo;

    double leftDrivePower, rightDrivePower;
    double leftLiftPower, rightLiftPower;
    double winchPower;

    boolean driveSlow = false;
    boolean liftSlow = false;


    // CONSTANTS //
    private final double liftPowerMult = 0.5d;
    private final double drivePowerMult = 0.95d;
    // CONSTANTS //

    @Override
    public void init() {
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

    @Override
    public void loop() {

        liftSlow = false;
        driveSlow = false;

        if (gamepad2.right_bumper || gamepad2.left_bumper)
            liftSlow = true;
        if (gamepad1.right_bumper || gamepad1.left_bumper)
            driveSlow = true;

        leftDrivePower = gamepad1.left_stick_y * drivePowerMult;
        rightDrivePower = gamepad1.right_stick_y * drivePowerMult;

        if (gamepad2.left_stick_y > 0 || gamepad2.right_stick_y > 0) {
            leftLiftPower = gamepad2.left_stick_y * liftPowerMult;
            rightLiftPower = gamepad2.right_stick_y * liftPowerMult;
        } else {
            double pwr = 0;

            if (gamepad2.y) {
                pwr = liftPowerMult;
            } else if (gamepad2.a) {
                pwr = -liftPowerMult;
            }

            leftLiftPower = pwr;
            rightLiftPower = pwr;
        }

        winchPower = (gamepad2.right_trigger - gamepad2.left_trigger) * liftPowerMult;

        if (driveSlow) {
            leftDrivePower /= 2;
            rightDrivePower /= 2;
        }

        if (liftSlow) {
            leftLiftPower /= 2;
            rightLiftPower /= 2;
            winchPower /= 2;
        }

        frontLeftMotor.setPower(leftDrivePower);
        rearLeftMotor.setPower(leftDrivePower);

        frontRightMotor.setPower(rightDrivePower);
        rearRightMotor.setPower(rightDrivePower);

        leftLiftMotor.setPower(leftLiftPower);
        rightLiftMotor.setPower(rightLiftPower);

        //winchMotor.setPower(winchPower * 0.5);

        if (gamepad2.b) {
            servo.setPosition(1.0);
        } else {
            servo.setPosition(0.0);
        }

        telemetry();

    }

    private void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 2", "Left Lift: (%.2f), Right Lift: (%.2f), Winch: (%.2f)", leftLiftPower, rightLiftPower, winchPower);
    }

}
