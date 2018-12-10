package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.ModularOpMode;

/**
 * Created by jdwam on 12/2/2018.
 */

@TeleOp(name = "Alternate LaylaModular")
public class LaylaModular_Alternate extends ModularOpMode {

    @Override
    public void initModules() {
        registerModules(HookModule.class);
    }
}
