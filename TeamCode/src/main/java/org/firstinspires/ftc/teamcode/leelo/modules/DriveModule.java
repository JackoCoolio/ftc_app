package org.firstinspires.ftc.teamcode.leelo.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;

public class DriveModule extends Module {

    DcMotor front_left, front_right, rear_left, rear_right;

    enum Movement {

        Forward,
        Backward,
        Left,
        Right,
        CW,
        CCW

    }

    public DriveModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        front_left = hardwareMap.dcMotor.get("front_left");
        front_right = hardwareMap.dcMotor.get("front_right");
        rear_left = hardwareMap.dcMotor.get("rear_left");
        rear_right = hardwareMap.dcMotor.get("rear_right");
    }

    public void move(Movement movement, double speed) {
        switch (movement) {
            case Forward:
                    setMotors(1,1,1,1);
                break;
            case Backward:
                    setMotors(-1, -1, -1, -1);
                break;
            case Left:
                    setMotors(-1, 1, 1, -1);
                break;
            case Right:
                    setMotors(1, -1, -1, 1);
                break;
            case CW:
                    setMotors(1, -1, 1, -1);
                break;
            case CCW:
                    setMotors(-1, 1, -1, 1);
                break;
        }
    }

    public void setMotors(double fl, double fr, double rl, double rr) {
        front_left.setPower(fl);
        front_right.setPower(fr);
        rear_left.setPower(rl);
        rear_right.setPower(rr);
    }

    @Override
    public void loop() {
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        setMotors(v1, v2, v3, v4);
    }

    @Override
    public void telemetry() {

    }
}
