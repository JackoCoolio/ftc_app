package org.firstinspires.ftc.teamcode.autonomous.main;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 2/5/2018.
 */

public class StagePresets {

    public static MotorGroup[] driveMotorGroups;

    public enum Direction {Left, Right}

    public static IMUAutonomous.Stage drive(final double speed, final double inches, final double timeout, final Telemetry telemetry, MotorGroup... _groups) {
        final MotorGroup[] groups;
        if (_groups.length == 0) {
            groups = driveMotorGroups;
        } else {
            groups = _groups;
        }
        return new DriveStage(speed, inches, timeout, telemetry, groups);
    }

//    public static IMUAutonomous.Stage drive(final double speed, final DelayedValue<Double> inchGetter, final double timeout, final Telemetry telemetry, final MotorGroup... _groups) {
//        final MotorGroup[] groups;
//        if (_groups.length == 0) {
//            groups = driveMotorGroups;
//        } else {
//            groups = _groups;
//        }
//        return new DriveStage(speed, 0, timeout, telemetry, groups) {
//            @Override
//            public void setup(double heading, ElapsedTime runtime) {
//                inches = inchGetter.getValue();
//                super.setup(heading, runtime);
//            }
//        };
//    }

    public static IMUAutonomous.Stage turn(final Direction dir, final double target, final double speed, final MotorGroup left, final MotorGroup right) {
        return new IMUAutonomous.Stage() {
            double startHeading;
            @Override
            public void setup(double heading, ElapsedTime runtime) {
                startHeading = heading;
            }

            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                if (dir.equals(Direction.Left)) {
                    if (heading < startHeading + target) {
                        left.setPower(-speed);
                        right.setPower(speed);
                        return false;
                    } else {
                        left.zero();
                        right.zero();
                        return true;
                    }
                } else if (dir.equals(Direction.Right)) {
                    if (heading > startHeading + target) {
                        left.setPower(speed);
                        right.setPower(-speed);
                        return false;
                    } else {
                        left.zero();
                        right.zero();
                        return true;
                    }
                } else return true;
            }
        };
    }

    public static IMUAutonomous.Stage runByTime(final double time, final double power, final MotorGroup... groups) {
        return new IMUAutonomous.Stage() {
            @Override
            public void setup(double heading, ElapsedTime runtime) {
                runtime.reset();
                for (MotorGroup group : groups) group.setPower(power);
            }

            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                if (runtime.seconds() > time) {
                    for (MotorGroup group : groups) group.zero();
                    return true;
                } else return false;
            }
        };
    }

    public static class DriveStage extends IMUAutonomous.Stage {

        protected double speed, inches, timeout;
        protected Telemetry telemetry;
        protected MotorGroup[] groups;

        protected boolean[] calibrated;
        protected boolean allCalibrated = false;

        public DriveStage(double speed, double inches, double timeout, Telemetry telemetry, MotorGroup... groups) {
            this.speed = speed;
            this.inches = inches;
            this.timeout = timeout;
            this.telemetry = telemetry;
            this.groups = groups;
        }

        @Override
        public void setup(double heading, ElapsedTime runtime) {
            runtime.reset();
            calibrated = new boolean[groups.length];
            for (boolean b : calibrated) b = false;
            for (MotorGroup group : groups) group.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        @Override
        public boolean run(double heading, ElapsedTime runtime) {
            if (!allCalibrated) {
                for (int i = 0; i < groups.length; i++) {
                    if (!calibrated[i]) {
                        telemetry.addData("Calibrating encoders","MotorGroup " + i + " of " + groups.length);
                        calibrated[i] = groups[i].encodersCalibrated(telemetry, runtime);
                        break;
                    }
                }

                allCalibrated = allTrue(calibrated);
                return false;
            } else {
                return MotorGroup.runAndStopIfFinished(speed, inches, timeout, telemetry, groups);
            }
        }

        protected final boolean allTrue(boolean[] b) {
            boolean all = true;
            for (boolean bool : b) {
                if (!bool) all = false;
            }
            return all;
        }
    }

}
