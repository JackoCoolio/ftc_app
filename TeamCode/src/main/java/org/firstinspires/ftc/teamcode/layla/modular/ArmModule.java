package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;
import org.firstinspires.ftc.teamcode.utility.Utility;

public class ArmModule extends Module {

    private enum GrabberStatus {
        Grabbing, Releasing, Idle
    }
    GrabberStatus status = GrabberStatus.Idle;

    DcMotor arm;
    Servo grabber1, grabber2;

    /*
    Config
     */
    private final double servoSpeed = .01d;
    private final double openPosition = 0d;
    private final double closedPosition = 1d;
    /*
    Config
     */

    public ArmModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        arm = hardwareMap.dcMotor.get("arm");
        grabber1 = hardwareMap.servo.get("grabber1");
        grabber2 = hardwareMap.servo.get("grabber2");
    }

    @Override
    public void loop() {
        arm.setPower(-gamepad2.right_stick_y);

        if (gamepad2.right_trigger >= .1) {
            grab();
            status = GrabberStatus.Grabbing;
        } else {
            release();
            status = GrabberStatus.Releasing;
        }
    }

    public void grab() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), closedPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
    }

    public void release() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), openPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
    }

    @Override
    public void telemetry() {
        telemetry.addData("Grabber", status.toString());
    }
}
