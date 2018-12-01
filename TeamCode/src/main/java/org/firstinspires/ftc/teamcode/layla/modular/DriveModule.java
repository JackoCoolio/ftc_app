package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

public class DriveModule extends Module {

    public MotorGroup leftDrive, rightDrive;

    private static final double main_influence = .75;
    private static final double alt_influence = .25;

    private double leftPower, rightPower;
    private double leftPower_alt, rightPower_alt;
    String turning = "Idle";

    public DriveModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        leftDrive = new MotorGroup(hardwareMap, "left_rear", "left_front");
        rightDrive = new MotorGroup(hardwareMap, "right_rear", "right_front");
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        // Sets the power that the first gamepad has influence over.
        leftPower = -gamepad1.left_stick_y * main_influence;
        rightPower = -gamepad1.right_stick_y * main_influence;

        // Gives the second gamepad a bit of control over turning the robot for better adjustments.
        if (gamepad2.left_bumper) {
            leftPower_alt = -alt_influence;
            rightPower_alt = alt_influence;
            turning = "Turning Left";
        } else if (gamepad2.right_bumper) {
            leftPower_alt = alt_influence;
            rightPower_alt = -alt_influence;
            turning ="Turning Right";
        } else {
            leftPower_alt = 0;
            rightPower_alt = 0;
            turning = "Idle";
        }

        // Sets the power of each side of the motor.
        leftDrive.setPower(leftPower + leftPower_alt);
        rightDrive.setPower(rightPower + rightPower_alt);

    }

    @Override
    public void telemetry() {
        telemetry.addData("Gamepad 1", "Left Drive: (%.2f), Right Drive: (%.2f)", leftPower, rightPower);
        telemetry.addData("Gamepad 2", turning);
    }

}
