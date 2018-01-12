package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * Created by jacktwamb52 on 1/12/2018.
 */

public class JewelHitter {

    public enum Color {
        Unknown,
        Red,
        Blue
    }

    protected JewelHitter.Color friendlyColor;

    // CONFIG FOR EASY TWEAKING //
    private final double defaultMiddle = 0.5;
    private final double pushDistance = 0.25;
    private final double servoSpeed = 0.02;
    private final double downPosition = 1;
    private final double upPosition = 0.1;

    private boolean pushed = false;
    private ElapsedTime timer = new ElapsedTime();

    private Robot robot;

    public JewelHitter(Robot robot, Color friendlyColor) {
        this.robot = robot;
        this.friendlyColor = friendlyColor;
    }

    public void start() {
        robot.lrServo.setPosition(defaultMiddle);
        robot.udServo.setPosition(downPosition);
    }

    public boolean run(Telemetry telemetry) {
        // Lower vertical servo.
        robot.udServo.setPosition(lerp(robot.udServo.getPosition(), downPosition, servoSpeed));

        // Useful debug information.
        telemetry.addData("Pushed",String.valueOf(pushed));
        telemetry.addData("Red",robot.colorSensor.red());
        telemetry.addData("Green",robot.colorSensor.green());
        telemetry.addData("Blue",robot.colorSensor.blue());



        if (!pushed) {
            JewelHitter.Color foundColor = determineColor(robot.colorSensor);
            telemetry.addData("Color", foundColor.toString());

            double push;
            if (foundColor.equals(JewelHitter.Color.Unknown)) {
                push = defaultMiddle;
            } else if (foundColor.equals(friendlyColor)) {
                push = defaultMiddle - pushDistance;
                pushed = true;
            } else {
                push = defaultMiddle + pushDistance;
                pushed = true;
            }
            robot.lrServo.setPosition(push);
            timer.reset();

            return false;

        } else {
            if (timer.seconds() > 2.25)
                robot.udServo.setPosition(lerp(robot.udServo.getPosition(), upPosition, servoSpeed * 3));

            if (timer.seconds() > 1.5)
                robot.lrServo.setPosition(lerp(robot.lrServo.getPosition(), defaultMiddle, servoSpeed / 5));

            if (timer.seconds() > 3)
                return true;

            return false;
        }
    }

    private double lerp(double start, double target, double speed) {

        if (start == target) return start; // If we are already at the target position, don't change anything.

        double result = start;

        if (target > start) {
            result += speed;
            if (result > target)
                result = target;
        } else {
            result -= speed;
            if (result < target)
                result = target;
        }

        return result;

    }

    private JewelHitter.Color determineColor(ColorSensor sensor) {

        if (sensor.red() > sensor.green() + sensor.blue()) {
            return JewelHitter.Color.Red;
        } else if (sensor.blue() > sensor.green() + sensor.red()) {
            return JewelHitter.Color.Blue;
        } else {
            return JewelHitter.Color.Unknown;
        }

    }

}
