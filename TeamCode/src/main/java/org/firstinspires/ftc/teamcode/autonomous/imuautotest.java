package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

public class imuautotest extends IMUAutonomous {

    Robot robot;

    double startAngle;

    @Override
    public ArrayList<Callable<Boolean>> setStages() {

        robot = new Robot(hardwareMap);

        ArrayList<Callable<Boolean>> stageList = new ArrayList<>();

        stageList.set(0, new Callable<Boolean>() {
            @Override
            public Boolean call() {

            }
        });

        return stageList;
    }
}
