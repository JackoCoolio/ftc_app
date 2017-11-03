package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by jacktwamb52 on 10/18/2017
 */

@TeleOp(name="Controller Test", group="Test")
public class ControllerTest extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        telemetry.addData("Controls"," Y: " + String.valueOf(gamepad1.y) + " X: " + String.valueOf(gamepad1.a));
    }

    @Override
    public void stop() {

    }

}
