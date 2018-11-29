package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;

public class TMModule extends Module {

    DcMotor tapeMeasure;

    public TMModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        tapeMeasure = hardwareMap.dcMotor.get("tape_measure");
    }

    @Override
    public void loop() {
        if (-gamepad2.left_stick_y > .1) {
            tapeMeasure.setPower(-gamepad2.left_stick_y);
        } else if (-gamepad2.left_stick_y < .1) {
            tapeMeasure.setPower(-gamepad2.left_stick_y);
        } else {
            tapeMeasure.setPower(0d);
        }
    }

    @Override
    public void telemetry() {
        telemetry.addData("Tape Measure Power", -gamepad2.left_stick_y);
    }
}
