package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;
/**
 * Basically a HashMap with some added DcMotor functionality.
 */
public class MotorGroup {

    private HashMap<String, DcMotor> motors;

    public MotorGroup(HardwareMap _hm, String... _names) {
        motors = new HashMap<>();

        for (String m : _names)
            motors.put(m, _hm.dcMotor.get(m));
    }

    public MotorGroup setDirection(DcMotor.Direction _dir) {
        for (DcMotor motor : motors.values()) {
            motor.setDirection(_dir);
        }
        return this; // Returns itself for easy chaining.
    }

    public void setPower(double _power) {
        for (DcMotor motor : motors.values()) {
            motor.setPower(_power);
        }
    }

    public void setPower(double power, String... _motors) {
        for (String m : _motors)
            motors.get(m).setPower(power);
    }

    public DcMotor getMotor(String _name) {
        return motors.get(_name);
    }

    public MotorGroup setMode(DcMotor.RunMode mode) {
        for (DcMotor motor : motors.values()) {
            motor.setMode(mode);
        }
        return this; // Returns itself for easy chaining.
    }

}