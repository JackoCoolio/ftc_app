package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

public class DriveModule extends Module {

    public MotorGroup leftDrive, rightDrive;

    private double leftPower, rightPower;

    public DriveModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        leftDrive = new MotorGroup(hardwareMap, "left_rear", "left_front");
        rightDrive = new MotorGroup(hardwareMap, "right_rear", "right_front");
    }

    @Override
    public void loop() {
        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    @Override
    public void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftPower, rightPower);
    }

}
