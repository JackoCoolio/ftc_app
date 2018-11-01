package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.main.StagePresets;
import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 2/5/2018.
 */

@Disabled
@Autonomous(name = "New Autonomous A")
public class NewAutonomous_A extends IMUAutonomous {

    GlyphRobot glyphRobot;

    @Override
    public Stage[] setStages() {

        glyphRobot = new GlyphRobot(hardwareMap);
        StagePresets.driveMotorGroups = new MotorGroup[] {glyphRobot.leftMotors, glyphRobot.rightMotors};

        return new Stage[] {
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
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 4) {
                            glyphRobot.lift.setPower(0.95);
                            return false;
                        } else {
                            glyphRobot.lift.zero();
                            return true;
                        }
                    }
                },

                new Stage() { // Turn towards correct slot.

                    double startHeading;
                    double target;

                    public void setup(double heading, ElapsedTime runtime) {
                        target = vuMarkAngles.get("A").get(vuMark);
                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.addData("Start heading",startHeading);
                        telemetry.addData("Heading",heading);
                        telemetry.addData("Target angle",startHeading + target);
                        telemetry.addData("Target offset",target);
                        if (heading > startHeading + target) {
                            glyphRobot.leftMotors.zero();
                            glyphRobot.rightMotors.zero();
                            return true;
                        } else {
                            glyphRobot.leftMotors.setPower(-AutonomousConstants.TURN_SPEED);
                            glyphRobot.rightMotors.setPower(AutonomousConstants.TURN_SPEED);
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
                    @Override public void setup(double heading, ElapsedTime runtime) {dist = vuMarkDistances.get("A").get(vuMark);}
                    @Override public boolean run(double heading, ElapsedTime runtime) {
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
                StagePresets.runByTime(3, -.95, glyphRobot.liftMotors),
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
