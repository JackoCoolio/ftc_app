package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

public class ChassisHardware {

    public final MotorGroup leftMotors;
    public final MotorGroup rightMotors;

    public final DcMotor frontLeft;
    public final DcMotor frontRight;
    public final DcMotor rearLeft;
    public final DcMotor rearRight;


    public ChassisHardware(HardwareMap hwMap) {
        leftMotors = new MotorGroup(hwMap, "front_left", "rear_left");
        rightMotors = new MotorGroup(hwMap, "front_right", "rear_right");

        frontLeft = hwMap.get(DcMotor.class, "front_left");
        frontRight = hwMap.get(DcMotor.class, "front_right");
        rearLeft = hwMap.get(DcMotor.class, "rear_left");
        rearRight = hwMap.get(DcMotor.class, "rear_right");

        rightMotors.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
