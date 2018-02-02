package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
/**
 * Basically a HashMap with some added DcMotor functionality.
 */
public class MotorGroup {

    private HashMap<String, DcMotor> motors;
    private HashMap<String, EncoderParameters> motorParams;
    private ElapsedTime timer;
    public boolean drivingByEncoder = false;
    private HashMap<DcMotor, Integer> targetPositions;
    private DcMotor trackMotor;
    int newTarget;

    public MotorGroup(HardwareMap _hm, String... _names) {
        motors = new HashMap<>();
        motorParams = new HashMap<>();
        timer = new ElapsedTime();

        for (String m : _names)
            motors.put(m, _hm.dcMotor.get(m));
    }

    public MotorGroup setDirection(DcMotor.Direction _dir) {
        for (DcMotor motor : motors.values()) {
            motor.setDirection(_dir);
        }
        return this; // Returns itself for easy chaining.
    }

    public void flip() {
        for (DcMotor motor : motors.values()) {
            if (motor.getDirection().equals(DcMotorSimple.Direction.FORWARD))
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
            else
                motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public void setPower(double _power) {
        for (DcMotor motor : motors.values()) {
            motor.setPower(_power);
        }
    }

    public void setPower(double power, String... _motors) {
        for (String m : _motors)
            motors.get(m).setPower(power);
    }

    public void zero() {
        for (DcMotor m : motors.values()) {
            m.setPower(0d);
        }
    }

    public DcMotor getMotor(String _name) {
        return motors.get(_name);
    }

    public MotorGroup setMode(DcMotor.RunMode mode) {
        for (DcMotor motor : motors.values()) {
            motor.setMode(mode);
        }
        return this; // Returns itself for easy chaining.
    }

    public void setEncoderParameters(EncoderParameters params) {
        for (String name : motors.keySet()) {
            motorParams.put(name, params);
        }
    }

    public void setEncoderParameters(EncoderParameters params, String motor) {
        motorParams.put(motor, params);
    }

    public boolean isBusy() {
        int busyCount = 0;
        for (DcMotor motor : motors.values()) {
            if (motor.isBusy()) busyCount++;
        }
        return (busyCount > 0);
    }

    public int getBusyCount() {
        int busyCount = 0;
        for (DcMotor motor : motors.values()) {
            if (motor.isBusy()) busyCount++;
        }
        return busyCount;
    }

    public void busyDebug(Telemetry telemetry) {
        for (String name : motors.keySet()) {
            telemetry.addData(name, (motors.get(name).isBusy())? "Busy" : "Not Busy");
        }
    }

    public void stopRunToPosition() {
        zero();
    }

//    public boolean encoderDrive(double speed, double inches, double minimumDriveTime, double timeoutS, String trackMotorName, Telemetry telemetry)
//    {
//        if (motorParams.size() != motors.size()) {
//            telemetry.addData("EncoderDrive","ERROR - EncoderParameters not set!");
//            return false;
//        }
//        if (!drivingByEncoder) { // First section run
//            telemetry.addData("EncoderDrive","Starting encoderDrive()");
//            drivingByEncoder = true;
//            trackMotor = getMotor(trackMotorName);
//            newTarget = trackMotor.getCurrentPosition() + (int)(inches * motorParams.get(trackMotorName).COUNTS_PER_INCH);
//
//            for (String motor : motors.keySet()) {
//                telemetry.addData("EncoderDrive","Setting target position for " + motor);
//                motors.get(motor).setTargetPosition(motors.get(motor).getCurrentPosition() + (int)(inches * motorParams.get(motor).COUNTS_PER_INCH));
//            }
//
//            setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            telemetry.addData("EncoderDrive", "Reset timer!");
//            timer.reset();
//            setPower(Math.abs(speed));
//        }
//
//        if (timer.seconds() < timeoutS && getBusyCount() < motors.size()) {
//            if (telemetry != null) {
//                telemetry.addData("Path1",  "Running to %7d", newTarget);
//                for (String name : motors.keySet()) {
//                    telemetry.addData(name,motors.get(name).getCurrentPosition());
//                }
//            }
//            return false;
//        } else if (timer.seconds() > minimumDriveTime) {
//            if (timer.seconds() > timeoutS) telemetry.addData("EncoderDrive","Timed out!");
//            if (!isBusy()) telemetry.addData("EncoderDrive", "Motors finished driving!");
//            zero();
//            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            drivingByEncoder = false;
//            telemetry.addData("EncoderDrive","Returning true");
//            return true;
//        }
//        return false;
//    }

    public boolean encoderDriveTest(double speed, double inches, double timeoutS, Telemetry telemetry)
    {
        telemetry.clear();
        telemetry.addData("EncoderDrive","Speed: " + speed + ", Inches: " + inches);
        if (motorParams.size() != motors.size()) {
            telemetry.addData("EncoderDrive","ERROR - EncoderParameters not set!");
            return false;
        }
        if (!drivingByEncoder) { // First section run
            telemetry.addData("EncoderDrive","Starting encoderDrive()");
            drivingByEncoder = true;

            setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            targetPositions = new HashMap<>();
            for (String motor : motors.keySet()) {
                telemetry.addData("EncoderDrive","Setting target position for " + motor);
                targetPositions.put(motors.get(motor), motors.get(motor).getCurrentPosition() + (int)(inches * motorParams.get(motor).COUNTS_PER_INCH));
            }

            telemetry.addData("EncoderDrive", "Reset timer!");
            timer.reset();
            if (inches > 0)
                setPower(Math.abs(speed));
            else
                setPower(-Math.abs(speed));
        }

        int finishedCount = 0;
        for (DcMotor motor : motors.values()) { // Check finished.
            if (inches < 0) {
                if (motor.getCurrentPosition() < targetPositions.get(motor)) {
                    finishedCount++;
                }
            } else {
                if (motor.getCurrentPosition() > targetPositions.get(motor)) {
                    finishedCount++;
                }
            }
        }

        for (String motorName : motors.keySet()) { // Telemetry
            DcMotor motor = motors.get(motorName);
            int motorPos = motor.getCurrentPosition();
            int targetPos = targetPositions.get(motor);
            telemetry.addData(motorName, "(" + motorPos + " / " + targetPos + ") " + (motorPos/targetPos*100) + " percent complete.");
        }

        if (finishedCount == motors.size() || timer.seconds() > timeoutS) {
            if (timer.seconds() > timeoutS) telemetry.addData("EncoderDrive","Timed out!");
            else telemetry.addData("EncoderDrive","Motors finished driving!");
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivingByEncoder = false;
        } else {
            telemetry.addData("Path1",  "Running to %7d", newTarget);
            for (String name : motors.keySet()) {
                telemetry.addData(name,motors.get(name).getCurrentPosition());
            }
        }

        return !drivingByEncoder;
    }

    public static boolean runAndStopIfFinished(double speed, double inches, double timeoutS, Telemetry telemetry, MotorGroup... groups) {
        boolean allFinished = false;
        for (MotorGroup group : groups) {
            boolean finished = group.encoderDriveTest(speed, inches, timeoutS, telemetry);
            allFinished = (finished) || allFinished;
        }
        if (allFinished) {
            for (MotorGroup group : groups)
                group.stopRunToPosition();
        }
        return allFinished;
    }

    public static class EncoderParameters {

        final double COUNTS_PER_INCH;

        public EncoderParameters(double countsPerRev, double driveGearReduction, double wheelDiameterInches) {
            COUNTS_PER_INCH = (countsPerRev * driveGearReduction) / (wheelDiameterInches * 3.1415);
        }

        public EncoderParameters(double countsPerInch) {
            COUNTS_PER_INCH = countsPerInch;
        }
    }

}