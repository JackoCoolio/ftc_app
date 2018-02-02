package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousConstants;
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
        enableIMU(false);
        robot = new Robot(hardwareMap);
        return new Stage[] {
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {

                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(AutonomousConstants.DRIVE_SPEED, 24, 5, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                },
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {

                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(AutonomousConstants.DRIVE_SPEED, -24, 5, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                }
        };

    }
}
