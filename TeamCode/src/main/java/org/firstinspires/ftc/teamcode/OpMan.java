package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

@TeleOp(name = "The one that works")
public class OpMan extends OpMode {

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
        -gyro?
    */

    private MotorGroup leftMotors;
    private MotorGroup rightMotors;
    private MotorGroup liftMotors;
    private DcMotor lift;

    private double leftDrivePower, rightDrivePower;
    private double leftLiftPower, rightLiftPower;
    private double winchPower;

    private boolean driveSlow;

    // SENSORS //
    //private TouchSensor topTouch;
    //private TouchSensor bottomTouch;
    // SENSORS //

    // CONSTANTS //
//  private final double liftPowerMult = 0.25d;
    private final double baseLiftSpeed = 0.35d;
    private final double drivePowerMult = .25d;
    private final double baseDriveSpeed = 0.95d;
    // CONSTANTS //

    @Override
    public void init() {

        lift = hardwareMap.dcMotor.get("lift");

        leftMotors = new MotorGroup(hardwareMap, "front_left", "rear_left");
        rightMotors = new MotorGroup(hardwareMap, "front_right", "rear_right");
        (liftMotors = new MotorGroup(hardwareMap, "left_lift", "right_lift")).setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotors.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotors.getMotor("left_lift").setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

        driveSlow = false;

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

//        if (topTouch.isPressed() && winchControls > 0) {
//            winchPower = 0;
//        } else if (bottomTouch.isPressed() && winchControls < 0) {
//            winchPower = 0;
//        } else {
        if (gamepad2.dpad_up) {
            winchPower = 1;
        } else if (gamepad2.dpad_down) {
            winchPower = -1;
        } else {
            winchPower = 0;
        }
//        }

        if (driveSlow) {
            leftDrivePower *= drivePowerMult;
            rightDrivePower *= drivePowerMult;
        }

        lift.setPower(winchPower);

        leftMotors.setPower(leftDrivePower);
        rightMotors.setPower(rightDrivePower);

        liftMotors.getMotor("left_lift").setPower(leftLiftPower);
        liftMotors.getMotor("right_lift").setPower(rightLiftPower);

        telemetry();

    }

    private void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 2", "Left Lift: (%.2f), Right Lift: (%.2f), Winch: (%.2f)", leftLiftPower, rightLiftPower, winchPower);
    }

}
