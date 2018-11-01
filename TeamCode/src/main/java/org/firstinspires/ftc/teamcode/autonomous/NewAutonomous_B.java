package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.main.StagePresets;
import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 2/7/2018.
 */
@Autonomous(name = "New Autonomous B")
public class NewAutonomous_B  extends IMUAutonomous {
    GlyphRobot glyphRobot;

    @Override
    public Stage[] setStages() {
        glyphRobot = new GlyphRobot(hardwareMap);
        StagePresets.driveMotorGroups = new MotorGroup[]{glyphRobot.leftMotors, glyphRobot.rightMotors};

        return new Stage[]{
                JewelHitter.getStage(glyphRobot, JewelHitter.Color.Blue, telemetry),
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 2) {
                            glyphRobot.lift.setPower(0.95);
                            return false;
                        } else {
                            glyphRobot.lift.zero();
                            return true;

                        }

                    }
                },
                StagePresets.drive(
                        AutonomousConstants.DRIVE_SPEED,
                        24,
                        4,
                        telemetry
                ),

                new Stage() {
                    @Override
                    public String getName(int index) {
                        return "run lifts 2s";
                    }

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 2) {
                            glyphRobot.lift.setPower(0.95);
                            return false;
                        } else {
                            glyphRobot.lift.zero();
                            return true;

                        }

                    }
                },

                new Stage() { //Turns towards the correct slot.
                    double startHeading;
                    double target;

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        glyphRobot.lift.setPower(0.95);

                        target = vuMarkAngles.get("B").get(vuMark);

                        startHeading = heading;
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.addData("Start heading", startHeading);
                        telemetry.addData("Heading", heading);
                        telemetry.addData("Target angle", startHeading + target);
                        telemetry.addData("Target offset", target);
                        if (heading < startHeading + target) {
                            glyphRobot.leftMotors.zero();
                            glyphRobot.rightMotors.zero();
                            return true;
                        } else {
                            glyphRobot.leftMotors.setPower(AutonomousConstants.TURN_SPEED);
                            glyphRobot.rightMotors.setPower(-AutonomousConstants.TURN_SPEED);
                            return false;
                        }
                    }
                },
                MotorGroup.calibrateStage(
                        telemetry,
                        glyphRobot.leftMotors,
                        glyphRobot.rightMotors

                ),

                new Stage() {
                    double dist;

                    @Override
                    public String getName(int index) {
                        return "drive to slot";
                    }

                    @Override public void setup(double heading, ElapsedTime runtime) {dist = vuMarkDistances.get("B").get(vuMark);}

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(
                                AutonomousConstants.DRIVE_SPEED,
                                dist,
                                3,
                                telemetry,
                                glyphRobot.leftMotors,
                                glyphRobot.rightMotors
                        );
                    }
                },
                new Stage() {

                    @Override public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                        glyphRobot.liftMotors.setPower(-0.95);
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() > 3) {
                            glyphRobot.liftMotors.zero();
                            return true;
                        }
                        return false;
                    }
                },
                StagePresets.drive(
                        AutonomousConstants.DRIVE_SPEED,
                        12,
                        3,
                        telemetry
                ),
                StagePresets.drive(
                        AutonomousConstants.DRIVE_SPEED,
                        -6,
                        2,
                        telemetry
                )
        };
    }
}
