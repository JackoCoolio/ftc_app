package org.firstinspires.ftc.teamcode.layla;

import org.firstinspires.ftc.teamcode.modules.ModuleBasedOpMode;
import org.firstinspires.ftc.teamcode.modules.TestModule;

public class LaylaModular extends ModuleBasedOpMode {

    @Override
    public void initModules() {
        registerModules(TestModule.class);
    }

}
