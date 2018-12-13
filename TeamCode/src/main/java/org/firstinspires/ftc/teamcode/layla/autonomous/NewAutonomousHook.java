package org.firstinspires.ftc.teamcode.layla.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;

/**
 * Created by jdwam on 12/2/2018.
 */
@Disabled
@Autonomous (name = "AutonomousHook:")
public class NewAutonomousHook extends IMUAutonomous {

    TapeMeasureRobot tapeMeasureRobot;

    @Override
    public Stage[] setStages() {
        tapeMeasureRobot = new TapeMeasureRobot(hardwareMap);

        return new Stage[]{
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DESCENDING_TIME)
                        {
                            tapeMeasureRobot.extend(AutonomousConstants.LIFT_SPEED);
                            return false;
                        } else {
                            tapeMeasureRobot.extend(0);
                            return true;
                        }
                    }
                },

                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.TURN_FREE_TIME) {
                            tapeMeasureRobot.leftDrive.setPower(-AutonomousConstants.TURN_SPEED);
                            tapeMeasureRobot.rightDrive.setPower(AutonomousConstants.TURN_SPEED);
                            return true;
                        } else {
                            tapeMeasureRobot.leftDrive.zero();
                            tapeMeasureRobot.rightDrive.zero();
                            return false;
                        }
                    }
                },
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.RETRACT_TIME)
                        {
                            tapeMeasureRobot.retract(AutonomousConstants.LIFT_SPEED);
                            return false;
                        } else {
                            tapeMeasureRobot.retract(0);
                            return true;
                        }
                    }
                },
                new Stage() {
                    @Override
                   public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DRIVE_BACK_TIME) {
                            tapeMeasureRobot.leftDrive.setPower(-1);
                            tapeMeasureRobot.rightDrive.setPower(-1);
                            return false;
                        } else {
                            tapeMeasureRobot.leftDrive.zero();
                              tapeMeasureRobot.rightDrive.zero();
                            return true;
                        }
                    }
        }
        };


    }
}