package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    //Hardware
    private DcMotor left_front_drive;
    private DcMotor right_front_drive;

    private DcMotor left_rear_drive;
    private DcMotor right_rear_drive;

    private DcMotor left_lift;
    private DcMotor right_lift;

    private MotorGroup leftMotors;
    private MotorGroup rightMotors;
    private MotorGroup liftMotors;

    private double leftPower;
    private double rightPower;

    //Other
    private DriveMode mode = DriveMode.Tank;

    @Override
    public void init() {

        telemetry.addData("Status","Initialized");

        leftMotors = new MotorGroup("front_left","rear_left")
                .initMotors(hardwareMap)
                .setDirection(DcMotor.Direction.REVERSE);

        rightMotors = new MotorGroup("front_right","rear_right")
                .initMotors(hardwareMap);

        liftMotors = new MotorGroup("left_lift","right_lift")
                .initMotors(hardwareMap);

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

        liftMotors.setPower(gamepad1.left_trigger, "left_lift");
        liftMotors.setPower(gamepad1.right_trigger, "right_lift");

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0 && gamepad1.a) { // If motors aren't being controlled independently, allow independent control.
            liftMotors.setPower(1);
        }

        leftMotors.setPower(leftPower);
        rightMotors.setPower(rightPower);

        setTelemetry();

    }

    @Deprecated
    private void setLeftPower(double pwr) {
        left_front_drive.setPower(pwr);
        left_rear_drive.setPower(pwr);
    }

    @Deprecated
    private void setRightPower(double pwr) {
        right_front_drive.setPower(pwr);
        right_rear_drive.setPower(pwr);
    }

    private void setTelemetry() {
        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", runtime.toString());
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftPower, rightPower);
        //if (debug)
            telemetry.addData("Controls"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
    }

    private void setAllPower(double pwr, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setPower(pwr);
        }
    }

}

class MotorGroup {

    private ArrayList<String> motorNames;
    private HashMap<String, DcMotor> motors;

    public MotorGroup() {
        motorNames = new ArrayList<>();
        motors = new HashMap<>();
    }

    public MotorGroup(String... names) {
        motorNames = new ArrayList<>(Arrays.asList(names));
        motors = new HashMap<>();
    }

    public void addMotors(String... _motorNames) {
        for (String m : _motorNames)
            motorNames.add(m);
    }

    public MotorGroup initMotors(HardwareMap hm) {
        for (String m : motorNames)
            motors.put(m, hm.get(DcMotor.class, m));
        motorNames.clear();
        return this; // Returns itself for easy chaining
    }

    public MotorGroup setDirection(DcMotor.Direction dir) {
        for (DcMotor motor : motors.values()) {
            motor.setDirection(dir);
        }
        return this; // Returns itself for easy chaining
    }

    public void setPower(double power) {
        for (DcMotor motor : motors.values()) {
            motor.setPower(power);
        }
    }

    public void setPower(double power, String... _motors) {
        for (String m : _motors)
            motors.get(m).setPower(power);
    }

    public DcMotor getMotor(String name) {
        return motors.get(name);
    }

}