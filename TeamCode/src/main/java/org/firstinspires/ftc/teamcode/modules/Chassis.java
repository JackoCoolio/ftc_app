package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.ChassisHardware;

public class Chassis extends Module {

    private ChassisHardware chassisHardware;

    private double leftDrivePower, rightDrivePower;

    public Chassis(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void init() {
        chassisHardware = new ChassisHardware(hardwareMap);
    }

    public void loop() {
        leftDrivePower = 0;
        rightDrivePower = 0;

        leftDrivePower = -ModuleBasedOpMode.gamepadA.left_stick_y;
        rightDrivePower = -ModuleBasedOpMode.gamepadA.right_stick_y;

        if (ModuleBasedOpMode.gamepadA.right_bumper) { //if either trigger is pressed, power is divided by 2 for precision turning
            leftDrivePower /= 2;
            rightDrivePower /= 2;
        }

        chassisHardware.frontLeft.setPower(leftDrivePower);
        chassisHardware.frontRight.setPower(rightDrivePower);
        chassisHardware.rearLeft.setPower(leftDrivePower);
        chassisHardware.rearRight.setPower(rightDrivePower);
    }

    public void telemetry() {
        telemetry.addData("Motor Power", "left (%.2f), right (%.2f)", leftDrivePower, rightDrivePower);
        telemetry.addData("Gamepad 1","Left: " + String.valueOf(ModuleBasedOpMode.gamepadA.left_stick_y) + " Right: " + String.valueOf(ModuleBasedOpMode.gamepadA.right_stick_y));
    }

}
