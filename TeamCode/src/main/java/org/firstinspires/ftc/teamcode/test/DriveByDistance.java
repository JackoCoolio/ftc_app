package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
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
                MotorGroup.calibrateStage(telemetry, robot.leftMotors, robot.rightMotors),
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(0.4, 36, 15, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                },
                MotorGroup.calibrateStage(telemetry, robot.leftMotors, robot.rightMotors),
                new Stage() {
                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return MotorGroup.runAndStopIfFinished(0.25, -24, 15, telemetry, robot.leftMotors, robot.rightMotors);
                    }
                }
        };

    }
}
