package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;

/*

    HARDWARE:
    -left_front, left_rear
    -right_front, right_rear
    -arm
    -grabber1, grabber2
    -tape_measure

 */

@TeleOp(name="Layla")
public class Layla extends OpMode {

    private enum GrabberStatus {
        Grabbing, Releasing, Idle
    }

    TapeMeasureRobot robot;

    // TELEMETRY DATA //
    private double leftPower, rightPower, extendSpeed;
    private boolean extending, grabbing, releasing;

    @Override
    public void init() {

        robot = new TapeMeasureRobot(hardwareMap);

    }

    @Override
    public void loop() {

        // Gamepad 1 Controls
        robot.leftDrive.setPower(-gamepad1.left_stick_y);
        robot.rightDrive.setPower(-gamepad1.right_stick_y);
        // Gamepad 1 Controls

        // Gamepad 2 Controls
        robot.arm.setPower(-gamepad2.right_stick_y);

        if (gamepad2.right_trigger >= .1) {
            robot.grab();
            grabbing = true;
            releasing = false;
        } else {
            robot.release();
            grabbing = false;
            releasing = true;
        }

        if (-gamepad2.left_stick_y > .1) {
            robot.extend(Math.abs(gamepad2.left_stick_y));
        } else if (-gamepad2.left_stick_y < .1) {
            robot.retract(Math.abs(gamepad2.left_stick_y));
        }
        // Gamepad 2 Controls

        // Telemetry
        telemetry();
        // Telemetry

    }

    private void telemetry() {

        telemetry.addData("Driving", "Left = " + leftPower + ", Right = " + rightPower);

        GrabberStatus status;
        if (grabbing)
            status = GrabberStatus.Grabbing;
        else if (releasing)
            status = GrabberStatus.Releasing;
        else
            status = GrabberStatus.Idle;

        telemetry.addData("Grabber", status.toString());

    }
}
