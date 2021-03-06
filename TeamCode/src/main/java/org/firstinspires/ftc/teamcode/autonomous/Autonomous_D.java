//package org.firstinspires.ftc.teamcode.autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
//import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
//import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;
//
//@Disabled
//@Autonomous(name = "Red: Autonomous D", group = "Red")
//public class Autonomous_D extends IMUAutonomous {
//
//    GlyphRobot glyphRobot;
//
//    @Override
//    public Stage[] setStages() {
//
//        glyphRobot = new GlyphRobot(hardwareMap);
//
//        return new Stage[] {
//                JewelHitter.getStage(glyphRobot, JewelHitter.Color.Red, telemetry),
//                new Stage() { // Drive off.
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        runtime.reset();
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        if (runtime.seconds() < AutonomousConstants.DRIVE_OFF_TIME_BD) {
//                            glyphRobot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
//                            glyphRobot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
//                            return false;
//                        } else {
//                            glyphRobot.leftMotors.zero();
//                            glyphRobot.rightMotors.zero();
//                            return true;
//                        }
//                    }
//                },
//                new Stage() { // Turn towards slot.
//
//                    double startHeading;
//                    double target;
//
//                    public void setup(double heading, ElapsedTime runtime) {
//                        glyphRobot.lift.setPower(0.95);
//                        startHeading = heading;
//                        target = vuMarkAngles.get("D").get(vuMark);
//                    }
//
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        telemetry.addData("Heading",heading);
//                        telemetry.addData("Start Heading", startHeading);
//                        telemetry.addData("Target",target + startHeading);
//                        telemetry.addData("Target offset",target);
//                        telemetry.addData("To go...",target + startHeading - heading);
//                        if (heading < startHeading + target) {
//                            glyphRobot.leftMotors.zero();
//                            glyphRobot.rightMotors.zero();
//                            return true;
//                        } else {
//                            glyphRobot.leftMotors.setPower(AutonomousConstants.TURN_SPEED);
//                            glyphRobot.rightMotors.setPower(-AutonomousConstants.TURN_SPEED);
//                            return false;
//                        }
//                    }
//                },
//                new Stage() { // Drive towards slots.
//
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        runtime.reset();
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        if (runtime.seconds() < AutonomousConstants.TOWARDS_SLOT_TIME_D) {
//                            glyphRobot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
//                            glyphRobot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
//                            return false;
//                        } else {
//                            glyphRobot.leftMotors.zero();
//                            glyphRobot.rightMotors.zero();
//                            return true;
//                        }
//                    }
//                },
//                new Stage() { // Run lifts.
//
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        runtime.reset();
//                        glyphRobot.lift.zero();
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        if (runtime.seconds() < AutonomousConstants.LIFT_RUNTIME) {
//                            glyphRobot.liftMotors.setPower(-AutonomousConstants.LIFT_SPEED);
//                            return false;
//                        } else {
//                            glyphRobot.liftMotors.zero();
//                            return true;
//                        }
//                    }
//                },
//                new Stage() {
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        runtime.reset();
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        if (runtime.seconds() < AutonomousConstants.PUSH_TIME) {
//                            glyphRobot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
//                            glyphRobot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
//                            return false;
//                        } else {
//                            glyphRobot.leftMotors.zero();
//                            glyphRobot.rightMotors.zero();
//                            return true;
//                        }
//                    }
//                },
//                new Stage() { // Drive back from slot.
//
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        runtime.reset();
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        if (runtime.seconds() < AutonomousConstants.DRIVE_BACK_TIME) {
//                            glyphRobot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
//                            glyphRobot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
//                            return false;
//                        } else {
//                            glyphRobot.leftMotors.zero();
//                            glyphRobot.rightMotors.zero();
//                            return true;
//                        }
//                    }
//                }
//        };
//    }
//}
