package org.firstinspires.ftc.teamcode.leelo.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;

public class ArmModule extends Module {

    DcMotor arm;

    public ArmModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        arm = hardwareMap.dcMotor.get("arm");
    }

    @Override
    public void loop() {
        if (gamepad1.right_trigger == 1) {
            arm.setPower(1);
        } else if (gamepad1.left_trigger == 1) {
            arm.setPower(-1);
        } else {
            arm.setPower(0);
        }
    }

    @Override
    public void telemetry() {
        telemetry.addData("Arm", arm.getPower());
    }
}
