package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.LiftHardware;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class Lift extends Module {

    private LiftHardware hardware;

    public Lift(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void init() {
        hardware = new LiftHardware(hardwareMap);
    }

    public void loop() {

//        if (gamepad1.dpad_up) {
//            hardware.mainLift.setPower(0.25);
//        } else if (gamepad1.dpad_down) {
//            hardware.mainLift.setPower(-0.25);
//        }

        hardware.leftMotor.setPower(gamepad1.left_stick_y);
        hardware.rightMotor.setPower(gamepad1.right_stick_y);

        hardware.servo.setPosition(gamepad1.right_trigger);
    }

    public void telemetry() {
        telemetry.addData("Gamepad 2:","Left: " + String.valueOf(gamepad1.left_stick_y) + " Right: " + String.valueOf(gamepad1.right_stick_y));
    }

}
