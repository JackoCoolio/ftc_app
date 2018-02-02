package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 1/30/2018.
 */
@Autonomous(name = "DriveByDistance")
public class DriveByDistance extends IMUAutonomous {

    Robot robot;

    @Override
    public Stage[] setStages() {
        enableVuforia(false);
        enableIMU(true);
        robot = new Robot(hardwareMap);
        return new Stage[] {
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        robot.leftMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        robot.rightMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.clearAll();
                        if (robot.leftMotors.encodersCalibrated(telemetry) && robot.rightMotors.encodersCalibrated(telemetry))
                            return MotorGroup.runAndStopIfFinished(0.4, 36, 15, telemetry, robot.leftMotors, robot.rightMotors);
                        else
                            return false;
                    }
                },
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        telemetry.clearAll();
                        return MotorGroup.runAndStopIfFinished(0.25, -24, 15, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                },
//                new Stage() {
//                    double startHeading;
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        startHeading = heading;
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        imuDebug(telemetry, startHeading, heading, -90);
//                        if (heading < startHeading - 90) {
//                            MotorGroup.zero(robot.leftMotors, robot.rightMotors);
//                            return true;
//                        } else {
//                            MotorGroup.turn(AutonomousConstants.TURN_SPEED, MotorGroup.TurnDirection.Right, robot.leftMotors, robot.rightMotors);
//                            return false;
//                        }
//                    }
//                },
//                new Stage() {
//                    double startHeading;
//                    @Override
//                    public void setup(double heading, ElapsedTime runtime) {
//                        startHeading = heading;
//                    }
//
//                    @Override
//                    public boolean run(double heading, ElapsedTime runtime) {
//                        imuDebug(telemetry, startHeading, heading, +90);
//                        if (heading > startHeading + 90) {
//                            MotorGroup.zero(robot.leftMotors, robot.rightMotors);
//                            return true;
//                        } else {
//                            MotorGroup.turn(AutonomousConstants.TURN_SPEED, MotorGroup.TurnDirection.Left, robot.leftMotors, robot.rightMotors);
//                            return false;
//                        }
//                    }
//                }
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return false;
                    }
                }
        };

    }
}
