package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
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

                    public void setup(Orientation angles) {
                        startHeading = angles.firstAngle;
                    }

                    public boolean run(Orientation angles) {
                        if (angles.firstAngle > startHeading + 90) {
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

                    public void setup(Orientation angles) {
                        startHeading = angles.firstAngle;
                    }

                    public boolean run(Orientation angles) {
                        if (angles.firstAngle < startHeading - 90) {
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
