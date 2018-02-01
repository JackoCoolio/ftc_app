package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jacktwamb52 on 1/31/2018.
 */

public class RelicArm {

    public Servo hand;

    private boolean closed = true;

    public RelicArm(HardwareMap hardwareMap) {

        hand = hardwareMap.servo.get("hand");

    }

    public boolean closed() {
        return closed;
    }

    public void open() {
        closed = false;
        hand.setPosition(1.0d);
    }

    public void close() {
        closed = true;
        hand.setPosition(0.0d);
    }

}
