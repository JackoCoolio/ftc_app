package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jacktwamb52 on 1/31/2018.
 */

public class RelicArm {

    public enum Mode {
        Pivot,
        Extend
    }

    public Mode mode;

    public Servo hand, shifter;
    public DcMotor relicMotor;
    private boolean closed = true;

    public RelicArm(HardwareMap hardwareMap) {

        hand = hardwareMap.servo.get("hand");
        relicMotor = hardwareMap.dcMotor.get("relicmotor");

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

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode.equals(Mode.Pivot)) {
            shifter.setPosition(0.0);
        } else if (mode.equals(Mode.Extend)) {
            shifter.setPosition(1.0);
        }
    }

    public void motor(double a) {
        if (mode.equals(Mode.Pivot)) {
            relicMotor.setPower(a);
        } else if (mode.equals(Mode.Extend)) {
            relicMotor.setPower(a);
        }
    }

}
