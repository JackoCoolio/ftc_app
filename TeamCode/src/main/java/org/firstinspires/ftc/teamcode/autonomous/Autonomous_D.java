package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import java.util.HashMap;

/**
 * Created by jacktwamb52 on 1/12/2018.
 */

@Autonomous(name = "Autonomous D")
public class Autonomous_D extends IMUAutonomous {

    HashMap<RelicRecoveryVuMark, Double> possibilities;
    double targetAngle;

    @Override
    public Stage[] setStages() {
        return new Stage[] {
                new Stage() {

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        //targetAngle = possibilities.get(vuMark);
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return false;
                    }
                }
        };
    }
}
