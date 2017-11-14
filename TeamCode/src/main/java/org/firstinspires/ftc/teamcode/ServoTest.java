package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jacktwamb52 on 11/7/2017.
 */

@TeleOp(name = "ServoTest", group = "Test")
public class ServoTest extends OpMode {

    Servo servo;

    @Override
    public void init() {
        telemetry.addData("Status","Initialized");

        servo = hardwareMap.get(Servo.class, "servo");

        servo.setPosition(0);
    }

    @Override
    public void loop() {
        telemetry.addData("Status","Running...");

        servo.setPosition(gamepad1.right_trigger);

        telemetry.addData("Servo Position",servo.getPosition());
    }

}
