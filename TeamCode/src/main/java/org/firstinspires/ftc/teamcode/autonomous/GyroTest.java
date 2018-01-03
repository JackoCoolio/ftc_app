package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by jacktwamb52 on 1/3/2018.
 */

@Autonomous(name = "GyroTest")
public class GyroTest extends OpMode {

    ModernRoboticsI2cGyro gyro;

    protected enum State {
        Idle,
        Right,
        Left,
        Forward,
        Back,
        hi,
    }

    @Override
    public void init() {
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");

        telemetry.addData("Initializing","Calibrating...");
        gyro.calibrate();
        telemetry.addData("Initializing","Done!");
        telemetry.log().clear();
    }

    @Override
    public void loop() {
        int heading = gyro.getHeading();
        telemetry.addData("Heading", heading);
    }

}
