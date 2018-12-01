package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    private GrabberStatus status = GrabberStatus.Idle;

    private DcMotor arm;
    private Servo grabber1, grabber2;
    private Servo locker;

    /*
    Config
     */
    private final double servoSpeed = .5d;
    private final double openPosition = 0d;
    private final double closedPosition = 1d;

    private final double grabber1_closed = 1d;
    private final double grabber1_open = 0d;

    private final double grabber2_closed = 1d;
    private final double grabber2_open = 0d;

    private final double lock_position = .35d;
    private final double unlock_position = 0d;
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
        locker = hardwareMap.servo.get("lock_servo");

        grabber1.setDirection(Servo.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        // Locker control
        locker.setPosition(mapServoPosition(gamepad2.left_trigger, unlock_position, lock_position));

        // Arm control
        arm.setPower(-gamepad2.right_stick_y);

        // Uses mapServoPosition to set the positions of the servos.
        grabber1.setPosition(mapServoPosition(gamepad2.right_trigger, grabber1_open, grabber1_closed));
        grabber2.setPosition(mapServoPosition(gamepad2.right_trigger, grabber2_open, grabber2_closed));

        // Grabber controls. The grabber is either grabbing or releasing, never in between.
        if (gamepad2.right_trigger >= .1) {
            grab();
            status = GrabberStatus.Grabbing;
        } else {
            release();
            status = GrabberStatus.Releasing;
        }
    }

    // Converts 0-1 to min-max.
    private double mapServoPosition(double value, double min, double max) {
        return value*(max-min) + min;
    }

    // Uses lerp() to move slowly between the current and closed positions of the servo.
    private void grab() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), closedPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), closedPosition, servoSpeed));
    }

    // Uses lerp() to move slowly between the current and open positions of the servo.
    private void release() {
        grabber1.setPosition(Utility.lerp(grabber1.getPosition(), openPosition, servoSpeed));
        grabber2.setPosition(Utility.lerp(grabber2.getPosition(), openPosition, servoSpeed));
    }

    // Displays data about the grabber's status (Grabbing/Releasing) and the locker.
    @Override
    public void telemetry() {
        telemetry.addData("Grabber", status.toString());
        telemetry.addData("Lock Position", Math.round(gamepad2.left_trigger)*100 + "%");
    }
}
