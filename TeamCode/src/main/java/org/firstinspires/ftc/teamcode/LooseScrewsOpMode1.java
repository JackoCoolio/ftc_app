package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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

    //Telemetry
//    private ElapsedTime runtime = new ElapsedTime();

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

    private Servo servo;

    private double leftPower;
    private double rightPower;

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

        servo = hardwareMap.get(Servo.class, "servo");

    }

    @Override
    public void loop() {

        leftPower = 0;
        rightPower = 0;

        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        liftMotors.setPower(gamepad2.left_stick_y, "left_lift");
        liftMotors.setPower(gamepad2.right_stick_y, "right_lift");

        servo.setPosition(gamepad2.right_trigger);

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
        telemetry.addData("Runtime", getRuntime());
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Gamepad 1"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
        telemetry.addData("Gamepad 2:","Left: " + String.valueOf(gamepad2.left_stick_y) + " Right: " + String.valueOf(gamepad2.right_stick_y));
    }

    @Deprecated
    private void setAllPower(double pwr, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setPower(pwr);
        }
    }

}

class MotorGroup {

    private ArrayList<String> motorNames;
    private HashMap<String, DcMotor> motors;

    protected MotorGroup(String... _names) {
        motorNames = new ArrayList<>(Arrays.asList(_names));
        motors = new HashMap<>();
    }

    protected MotorGroup initMotors(HardwareMap _hm) {
        for (String m : motorNames)
            motors.put(m, _hm.get(DcMotor.class, m));
        motorNames.clear();
        return this; // Returns itself for easy chaining
    }

    protected MotorGroup setDirection(DcMotor.Direction _dir) {
        for (DcMotor motor : motors.values()) {
            motor.setDirection(_dir);
        }
        return this; // Returns itself for easy chaining
    }

    protected void setPower(double _power) {
        for (DcMotor motor : motors.values()) {
            motor.setPower(_power);
        }
    }

    protected void setPower(double power, String... _motors) {
        for (String m : _motors)
            motors.get(m).setPower(power);
    }

    protected DcMotor getMotor(String _name) {
        return motors.get(_name);
    }

}