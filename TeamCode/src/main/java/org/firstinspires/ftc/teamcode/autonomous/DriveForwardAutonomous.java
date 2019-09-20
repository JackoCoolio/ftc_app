package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
import org.firstinspires.ftc.teamcode.hardware.TapeMeasureRobot;

/**
 * Created by jdwam on 12/2/2018.
 */
@Disabled
@Autonomous (name = "DriveForwardAutonomous:")
public class DriveForwardAutonomous extends IMUAutonomous {
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
                            if (runtime.seconds() < 1.5) {
                                tapeMeasureRobot.rightDrive.setPower(-1d);
                                tapeMeasureRobot.leftDrive.setPower(-1d);
                                return true;
                            } else {
                                tapeMeasureRobot.rightDrive.zero();
                                tapeMeasureRobot.leftDrive.zero();
                                return false;
                            }
                        }
                    }
            };
        }
}
