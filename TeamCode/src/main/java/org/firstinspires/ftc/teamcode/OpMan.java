package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "WorkingDrive")
public class OpMan extends OpMode {

    private DcMotor frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
    //private DcMotor leftLiftMotor, rightLiftMotor, winchMotor;
    //private Servo servo;

    private double leftDrivePower, rightDrivePower;
    private double leftLiftPower, rightLiftPower;
    private double winchPower;

    private boolean driveSlow;
    private boolean liftSlow;

    // SENSORS //
    private TouchSensor topTouch;
    private TouchSensor bottomTouch;
    // SENSORS //

    // CONSTANTS //
    private final double liftPowerMult = 0.25d;
    private final double baseLiftSpeed = 0.5d;
    private final double drivePowerMult = .25d;
    private final double baseDriveSpeed = 0.95d;
    // CONSTANTS //

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "rear_left");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right");
        rearRightMotor = hardwareMap.get(DcMotor.class, "rear_right");

        //leftLiftMotor = hardwareMap.get(DcMotor.class, "left_lift");
        //rightLiftMotor = hardwareMap.get(DcMotor.class, "right_lift");
        //winchMotor = hardwareMap.get(DcMotor.class, "winch");

        //servo = hardwareMap.get(Servo.class, "servo");

        topTouch = hardwareMap.touchSensor.get("top_touch");
        bottomTouch = hardwareMap.touchSensor.get("bottom_touch");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

        liftSlow = false;
        driveSlow = false;

        if (gamepad2.right_bumper || gamepad2.left_bumper)
            liftSlow = true;
        if (gamepad1.right_bumper || gamepad1.left_bumper)
            driveSlow = true;

        leftDrivePower = gamepad1.left_stick_y * baseDriveSpeed;
        rightDrivePower = gamepad1.right_stick_y * baseDriveSpeed;

        if (gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0) {
            leftLiftPower = gamepad2.left_stick_y * baseLiftSpeed;
            rightLiftPower = gamepad2.right_stick_y * baseLiftSpeed;
        } else {
            double pwr = 0;

            if (gamepad2.y) {
                pwr = -baseLiftSpeed;
            } else if (gamepad2.a) {
                pwr = baseLiftSpeed;
            }

            leftLiftPower = pwr;
            rightLiftPower = pwr;
        }

        double winchControls = gamepad2.right_trigger - gamepad2.left_trigger;
        if (topTouch.isPressed() && winchControls > 0) {
            winchPower = 0;
        } else if (bottomTouch.isPressed() && winchControls < 0) {
            winchPower = 0;
        } else {
            winchPower = (gamepad2.right_trigger - gamepad2.left_trigger);
        }

        if (driveSlow) {
            leftDrivePower *= drivePowerMult;
            rightDrivePower *= drivePowerMult;
        }

        if (liftSlow) {
            leftLiftPower *= liftPowerMult;
            rightLiftPower *= liftPowerMult;
            winchPower *= liftPowerMult;
        }

        frontLeftMotor.setPower(leftDrivePower);
        rearLeftMotor.setPower(leftDrivePower);

        frontRightMotor.setPower(rightDrivePower);
        rearRightMotor.setPower(rightDrivePower);

        //leftLiftMotor.setPower(leftLiftPower);
        //rightLiftMotor.setPower(rightLiftPower);

        //winchMotor.setPower(winchPower);

        if (gamepad2.b) {
            //servo.setPosition(1);
        } else {
            //servo.setPosition(0);
        }

        telemetry();

    }

    private void telemetry() {
        telemetry.addData("B",String.valueOf(gamepad2.b));
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 2", "Left Lift: (%.2f), Right Lift: (%.2f), Winch: (%.2f)", leftLiftPower, rightLiftPower, winchPower);
    }

}
