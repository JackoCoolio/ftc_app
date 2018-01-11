package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

@Autonomous(name = "LKSAJDLAKSJDASKJD")
public class imuautotest extends IMUAutonomous {

    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);

        Stage[] stageList = new Stage[] {
                new Stage() {

                    double startHeading;

                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading > startHeading + 90) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(0.95);
                            robot.rightMotors.setPower(-0.95);
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
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(-0.95);
                            robot.rightMotors.setPower(0.95);
                            return false;
                        }
                    }
                }
        };



        return stageList;
    }
}
