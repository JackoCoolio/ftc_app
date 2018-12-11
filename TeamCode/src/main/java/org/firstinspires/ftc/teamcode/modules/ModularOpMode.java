package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ModularOpMode extends OpMode {

    private Class<? extends Module>[] types;
    private Module[] modules;

//    public static Gamepad gamepadA;
//    public static Gamepad gamepadB;

    /**
     * Ideally, this is the only code that has to be written in the 'main' class.
     */
    protected void initModules() {
        telemetry.addData("MODULE INITIALIZATION ERROR","No modules initialized!");
    }

    @SafeVarargs
    protected final void registerModules(Class<? extends Module>... types) {
        telemetry.addData("INITIALIZING MODULES","Modules set.");
        this.types = types;
    }

    @Override
    public final void init() {

//        ModularOpMode.gamepadA = gamepad1;
//        ModularOpMode.gamepadB = gamepad2;

        // Runs initModules() in the OpMode.
        initModules();

        telemetry.addData("INITIALIZING MODULES","Initializing OpModes");

        this.modules = new Module[types.length];

        for (int i = 0; i < this.modules.length; i++) {

            Class<? extends Module> clazz = types[i];
            Module module = null;

            try {

                // Gets the constructor from the Module class.
                Constructor<?> con = clazz.getConstructor(HardwareMap.class, Gamepad.class, Gamepad.class, Telemetry.class);

                telemetry.addData("FOUND CONSTRUCTOR", clazz.getSimpleName());

                // Initializes each module.
                module = (Module) con.newInstance(hardwareMap, gamepad1, gamepad2, telemetry);

            } catch (NoSuchMethodException e) {
                telemetry.addData("NOSUCHMETHOD EXCEPTION", "No constructor for " + clazz.getSimpleName() + "!");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                telemetry.addData("ILLEGALACCESS EXCEPTION", "Constructor for " + clazz.getSimpleName() + " is private!");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                telemetry.addData("INVOCATION EXCEPTION", "Something went wrong!");
                e.printStackTrace();
            } catch (InstantiationException e) {
                telemetry.addData("INSTANTIATION EXCEPTION", "Could not instantiate a " + clazz.getSimpleName() + " module!");
                e.printStackTrace();
            }

            // Adds each module to the array that is iterated through.
            modules[i] = module;

        }

        for (int i = 0; i < this.modules.length; i++) {
            Module module = this.modules[i];

            try {
                // Initializes each module.
                module.init();
                telemetry.addData("INITIALIZED MODULE", module.getClass().getSimpleName());
            } catch (NullPointerException e) {
                telemetry.addData("MODULE INITIALIZATION ERROR",  module.getClass().getSimpleName() + " failed to init!");
            }
        }

        telemetry.addData("Status","Initialized");

    }

    @Override
    public final void loop() {

        for (int i = 0; i < modules.length; i++) {

            try {
                // Updates gamepads.
                modules[i].gamepad1.copy(gamepad1);
                modules[i].gamepad2.copy(gamepad2);
            } catch (RobotCoreException e) {
                telemetry.addData("ROBOTCORE EXCEPTION ERROR","Something went wrong with the gamepads!");
            }

            // Runs each module's loop() and telemetry()
            modules[i].loop();
            modules[i].telemetry();
        }

        // Non-module specific telemetry.
        telemetry.addData("Status","Running");
        telemetry.addData("Runtime", getRuntime());

    }

}
