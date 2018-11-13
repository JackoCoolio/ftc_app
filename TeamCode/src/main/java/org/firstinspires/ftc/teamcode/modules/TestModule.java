package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Used for testing the Modular OpMode system.
 */
public class TestModule extends Module {

    int count;

    public TestModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
        count = 0;
    }

    @Override
    public void init() {
        telemetry.addData("TESTMODULE","TestModule.init()");
    }

    @Override
    public void loop() {
        telemetry.addData("COUNT", count);
    }

    @Override
    public void telemetry() {

    }

}
