package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;

/**
 * Created by jdwam on 12/2/2018.
 */

public class HookModule extends Module {

    private Servo hook;

    private boolean engaged = false;
    private double engaged_pos = 0d;
    private double disengaged_pos = 1d;

    private boolean pressable = true;

    public HookModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        hook = hardwareMap.servo.get("hook");
    }

    @Override
    public void loop() {
//        hook.setPosition(-gamepad1.right_stick_y);

        if (gamepad2.a && pressable) {
            pressable = false;
            engaged = !engaged;
        }
        if (!gamepad2.a) {
            pressable = true;
        }

        if (engaged) {
            hook.setPosition(engaged_pos);
        } else {
            hook.setPosition(disengaged_pos);
        }
    }

    @Override
    public void telemetry() {
        telemetry.addData("Position",-gamepad1.right_stick_y);
    }
}
