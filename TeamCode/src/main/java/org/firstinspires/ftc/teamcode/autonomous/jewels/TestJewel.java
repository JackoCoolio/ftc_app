package org.firstinspires.ftc.teamcode.autonomous.jewels;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name="ServoArm")
public class TestJewel extends OpMode {

    private Robot robot;

    private enum Color {
        Unknown,
        Red,
        Blue
    }

    protected Color friendlyColor = Color.Blue;

    // CONFIG FOR EASY TWEAKING //
    private final double defaultMiddle = 0.5;
    private final double pushDistance = 0.25;
    private final double servoSpeed = 0.02;
    private final double downPosition = 1;
    private final double upPosition = 0.1;

    private boolean pushed = false;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {

        robot = new Robot(hardwareMap);

    }

    @Override
    public void start() {

        // Set servo positions.
        robot.lrServo.setPosition(defaultMiddle);
        robot.udServo.setPosition(downPosition);

    }

    @Override
    public void loop() {

        // Lower vertical servo.
        robot.udServo.setPosition(lerp(robot.udServo.getPosition(), downPosition, servoSpeed));

        // Useful debug information.
        telemetry.addData("Pushed",String.valueOf(pushed));
        telemetry.addData("Red",robot.colorSensor.red());
        telemetry.addData("Green",robot.colorSensor.green());
        telemetry.addData("Blue",robot.colorSensor.blue());



        if (!pushed) {
            Color foundColor = determineColor(robot.colorSensor);
            telemetry.addData("Color", foundColor.toString());

            double push;
            if (foundColor.equals(Color.Unknown)) {
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

        } else {
            if (timer.seconds() > 2.25)
                robot.udServo.setPosition(lerp(robot.udServo.getPosition(), upPosition, servoSpeed * 3));

            if (timer.seconds() > 1.5)
                robot.lrServo.setPosition(lerp(robot.lrServo.getPosition(), defaultMiddle, servoSpeed / 5));
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

    private Color determineColor(ColorSensor sensor) {

        if (sensor.red() > sensor.green() + sensor.blue()) {
            return Color.Red;
        } else if (sensor.blue() > sensor.green() + sensor.red()) {
            return Color.Blue;
        } else {
            return Color.Unknown;
        }

    }

}