package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;

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

    int encoderDriveCount = 0;

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

    @Deprecated public void flip() {
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

    public static void zero(MotorGroup... groups) {for (MotorGroup group : groups) group.zero();}

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

    @Deprecated public void setEncoderParameters(EncoderParameters params, String motor) {
        motorParams.put(motor, params);
    }

    @Deprecated public boolean isBusy() {
        int busyCount = 0;
        for (DcMotor motor : motors.values()) {
            if (motor.isBusy()) busyCount++;
        }
        return (busyCount > 0);
    }

    @Deprecated public int getBusyCount() {
        int busyCount = 0;
        for (DcMotor motor : motors.values()) {
            if (motor.isBusy()) busyCount++;
        }
        return busyCount;
    }

    @Deprecated public void busyDebug(Telemetry telemetry) {
        for (String name : motors.keySet()) {
            telemetry.addData(name, (motors.get(name).isBusy())? "Busy" : "Not Busy");
        }
    }

    public void stopRunToPosition() {
        zero();
    }

    public int encoderCount() {
        int count = 0;
        for (DcMotor motor : motors.values())
            count += motor.getCurrentPosition();
        return count;
    }

    public boolean encodersCalibrated(Telemetry telemetry, ElapsedTime timer) {
        int count = 0;
        for (DcMotor motor : motors.values())
            count += motor.getCurrentPosition();
        if (count == 0) {
            telemetry.addData("Encoder Status","Encoders calibrated.");
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            return true;
        } else {
            if (timer.seconds() > 5) {
                for (DcMotor motor : motors.values()) {
                    if (motor.getCurrentPosition() != 0) motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
            }
            telemetry.addData("Encoder Status","Waiting for encoders to reset.");
            return false;
        }
    }

    public static IMUAutonomous.Stage calibrateStage(final Telemetry telemetry, final MotorGroup... groups) {
        return new IMUAutonomous.Stage() {

            @Override
            public String getName(int index) {
                return "Calibrating. Index #" + index + ".";
            }

            boolean[] calibrated;
            @Override
            public void setup(double heading, ElapsedTime runtime) {
                runtime.reset();
                calibrated = new boolean[groups.length];
                for (int i = 0; i < calibrated.length; i++) calibrated[i] = false;

                for (MotorGroup group : groups)
                    for (DcMotor motor : group.motors.values())
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                for (int i = 0; i < groups.length; i++) {
                    if (!calibrated[i]) {
                        telemetry.addData("Calibrating encoders","MotorGroup " + i + " of " + groups.length);
                        calibrated[i] = (groups[i].encoderCount() == 0);
                        break;
                    }
                }
                return allTrue(calibrated);
            }

            private boolean allTrue(boolean[] b) {
                for (boolean bool : b)
                    if (!bool) return false;
                return true;
            }
        };
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
        telemetry.addData("EncoderDrive #" + encoderDriveCount, "Speed: " + speed + ", Inches: " + inches);
        if (motorParams.size() != motors.size()) {
            telemetry.addData("EncoderDrive #" + encoderDriveCount,"ERROR - EncoderParameters not set!");
            return false;
        }
        if (!drivingByEncoder) { // First section run

            telemetry.addData("EncoderDrive #" + encoderDriveCount,"Starting encoderDrive()");
            drivingByEncoder = true;

            targetPositions = new HashMap<>();
            for (String motor : motors.keySet()) {
                telemetry.addData("EncoderDrive #" + encoderDriveCount,"Setting target position for " + motor + " with current position of: " + motors.get(motor).getCurrentPosition());
                targetPositions.put(motors.get(motor), motors.get(motor).getCurrentPosition() + (int)(inches * motorParams.get(motor).COUNTS_PER_INCH));
            }

            telemetry.addData("EncoderDrive #" + encoderDriveCount, "Reset timer!");
            timer.reset();
        }

        if (timer.seconds() > 2) {
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

        if (finishedCount == motors.size() || timer.seconds() > timeoutS) {
            if (timer.seconds() > timeoutS) telemetry.addData("EncoderDrive #" + encoderDriveCount,"Timed out!");
            else telemetry.addData("EncoderDrive #" + encoderDriveCount,"Motors finished driving!");
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            encoderDriveCount++;
            drivingByEncoder = false;
        } else {
            for (String name : motors.keySet()) {
                telemetry.addData(name,motors.get(name).getCurrentPosition());
            }
        }

        for (String motorName : motors.keySet()) { // Telemetry
            DcMotor motor = motors.get(motorName);
            int motorPos = motor.getCurrentPosition();
            int targetPos = targetPositions.get(motor);
            telemetry.addData(("EncoderDrive #" + encoderDriveCount) + ": " + motorName, "(" + motorPos + " / " + targetPos + ") " + (motorPos/targetPos*100) + " percent complete.");
        }

        return !drivingByEncoder;
    }

    public enum TurnDirection {Left,Right};
    public static void turn(double turnSpeed, TurnDirection dir, MotorGroup left, MotorGroup right) {
        if (dir.equals(TurnDirection.Left)) {
            left.setPower(-turnSpeed);
            right.setPower(turnSpeed);
        } else if (dir.equals(TurnDirection.Right)) {
            left.setPower(turnSpeed);
            right.setPower(-turnSpeed);
        }
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
        double COUNTS_PER_REV;
        double DRIVE_GEAR_REDUCTION;
        double WHEEL_DIAMETER_INCHES;

        public EncoderParameters(double countsPerRev, double driveGearReduction, double wheelDiameterInches) {
            COUNTS_PER_REV = countsPerRev;
            DRIVE_GEAR_REDUCTION = driveGearReduction;
            WHEEL_DIAMETER_INCHES = wheelDiameterInches;
            COUNTS_PER_INCH = (countsPerRev * driveGearReduction) / (wheelDiameterInches * 3.1415);
        }

        public EncoderParameters(double countsPerInch) {
            COUNTS_PER_INCH = countsPerInch;
        }
    }

    public static class DistanceDriver {

        EncoderParameters params;
        MotorGroup leftGroup;
        MotorGroup rightGroup;

        DcMotor trackMotorL, trackMotorR;

        ElapsedTime timer;

        double speed, inches, timeout, rampup;

        int newLeftTarget;
        int newRightTarget;

        public DistanceDriver(double speed, double inches, double timeout, double rampup, EncoderParameters params, MotorGroup leftGroup, String trackMotorL, MotorGroup rightGroup, String trackMotorR) {
            if (inches > 0)
                this.speed = Math.abs(speed);
            else
                this.speed = -Math.abs(speed);
            this.inches = inches;
            this.timeout = timeout;
            this.rampup = rampup;
            this.params = params;
            this.timer = new ElapsedTime();
            this.trackMotorL = leftGroup.getMotor(trackMotorL);
            this.trackMotorR = rightGroup.getMotor(trackMotorR);

            init();
        }

        private int getAveragePosition(MotorGroup group) {
            int sum = 0;
            for (DcMotor motor : group.motors.values())
                sum += motor.getCurrentPosition();
            sum /= group.motors.size();
            return sum;
        }

        private void init() {
            timer.reset();

            int leftSum = getAveragePosition(leftGroup);
            int rightSum = getAveragePosition(rightGroup);

            newLeftTarget = leftSum * (int)(inches * params.COUNTS_PER_INCH);
            newRightTarget = rightSum * (int)(inches * params.COUNTS_PER_INCH);


        }

        public boolean drive() {
            if ((timer.seconds() < timeout) && (Math.abs(getAveragePosition(leftGroup)) < newLeftTarget) && (Math.abs(getAveragePosition(rightGroup)) < newRightTarget)) {
                double rem = (getAveragePosition(leftGroup) + getAveragePosition(rightGroup)) / 2;
                double NLSpeed, NRSpeed;
                if (timer.seconds() < rampup) {
                    double ramp = timer.seconds() / rampup;
                    NLSpeed = speed * ramp;
                    NRSpeed = speed * ramp;
                } else if (rem > params.COUNTS_PER_REV * 2) {
                    NLSpeed = speed;
                    NRSpeed = speed;
                } else if (rem > params.COUNTS_PER_REV / 2 && speed*.2 > .1) {
                    NLSpeed = speed * (rem / (params.COUNTS_PER_REV*2));
                    NRSpeed = speed * (rem / (params.COUNTS_PER_REV*2));
                } else {
                    NLSpeed = speed * .2;
                    NRSpeed = speed * .2;
                }

                leftGroup.setPower(NLSpeed);
                rightGroup.setPower(NRSpeed);
            } else {
                leftGroup.zero();
                rightGroup.zero();
            }
            return false;
        }

    }

}