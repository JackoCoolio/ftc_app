package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by Noble on 1/19/2018.
 */

@TeleOp(name = "Angle Finder")
public class AngleFinderAutonomous extends IMUAutonomous {

    private Robot robot;

    private enum Direc {Forward, Backward}
    private Direc dir = null;

    @Override
    public Stage[] setStages() {

        robot =  new Robot(hardwareMap);

        return new Stage[] {
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (gamepad1.y) {
                            dir = Direc.Forward;
                        } else if (gamepad1.a) {
                            dir = Direc.Backward;
                        }
                        return (dir != null);
                    }
                },
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(AutonomousConstants.DRIVE_SPEED, dir.equals(Direc.Forward)? 24 : -24, 5, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                },
                new Stage() {

                    double startHeading;
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (gamepad1.dpad_left) {
                            robot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                        }  else if (gamepad1.dpad_right) {
                            robot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            robot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                        }

                        double result = heading - startHeading;
                        telemetry.addData("Target Angle",result);

                        return false;
                    }
                }
        };
    }
}
