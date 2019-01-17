package org.firstinspires.ftc.teamcode.layla.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layla.modular.LiftModule;
import org.firstinspires.ftc.teamcode.modules.ModularOpMode;

@TeleOp(name = "Lift Only Layla")
public class LiftOnlyLayla extends ModularOpMode {

    @Override
    protected void initModules() {
        registerModules(LiftModule.class);
    }
}
