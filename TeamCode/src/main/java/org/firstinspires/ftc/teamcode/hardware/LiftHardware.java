package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jacktwamb52 on 11/16/2017.
 */

public class LiftHardware {

    public final Servo servo;

    public final DcMotor leftMotor;
    public final DcMotor rightMotor;

    public DcMotor mainLift;

    public LiftHardware(HardwareMap hwMap) {

        leftMotor = hwMap.get(DcMotor.class, "left_lift");
        rightMotor = hwMap.get(DcMotor.class, "right_lift");

        //mainLift = hwMap.get(DcMotor.class, "lift");

        servo = hwMap.get(Servo.class, "servo");
    }



}
