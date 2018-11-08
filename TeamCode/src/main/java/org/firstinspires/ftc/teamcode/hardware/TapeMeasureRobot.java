package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.MotorGroup;

public class TapeMeasureRobot extends Robot {

    MotorGroup leftDrive, rightDrive;
    DcMotor arm;

    public TapeMeasureRobot(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    @Override
    public void init() {

        leftDrive = new MotorGroup(hardwareMap, "left_front", "left_rear");
        rightDrive = new MotorGroup(hardwareMap, "right_front", "right_rear");

        arm = hardwareMap.dcMotor.get("tape_measure");

    }

    public void extend(double speed) {
        arm.setPower(speed);
    }

    public void retract(double speed) {
        arm.setPower(-speed);
    }

}
