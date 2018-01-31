package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 1/30/2018.
 */
@TeleOp(name = "DriveByDistance")
public class DriveByDistance extends OpMode {

    static final double PI = 3.1415;
    static final double COUNTS_PER_REV = 560;
    static final double DRIVE_GEAR_REDUCTION = 40.0; // ?
    static final double WHEEL_DIAMETER_INCHES = 3.75;

    Robot robot;
    ElapsedTime timer;
    boolean driven = false;

    @Override
    public void init() {
        timer = new ElapsedTime();
        robot = new Robot(hardwareMap);
        MotorGroup.EncoderParameters params = new MotorGroup.EncoderParameters(
                COUNTS_PER_REV,
                DRIVE_GEAR_REDUCTION,
                WHEEL_DIAMETER_INCHES
        );
        robot.leftMotors.setEncoderParameters(params);
        robot.rightMotors.setEncoderParameters(params);
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {
        if (!driven) {
            robot.leftMotors.encoderDrive(0.5, 12, 10, "front_left", telemetry);
            driven = robot.rightMotors.encoderDrive(0.5, 12, 10, "front_right", telemetry);
        } else {
            driven = false;
        }
    }
}
