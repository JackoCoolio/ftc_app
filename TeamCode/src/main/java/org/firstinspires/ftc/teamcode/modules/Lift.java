package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.LiftHardware;

public class Lift extends Module {

    private boolean extended = false;
    private LiftHardware liftHardware;
    private double leftLiftPower, rightLiftPower;

    public Lift(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void init() {
        liftHardware = new LiftHardware(hardwareMap);
    }

    public void loop() {

        leftLiftPower = ModuleBasedOpMode.gamepadB.left_stick_y * -.25;
        rightLiftPower = ModuleBasedOpMode.gamepadB.right_stick_y * -.25;
        this.liftHardware.leftMotor.setPower(leftLiftPower);
        this.liftHardware.rightMotor.setPower(rightLiftPower);

        extended = ModuleBasedOpMode.gamepadB.a;
        if (extended) {
            liftHardware.servo.setPosition(1.0);
        } else {
            liftHardware.servo.setPosition(-1.0);
        }

//        double mainLiftPower = gamepad2.right_trigger - gamepad2.left_trigger;
//        hardware.mainLift.setPower(mainLiftPower * 0.25);

    }

    public void telemetry() {
        telemetry.addData("Left Motor",liftHardware.leftMotor.getConnectionInfo());
        telemetry.addData("Servo Extended", String.valueOf(extended));
        telemetry.addData("Gamepad 2:","Left: " + String.valueOf(ModuleBasedOpMode.gamepadB.left_stick_y) + " Right: " + String.valueOf(ModuleBasedOpMode.gamepadB.right_stick_y));
    }

}
