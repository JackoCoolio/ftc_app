package org.firstinspires.ftc.teamcode.layla;

import org.firstinspires.ftc.teamcode.layla.modular.DriveModule;
import org.firstinspires.ftc.teamcode.layla.modular.TMModule;
import org.firstinspires.ftc.teamcode.modules.ModuleBasedOpMode;

public class LaylaModular extends ModuleBasedOpMode {

    @Override
    public void initModules() {
        registerModules(DriveModule.class, TMModule.class);
    }

}
