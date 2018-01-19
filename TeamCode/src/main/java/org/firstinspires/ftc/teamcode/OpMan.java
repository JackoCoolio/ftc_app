package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "The one that works")
public class OpMan extends OpMode {

    // POWER VARIABLES FOR DEBUGGING //
    private double leftDrivePower, rightDrivePower;
    private double leftLiftPower, rightLiftPower;
    private double winchPower;
    // POWER VARIABLES FOR DEBUGGING //

    // CONTROL BOOLEANS //
    private boolean driveSlow;
    // CONTROL BOOLEANS //

    // CONSTANTS //
//  private final double liftPowerMult = 0.25d;
    private final double baseLiftSpeed = 0.35d;
    private final double drivePowerMult = .25d;
    private final double baseDriveSpeed = 0.95d;
    // CONSTANTS //

    // HARDWARE //
    Robot robot;
    // HARDWARE //

    @Override
    public void init() {

        robot = new Robot(hardwareMap); // Initialize the robot hardware.

    }

    @Override
    public void loop() {

        driveSlow = false; // Reset boolean to false every cycle.

        if (gamepad1.right_bumper || gamepad1.left_bumper) // Check if driver is pressing either bumper (slow-mode)
            driveSlow = true;

        leftDrivePower = gamepad1.left_stick_y * baseDriveSpeed * -1; // Calculate left drive speed.
        rightDrivePower = gamepad1.right_stick_y * baseDriveSpeed * -1; // Calculate right drive speed.

        if (gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0) { // Separate lift-control sticks allow for individual lift motor tweaking.
            leftLiftPower = gamepad2.left_stick_y * baseLiftSpeed * -1;
            rightLiftPower = gamepad2.right_stick_y * baseLiftSpeed * -1;
        } else { // If neither lift-control stick is active, allow the Y & A buttons to act as controls.
            double pwr = 0;

            if (gamepad2.y) {
                pwr = -baseLiftSpeed;
            } else if (gamepad2.a) {
                pwr = baseLiftSpeed;
            }

            leftLiftPower = pwr;
            rightLiftPower = pwr;
        }

        if (gamepad2.dpad_up) { // D-Pad controls are used for lift.
            winchPower = .95;
        } else if (gamepad2.dpad_down) {
            winchPower = -.95;
        } else {
            winchPower = 0;
        }

        if (driveSlow) { // If slow-mode is on, multiply drive speed.
            leftDrivePower *= drivePowerMult;
            rightDrivePower *= drivePowerMult;
        }

        robot.lift.setPower(winchPower); // Set power for all motors using values calculated above.

        robot.leftMotors.setPower(leftDrivePower);
        robot.rightMotors.setPower(rightDrivePower);

        robot.liftMotors.getMotor("left_lift").setPower(leftLiftPower);
        robot.liftMotors.getMotor("right_lift").setPower(rightLiftPower);

        telemetry(); // Do telemetry in a separate method for easier readability.

    }

    private void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 2", "Left Lift: (%.2f), Right Lift: (%.2f), Winch: (%.2f)", leftLiftPower, rightLiftPower, winchPower);
    }

}
