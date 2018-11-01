package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by Noble on 1/19/2018.
 */

@Disabled
@TeleOp(name = "Angle Finder")
public class AngleFinderAutonomous extends IMUAutonomous {

    private GlyphRobot glyphRobot;

    private enum Direc {Forward, Backward}
    private Direc dir = null;

    @Override
    public Stage[] setStages() {

        glyphRobot =  new GlyphRobot(hardwareMap);

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
                        return MotorGroup.runAndStopIfFinished(AutonomousConstants.DRIVE_SPEED, dir.equals(Direc.Forward)? 24 : -24, 5, telemetry, glyphRobot.leftMotors, glyphRobot.rightMotors);
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
                            glyphRobot.leftMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                            glyphRobot.rightMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                        }  else if (gamepad1.dpad_right) {
                            glyphRobot.leftMotors.setPower(AutonomousConstants.DRIVE_SPEED);
                            glyphRobot.rightMotors.setPower(-AutonomousConstants.DRIVE_SPEED);
                        } else {
                            glyphRobot.leftMotors.zero();
                            glyphRobot.rightMotors.zero();
                        }

                        double result = heading - startHeading;
                        telemetry.addData("Target Angle",result);

                        return false;
                    }
                }
        };
    }
}
