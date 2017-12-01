package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Chassis;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.ModuleBasedOpMode;

@TeleOp(name="Drive", group="Drive Test")
public class MainOpMode extends ModuleBasedOpMode {

    @Override
    protected void initModules() {
        registerModules(Chassis.class, Lift.class);
    }
}

