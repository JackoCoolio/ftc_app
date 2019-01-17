package org.firstinspires.ftc.teamcode.layla.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layla.modular.DriveModule;
import org.firstinspires.ftc.teamcode.modules.ModularOpMode;

@TeleOp(name = "Drive Only Layla")
public class DriveOnlyLayla extends ModularOpMode {

    @Override
    protected void initModules() {
        registerModules(DriveModule.class);
    }
}
