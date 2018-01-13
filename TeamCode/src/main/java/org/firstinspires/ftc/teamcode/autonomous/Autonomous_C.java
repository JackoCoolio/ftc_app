package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Created by jacktwamb52 on 1/12/2018.
 */

@Autonomous(name = "Jewel Hitter test")
public class Autonomous_C extends IMUAutonomous {

    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);

        return new Stage[] {
                new Stage() { // Jewel hitter

                    JewelHitter jewelHitter;

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        jewelHitter = new JewelHitter(robot, JewelHitter.Color.Blue);

                        jewelHitter.start();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        return jewelHitter.run();
                    }
                },
                new Stage() {

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 3) {
                            robot.leftMotors.setPower(0.2);
                            robot.rightMotors.setPower(0.2);
                            return false;
                        } else {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        }
                    }
                },
                new Stage() {

                    double startHeading;

                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading <= startHeading - 135) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(-0.2);
                            robot.rightMotors.setPower(0.2);
                            return false;
                        }
                    }
                }
        };
    }
}
