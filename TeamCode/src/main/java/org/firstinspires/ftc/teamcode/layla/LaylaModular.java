package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layla.modular.ArmModule;
import org.firstinspires.ftc.teamcode.layla.modular.DriveModule;
import org.firstinspires.ftc.teamcode.layla.modular.HookModule;
import org.firstinspires.ftc.teamcode.layla.modular.TMModule;
import org.firstinspires.ftc.teamcode.modules.ModularOpMode;

@TeleOp(name = "LaylaModular")
public class LaylaModular extends ModularOpMode {

    @Override
    public void initModules() {

        // Adds modules to the list of modules to be used.
        registerModules(ArmModule.class, DriveModule.class, TMModule.class, HookModule.class);

    }

}
