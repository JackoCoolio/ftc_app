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
        return new IMUAutonomous.Stage() {

            boolean[] calibrated;
            boolean allCalibrated = false;

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

            private boolean allTrue(boolean[] b) {
                boolean all = true;
                for (boolean bool : b) {
                    if (!bool) all = false;
                }
                return all;
            }
        };
    }

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

}
