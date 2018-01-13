package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.Robot;

import static android.R.attr.name;

/**
 * Created by ebenseshe53 on 1/11/2018.
 */
@Autonomous (name = "Autonomous B", group = "concepts")
public class Autonomous_B extends IMUAutonomous {
    Robot robot;

    @Override
    public Stage[] setStages() {

        robot = new Robot(hardwareMap);

        Stage[] stageList = new Stage[]{

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
              new Stage()
                   {
                   @Override
                   public void setup(double heading, ElapsedTime runtime) {
                    runtime.reset();

                   }


            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                if (runtime.seconds() < 6) {
                    robot.leftMotors.setPower(-0.2);
                    robot.rightMotors.setPower(-0.2);
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

                    public void setup(double heading, ElapsedTime runtime) {
                        startHeading = heading;
                    }

                    public boolean run(double heading, ElapsedTime runtime) {
                        if (heading > startHeading - 10) {
                            robot.leftMotors.zero();
                            robot.rightMotors.zero();
                            return true;
                        } else {
                            robot.leftMotors.setPower(0.1);
                            robot.rightMotors.setPower(-0.1);
                            return false;
                        }
                    }
                },

                new Stage()
                {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();

                    }

            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                if (runtime.seconds() < 4) {
                    robot.liftMotors.setPower(.95);
                    return false;
                } else {
                    robot.liftMotors.zero();
                    return true;
                }

            }
        },
                new Stage()
                {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();

                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < 1) {
                            robot.leftMotors.setPower(.25);
                            robot.rightMotors.setPower(.25);
                            return false;
                        } else {
                            robot.liftMotors.zero();
                            return true;
                        }

                    }
                },
        };

        return stageList;
    }

}

