package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Module {

    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;
    protected Gamepad gamepad1, gamepad2;

    public Module(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        this.telemetry = telemetry;

        init();
    }

//    protected Module(ModuleParameters parameters) {
//        this.hardwareMap = parameters.hardwareMap;
//        this.gamepad1 = parameters.gamepad1;
//        this.gamepad2 = parameters.gamepad2;
//        this.telemetry = parameters.telemetry;
//        init();
//    }

    public abstract void init();
    public abstract void loop();
    public abstract void telemetry();
}