package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class ChassisHardware {

    public MotorGroup leftMotors;
    public MotorGroup rightMotors;

    public ChassisHardware(HardwareMap hwMap) {
        leftMotors = new MotorGroup(hwMap, "front_left", "rear_left");
        rightMotors = new MotorGroup(hwMap, "front_right", "rear_right");
    }



}
