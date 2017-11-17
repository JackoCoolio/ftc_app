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

    public Chassis(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        super(hardwareMap, gamepad1, gamepad2);
    }

    public void init() {
        hardware = new ChassisHardware(hardwareMap);
    }

    public void loop() {
        leftPower = 0;
        rightPower = 0;

        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        hardware.leftMotors.setPower(leftPower);
        hardware.rightMotors.setPower(rightPower);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Gamepad 1"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
    }

}
