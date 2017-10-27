package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by jacktwamb52 on 9/29/2017.
 */

/*
USING "PHONE 4" AND "RC PHONE"

Controller 0 is top
1 is bottom


*/

@TeleOp(name="Drive", group="Drive Test")
public class LooseScrewsOpMode1 extends OpMode {

    enum DriveMode {
        Tank,
        POV
    }

    private boolean debug = true;

    //Telemetry
    private ElapsedTime runtime = new ElapsedTime();

    //Hardware Directions
    private DcMotorSimple.Direction left_drive_dir;
    private DcMotorSimple.Direction right_drive_dir;

    //Hardware
    private DcMotor left_front_drive;
    private DcMotor right_front_drive;
    private DcMotor left_rear_drive;
    private DcMotor right_rear_drive;
    public double leftPower;
    public double rightPower;

    //Other
    private DriveMode mode = DriveMode.Tank;

    @Override
    public void init() {

        telemetry.addData("Status","Initialized");

        getHardware();
        left_front_drive.setDirection(DcMotor.Direction.REVERSE);
        left_rear_drive.setDirection(DcMotor.Direction.REVERSE);

        getHardware();

    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        leftPower = 0;
        rightPower = 0;

        if (mode == DriveMode.POV) {
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);
        } else if (mode == DriveMode.Tank) {
            leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;
        }

        setLeftPower(leftPower);
        setRightPower(rightPower);

        setTelemetry();

    }

    private void getHardware() {
        left_front_drive = hardwareMap.get(DcMotor.class, "left_front");
        right_front_drive = hardwareMap.get(DcMotor.class, "right_front");
        left_rear_drive = hardwareMap.get(DcMotor.class, "left_rear");
        right_rear_drive = hardwareMap.get(DcMotor.class, "right_rear");
    }

    private void setLeftPower(double pwr) {
        left_front_drive.setPower(pwr);
        left_rear_drive.setPower(pwr);
    }

    private void setRightPower(double pwr) {
        right_front_drive.setPower(pwr);
        right_rear_drive.setPower(pwr);
    }

    private void setTelemetry() {
        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        //if (debug)
            telemetry.addData("Controls"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
    }

    private void lift() {
        //lift that shit
    }

    private void poop() {
        //poop that shit
    }

}