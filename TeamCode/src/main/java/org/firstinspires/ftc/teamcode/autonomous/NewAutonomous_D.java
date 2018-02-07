package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.main.StagePresets;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 2/7/2018.
 */
@Autonomous(name = "New Autonomous D")
public class NewAutonomous_D extends IMUAutonomous {

    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);
        StagePresets.driveMotorGroups = new MotorGroup[] {robot.leftMotors, robot.rightMotors};

        return new Stage[] {
                JewelHitter.getStage(robot, JewelHitter.Color.Red, telemetry),

                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 6) {
                            robot.lift.setPower(0.95);
                            return false;
                        } else {
                            robot.lift.zero();
                            return true;
                        }
                    }
                },
                StagePresets.drive(
                        AutonomousConstants.DRIVE_SPEED,
                        -24,
                        4,
                        telemetry
                ),
                new Stage() { // Turn towards correct slot.

                    double startHeading;
                    double target;

                    public void setup(double heading, ElapsedTime runtime) {
                        robot.lift.setPower(0.95);

                        target = vuMarkAngles.get("D").get(vuMark);

                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.addData("Start heading",startHeading);
                        telemetry.addData("Heading",heading);
                        telemetry.addData("Target angle",startHeading + target);
                        telemetry.addData("Target offset",target);
                        if (heading < startHeading + target) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(-AutonomousConstants.TURN_SPEED);
                            robot.rightMotors.setPower(AutonomousConstants.TURN_SPEED);
                            return false;
                        }
                    }
                },
                MotorGroup.calibrateStage(
                        telemetry,
                        robot.leftMotors,
                        robot.rightMotors
                ),
                new Stage() {
                    double dist;
                    @Override public void setup(double heading, ElapsedTime runtime) {dist = vuMarkDistances.get("A").get(vuMark);}

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(
                                AutonomousConstants.DRIVE_SPEED,
                                dist,
                                3,
                                telemetry,
                                robot.leftMotors,
                                robot.rightMotors
                        );
                    }
                },
                new Stage() {

                    @Override public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                        robot.liftMotors.setPower(-0.95);
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() > 3) {
                            robot.liftMotors.zero();
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