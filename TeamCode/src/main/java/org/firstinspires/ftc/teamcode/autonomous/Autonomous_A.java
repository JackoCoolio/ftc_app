package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous (name = "Blue: Autonomous A", group = "Blue")
public class Autonomous_A extends IMUAutonomous
{
    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);

        return new Stage[]{
                JewelHitter.getStage(robot, JewelHitter.Color.Blue), // So you don't have to copy and paste each time.
                new Stage() {// Drive off platform.

                    @Override public void setup(double heading, ElapsedTime runtime) { runtime.reset(); }

                    @Override public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 4) {
                            robot.leftMotors.setPower(DRIVE_SPEED);
                            robot.rightMotors.setPower(DRIVE_SPEED);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() { // Turn towards correct slot.

                    double startHeading;
                    final double target = getTargetAngle("A");

                    public void setup(double heading, ElapsedTime runtime) { startHeading = heading; }

                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading > startHeading + target) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(-TURN_SPEED);
                            robot.rightMotors.setPower(TURN_SPEED);
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
                        if (runtime.seconds() < 2) {
                            robot.leftMotors.setPower(DRIVE_SPEED);
                            robot.rightMotors.setPower(DRIVE_SPEED);
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
                        if (runtime.seconds() < 4) {
                            robot.liftMotors.setPower(LIFT_SPEED);
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
                        if (runtime.seconds() < 1.5) {
                            robot.leftMotors.setPower(-DRIVE_SPEED);
                            robot.rightMotors.setPower(-DRIVE_SPEED);
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
