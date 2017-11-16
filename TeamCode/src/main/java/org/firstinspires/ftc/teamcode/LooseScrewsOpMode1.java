package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/*
USING "PHONE 4" AND "RC PHONE"

Controller 0 is top
1 is bottom


*/

@TeleOp(name="Drive", group="Drive Test")
public class LooseScrewsOpMode1 extends OpMode {

    //Telemetry
//    private ElapsedTime runtime = new ElapsedTime();

    //Hardware
//    private DcMotor left_front_drive;
//    private DcMotor right_front_drive;
//
//    private DcMotor left_rear_drive;
//    private DcMotor right_rear_drive;
//
//    private DcMotor left_lift;
//    private DcMotor right_lift;

    private Robot robot;

    private double leftPower;
    private double rightPower;

    @Override
    public void init() {

        robot = new Robot();
        robot.init(hardwareMap);

        telemetry.addData("Status","Initialized");

    }

    @Override
    public void loop() {

        leftPower = 0;
        rightPower = 0;

        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        robot.liftMotors.setPower(gamepad2.left_stick_y, "left_lift");
        robot.liftMotors.setPower(gamepad2.right_stick_y, "right_lift");

        robot.servo.setPosition(gamepad2.right_trigger);

        robot.leftMotors.setPower(leftPower);
        robot.rightMotors.setPower(rightPower);

        setTelemetry();

    }

    private void setTelemetry() {
        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", getRuntime());
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Gamepad 1"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
        telemetry.addData("Gamepad 2:","Left: " + String.valueOf(gamepad2.left_stick_y) + " Right: " + String.valueOf(gamepad2.right_stick_y));
    }

}

