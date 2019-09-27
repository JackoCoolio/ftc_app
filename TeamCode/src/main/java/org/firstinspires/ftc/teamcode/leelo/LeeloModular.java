package org.firstinspires.ftc.teamcode.leelo;

import org.firstinspires.ftc.teamcode.leelo.modules.ArmModule;
import org.firstinspires.ftc.teamcode.leelo.modules.DriveModule;
import org.firstinspires.ftc.teamcode.leelo.modules.HandModule;
import org.firstinspires.ftc.teamcode.modules.ModularOpMode;

public class LeeloModular extends ModularOpMode {

    @Override
    protected void initModules() {
        registerModules(HandModule.class, ArmModule.class, DriveModule.class);
    }
}
