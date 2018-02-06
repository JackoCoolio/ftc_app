package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "Red: Autonomous C", group = "Red")
public class Autonomous_C extends IMUAutonomous {

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
                        if (runtime.seconds() < AutonomousConstants.DRIVE_OFF_TIME_AC) {
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

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        robot.lift.setPower(0.95);
                        startHeading = heading;
                        target = vuMarkAngles.get("C").get(vuMark);
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading < startHeading + target) {
                            robot.leftMotors.setPower(-AutonomousConstants.TURN_SPEED);
                            robot.rightMotors.setPower(AutonomousConstants.TURN_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
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
                        if (runtime.seconds() < AutonomousConstants.TOWARDS_SLOT_TIME_C) {
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
                        robot.lift.zero();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.LIFT_RUNTIME) {
                            robot.liftMotors.setPower(-AutonomousConstants.LIFT_SPEED);
                            return false;
                        } else {
                            robot.liftMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.PUSH_TIME) {
                            robot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            robot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
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
                            robot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
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
