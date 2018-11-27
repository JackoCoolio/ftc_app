package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.layla.modular.ArmModule;
import org.firstinspires.ftc.teamcode.layla.modular.DriveModule;
import org.firstinspires.ftc.teamcode.layla.modular.TMModule;
import org.firstinspires.ftc.teamcode.modules.ModuleBasedOpMode;

@TeleOp(name = "LaylaModular")
public class LaylaModular extends ModuleBasedOpMode {

    @Override
    public void initModules() {
        registerModules(DriveModule.class, TMModule.class, ArmModule.class);
    }

}
