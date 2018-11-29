package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.AutonomousConstants;
import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;
import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;
import org.firstinspires.ftc.teamcode.utility.MotorGroup;

/**
 * Created by ebenseshe53 on 11/28/2018.
 */

public class TapeMeasureAutonomous extends IMUAutonomous {
    public Stage[] setStages() {

        final TapeMeasureRobot TMrobot = new TapeMeasureRobot(hardwareMap);

        return new Stage[]{
//                JewelHitter.getStage(glyphRobot, JewelHitter.Color.Blue, telemetry), // So you don't have to copy and paste each time.
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DESCEND_TIME) {
                            TMrobot.extend(.25);
                            return false;
                        } else {
                            TMrobot.extend(0);
                            return true;
                        }
                    }
                }
        };
                new Stage() {
                    @Override
                    public void setup(double heading, ElapsedTime runtime) {
                        runtime.reset();
                    }

                    @Override
                    public boolean run(double heading, ElapsedTime runtime) {
                        if (runtime.seconds() < AutonomousConstants.DESCEND_TIME) {
                            TMrobot.retract(.25);
                            return false;
                        } else {
                            TMrobot.retract(0);
                            return true;
                        }
                    }
                };

        new Stage() {
            @Override
            public void setup(double heading, ElapsedTime runtime) {
                runtime.reset();
            }

            @Override
            public boolean run(double heading, ElapsedTime runtime) {
                if (runtime.seconds() < AutonomousConstants.DESCEND_TIME) {
                    TMrobot.leftDrive.setPower(-AutonomousConstants.TURN_SPEED);
                    TMrobot.rightDrive.setPower(AutonomousConstants.TURN_SPEED);
                    return false;
                } else {
                    TMrobot.leftDrive.zero();
                    TMrobot.rightDrive.zero();
                    return true;
                }
            }
        };
    };
}


