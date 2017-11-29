package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class LiftHardware {

    public final Servo servo;

    public final MotorGroup motors;

    public DcMotor mainLift;

    public LiftHardware(HardwareMap hwMap) {
        motors = new MotorGroup(hwMap, "left_lift", "right_lift");

        //mainLift = hwMap.get(DcMotor.class, "lift");

        servo = hwMap.get(Servo.class, "servo");
    }



}
