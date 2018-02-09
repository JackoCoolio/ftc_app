package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jacktwamb52 on 1/31/2018.
 */

public class RelicArm {

    public enum Mode {
        Pivot,
        Extend
    }

    public Mode mode;

    public Servo hand, shifter, lock;
    public DcMotor relicMotor;
    private boolean closed = true;
    private ElapsedTime timer;
    private boolean lockMoving = false;
    private boolean locked = true;

    public RelicArm(HardwareMap hardwareMap) {

        hand = hardwareMap.servo.get("hand");
        relicMotor = hardwareMap.dcMotor.get("relicmotor");
        relicMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lock = hardwareMap.servo.get("lock");
        shifter = hardwareMap.servo.get("shifter");
    }

    public void lock()
    {
        lock.setPosition(0.0);
        locked = true;
    }

    public void unlock()
    {
        if (!lockMoving && locked) {
            lockMoving = true;
            timer.reset();
            lock.setPosition(1.0);
        } else {
            if (timer.seconds() > .5) {
                locked = false;
                lockMoving = false;
            }
        }
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
            lock();
            shifter.setPosition(1.0);
        }
    }

    public void motor(double a, Telemetry telemetry) {
        if (locked && mode.equals(Mode.Pivot)){
            unlock();
            return;
        } else if (!locked && mode.equals(Mode.Extend)) {
            lock();
            return;
        }
        if (lockMoving) return;

        if (mode.equals(Mode.Pivot)) {
            relicMotor.setPower(a/4);
        } else if (mode.equals(Mode.Extend)) {
            relicMotor.setPower(a);
        }
    }

}
