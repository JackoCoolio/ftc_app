package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;

/*
    This was the main OpMode from our 2017-18 season.
 */
@Disabled
@TeleOp(name = "The one that works")
public class OpMan extends OpMode {

    // POWER VARIABLES FOR DEBUGGING //
    private double leftDrivePower, rightDrivePower;
    private double leftLiftPower, rightLiftPower;
    private double winchPower;
    private double relicArmMotorPower;
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
    GlyphRobot glyphRobot;
    // HARDWARE //

    @Override
    public void init() {

        glyphRobot = new GlyphRobot(hardwareMap); // Initialize the glyphRobot hardware.

    }

    @Override
    public void loop() {

        if (gamepad1.start) glyphRobot.init();

        driveSlow = false; // Reset boolean to false every cycle.

        if (gamepad1.right_bumper || gamepad1.left_bumper) // Check if driver is pressing either bumper (slow-mode)
            driveSlow = true;

        if (gamepad1.y) {
            leftDrivePower = baseDriveSpeed;
            rightDrivePower = baseDriveSpeed;
        } else if (gamepad1.a) {
            leftDrivePower = -baseDriveSpeed;
            rightDrivePower = -baseDriveSpeed;
        } else {
            leftDrivePower = gamepad1.left_stick_y * baseDriveSpeed * -1; // Calculate left drive speed.
            rightDrivePower = gamepad1.right_stick_y * baseDriveSpeed * -1; // Calculate right drive speed.
        }

        if (gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0) { // Separate lift-control sticks allow for individual lift motor tweaking.
            leftLiftPower = gamepad2.left_stick_y * baseLiftSpeed * -1;
            rightLiftPower = gamepad2.right_stick_y * baseLiftSpeed * -1;
        } else { // If neither lift-control stick is active, allow the Y & A buttons to act as controls.
            double pwr = 0;

            if (gamepad2.y) {
                pwr = baseLiftSpeed;
            } else if (gamepad2.a) {
                pwr = -baseLiftSpeed;
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

        /*
         if (gamepad2.back) {
            glyphRobot.relicArm.setMode(RelicArm.Mode.Extend);
         } else if (gamepad2.start) {
             glyphRobot.relicArm.setMode(RelicArm.Mode.Pivot);
         }


        relicArmMotorPower = -gamepad2.right_trigger + gamepad2.left_trigger;


        if (gamepad2.b) {
            glyphRobot.relicArm.close();
        } else if (gamepad2.x) {
            glyphRobot.relicArm.open();
        }
        */

        if (driveSlow) { // If slow-mode is on, multiply drive speed.
            leftDrivePower *= drivePowerMult;
            rightDrivePower *= drivePowerMult;
        }

        //glyphRobot.relicArm.motor(relicArmMotorPower, telemetry);

        glyphRobot.lift.setPower(winchPower); // Set power for all motors using values calculated above.

        glyphRobot.leftMotors.setPower(leftDrivePower);
        glyphRobot.rightMotors.setPower(rightDrivePower);

        glyphRobot.liftMotors.getMotor("left_lift").setPower(leftLiftPower);
        glyphRobot.liftMotors.getMotor("right_lift").setPower(rightLiftPower);

        telemetry(); // Do telemetry in a separate method for easier readability.

    }

    private void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 2", "Left Lift: (%.2f), Right Lift: (%.2f), Winch: (%.2f)", leftLiftPower, rightLiftPower, winchPower);
    }

}
