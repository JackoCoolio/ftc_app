package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Chassis;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.ModuleBasedOpMode;

@TeleOp(name="Drive", group="Drive Test")
public class LooseScrewsOpMode1 extends ModuleBasedOpMode {

    @Override
    protected void initModules() {
        if (Chassis.class != null && Lift.class != null) {
            registerModules(Chassis.class, Lift.class);
        } else {
            telemetry.addData("SOMETHING IS NULL FIX IT PLEASE","THANK YOU");
        }
//        registerModules(new Chassis(hardwareMap, gamepad1, gamepad2), new Lift(hardwareMap, gamepad1, gamepad2));
    }
}

