package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "Blue: Autonomous D", group = "Blue")
public class Autonomous_D extends IMUAutonomous {

    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);

        return new Stage[] {
                JewelHitter.getStage(robot, JewelHitter.Color.Red, telemetry),
                new Stage() { // Drive off.
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DRIVE_OFF_TIME_BD) {
                            robot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() { // Turn towards slot.

                    double startHeading;
                    double target;

                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                        target = vuMarkAngles.get("D").get(vuMark);
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.addData("Heading",heading);
                        telemetry.addData("Start Heading", startHeading);
                        telemetry.addData("Target",target + startHeading);
                        telemetry.addData("Target offset",target);
                        telemetry.addData("To go...",target + startHeading - heading);
                        if (heading < startHeading + target) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(AutonomousConstants.TURN_SPEED);
                            robot.rightMotors.setPower(-AutonomousConstants.TURN_SPEED);
                            return false;
                        }
                    }
                },
                new Stage() { // Drive towards slots.

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.TOWARDS_SLOT_TIME_D) {
                            robot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() { // Run lifts.

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.LIFT_RUNTIME) {
                            robot.liftMotors.setPower(AutonomousConstants.LIFT_SPEED);
                            return false;
                        } else {
                            robot.liftMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() { // Drive back from slot.

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DRIVE_BACK_TIME) {
                            robot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        }
                    }
                }
        };
    }
}
