package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class LiftHardware {

    public Servo servo;

    public MotorGroup motors;

    public DcMotor mainLift;

    public LiftHardware(HardwareMap hwMap) {
        motors = new MotorGroup(hwMap, "lift_left", "right_lift");

        mainLift = hwMap.get(DcMotor.class, "lift");

        servo = hwMap.get(Servo.class, "servo");
    }



}
