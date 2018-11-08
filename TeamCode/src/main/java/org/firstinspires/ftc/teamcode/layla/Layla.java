package org.firstinspires.ftc.teamcode.layla;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;

public class Layla extends OpMode {

    TapeMeasureRobot robot;

    @Override
    public void init() {

        robot = new TapeMeasureRobot(hardwareMap);

    }

    @Override
    public void loop() {



    }
}
