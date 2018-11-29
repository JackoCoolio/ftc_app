package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;

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

    double[] values;
    @Override
    public void loop() {

        // Gamepad 1 Controls
        robot.leftDrive.setPower(-gamepad1.left_stick_y);
        robot.rightDrive.setPower(-gamepad1.right_stick_y);
        // Gamepad 1 Controls

        // Gamepad 2 Controls
        robot.arm.setPower(-gamepad2.right_stick_y*.5d);

//        if (gamepad2.right_trigger >= .1) {
//            robot.grab();
//            grabbing = true;
//            releasing = false;
//        } else {
//            values = robot.release();
//            grabbing = false;
//            releasing = true;
//        }

        robot.grabber1.setPosition(robot.mapServoPosition(gamepad2.right_trigger, robot.grabber1_open, robot.grabber1_closed));
        robot.grabber2.setPosition(robot.mapServoPosition(gamepad2.right_trigger, robot.grabber2_open, robot.grabber2_closed));

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

        telemetry.addData("Servo", "g1: " + robot.grabber1.getPosition() + ", g2: " + robot.grabber2.getPosition());
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
