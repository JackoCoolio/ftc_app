package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public abstract class Module {

    HardwareMap hardwareMap;

    Gamepad gamepad1, gamepad2;

    public Module(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        this.hardwareMap = hardwareMap;

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        init();
    }

    public abstract void init();
    public abstract void loop();
    public abstract void telemetry(Telemetry telemetry);
}
