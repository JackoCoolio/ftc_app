package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Chassis;
import org.firstinspires.ftc.teamcode.modules.Module;

@TeleOp(name="Drive", group="Drive Test")
public class LooseScrewsOpMode1 extends OpMode {

    private Module[] modules = {
            new Chassis(hardwareMap, gamepad1, gamepad2),
            //new Lift(hardwareMap, gamepad1, gamepad2)
    };

    @Override
    public void init() {

        for (Module module : modules) {
            module.init();
        }

        telemetry.addData("Status","Initialized");

    }

    @Override
    public void loop() {

        for (Module module : modules) {
            module.loop();
            module.telemetry(telemetry);
        }

        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", getRuntime());
    }

}

