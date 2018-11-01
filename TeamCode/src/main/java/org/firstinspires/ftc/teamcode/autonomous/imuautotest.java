package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

@Disabled
@Autonomous(name = "imaautotest")
public class imuautotest extends IMUAutonomous {

    GlyphRobot glyphRobot;

    @Override
    public Stage[] setStages() {

        glyphRobot = new GlyphRobot(hardwareMap);

        Stage[] stageList = new Stage[] {
                new Stage() {

                    double startHeading;

                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading > startHeading + 90) {
                            glyphRobot.leftMotors.zero();
                            glyphRobot.rightMotors.zero();
                            return true;
                        } else {
                            glyphRobot.leftMotors.setPower(0.95);
                            glyphRobot.rightMotors.setPower(-0.95);
                            return false;
                        }
                    }
                },
                new Stage() {

                    double startHeading;

                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading < startHeading - 90) {
                            glyphRobot.leftMotors.zero();
                            glyphRobot.rightMotors.zero();
                            return true;
                        } else {
                            glyphRobot.leftMotors.setPower(-0.95);
                            glyphRobot.rightMotors.setPower(0.95);
                            return false;
                        }
                    }
                }
        };



        return stageList;
    }
}
