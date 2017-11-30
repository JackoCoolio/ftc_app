package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.ChassisHardware;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class Chassis extends Module {

    private ChassisHardware hardware;

    private double leftPower, rightPower;

    public Chassis(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void init() {
        hardware = new ChassisHardware(hardwareMap);
    }

    public void loop() {
        leftPower = 0;
        rightPower = 0;

        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        if (gamepad1.left_trigger > 0.25 || gamepad1.right_trigger > 0.25) { //if either trigger is pressed, power is divided by 2 for precision turning
            leftPower /= 2;
            rightPower /= 2;
        }

        hardware.leftMotors.setPower(leftPower);
        hardware.rightMotors.setPower(rightPower);
    }

    public void telemetry() {
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Gamepad 1"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
    }

}
