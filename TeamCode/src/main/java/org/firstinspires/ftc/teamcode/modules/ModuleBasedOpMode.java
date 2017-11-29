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

    //For easier readability
    enum Mode {
        Type,
        Instance
    }

    private Mode mode;

    private Class<? extends Module>[] types;
    private Module[] modules;

    /**
     * Ideally, this is the only code that has to be written in the 'main' class.
     *
     */
    protected void initModules() {
        telemetry.addData("MODULE INITIALIZATION ERROR","No modules initialized!");
    }

    protected final void registerModules(Class<? extends Module>... types) {
        telemetry.addData("INITIALIZING MODULES","Setting module initialization mode to Type");
        this.types = types;
        mode = Mode.Type;
    }
    protected final void registerModules(Module... modules) {
        telemetry.addData("INITIALIZING MODULES","Setting module initialization mode to Instance");
        this.modules = modules;
        mode = Mode.Instance;
    }

    @Override
    public final void init() {

        initModules();

        telemetry.addData("INITIALIZING MODULES","Initializing OpModes in mode - " + mode.toString());
        if (mode == Mode.Type) {
            this.modules = new Module[types.length];

            for (int i = 0; i < this.modules.length; i++) {

                Class<? extends Module> clazz = types[i];
                Module module = null;

                try {
                    Constructor<?> con = clazz.getConstructor(HardwareMap.class, Gamepad.class, Gamepad.class);
                    telemetry.addData("FOUND CONSTRUCTOR", clazz.getSimpleName());

                    module = (Module) con.newInstance(hardwareMap, gamepad1, gamepad2);
                } catch (NoSuchMethodException e) {
                    telemetry.addData("NOSUCHMETHOD ERROR", "No constructor for " + clazz.getSimpleName() + "!");

                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    telemetry.addData("ILLEGALACCESS ERROR", "Constructor for " + clazz.getSimpleName() + " is private!");
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    telemetry.addData("INVOCATION ERROR", "Something went wrong!");
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    telemetry.addData("INSTANTIATION ERROR", "Could not instantiate a " + clazz.getSimpleName() + " module!");
                    e.printStackTrace();
                }

                modules[i] = module;

            }
        }

        for (int i = 0; i < this.modules.length; i++) {
            Module module = this.modules[i];

            try {
                module.init();
                telemetry.addData("FOUND MODULE", module.getClass().getSimpleName());
            } catch (NullPointerException e) {
                telemetry.addData("MODULE INITIALIZATION ERROR", "A module is null!");
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
