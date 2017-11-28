package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jacktwamb52 on 11/20/2017.
 */

public abstract class ModuleBasedOpMode extends OpMode {

    protected Class<? extends Module>[] types;
    private Module[] modules;

    protected abstract void initModules();

    protected final void registerModules(Class<? extends Module>... types) {
        this.types = types;
    }

    @Override
    public final void init() {

        initModules();

        modules = new Module[types.length];

        for (int i = 0; i < modules.length; i++) {

           Class<? extends Module> clazz = types[i];
           Constructor<?> con = null;
           Module module = null;

            try {
                con = clazz.getConstructor(HardwareMap.class, Gamepad.class, Gamepad.class);
                telemetry.addData("FOUND CONSTRUCTOR FOR", clazz.getSimpleName());

                module = (Module) con.newInstance(hardwareMap, gamepad1, gamepad2);
            } catch (NoSuchMethodException e) {
                telemetry.addData("NOSUCHMETHOD ERROR","No constructor for " + clazz.getSimpleName() + "!");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                telemetry.addData("ILLEGALACCESS ERROR","Constructor for " + clazz.getSimpleName() + " is private!");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                telemetry.addData("INVOCATION ERROR","Something went wrong!");
                e.printStackTrace();
            } catch (InstantiationException e) {
                telemetry.addData("INSTANTIATION ERROR","Could not instantiate a " + clazz.getSimpleName() + " module!");
                e.printStackTrace();
            }

            modules[i] = module;

        }

        for (int i = 0; i < modules.length; i++) {
            Module module = modules[i];

            try {
                module.init();
                telemetry.addData("FOUND MODULE", module.getClass().getSimpleName());
            } catch (NullPointerException e) {
                telemetry.addData("WHAT", "MODULE IS NULL");
            }
        }

        telemetry.addData("Status","Initialized");

    }

    @Override
    public final void loop() {
        for (Module module : modules) {
            module.loop();
            module.telemetry(telemetry);
        }

        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", getRuntime());
    }

}
