package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract class Module {

    final HardwareMap hardwareMap;
    final Telemetry telemetry;
    final Gamepad gamepad1, gamepad2;

    protected Module(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        this.telemetry = telemetry;

        init();
    }

    public abstract void init();
    public abstract void loop();
    public abstract void telemetry();
}
