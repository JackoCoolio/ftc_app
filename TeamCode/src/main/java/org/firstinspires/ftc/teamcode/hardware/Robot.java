package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Robot {

    protected HardwareMap hardwareMap;
    public ElapsedTime timer;

    public Robot(HardwareMap hardwareMap) {
        timer = new ElapsedTime();
        this.hardwareMap = hardwareMap;

        init();
    }

    public abstract void init();

}
