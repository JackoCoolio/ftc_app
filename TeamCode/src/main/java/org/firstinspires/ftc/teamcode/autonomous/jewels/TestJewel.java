package org.firstinspires.ftc.teamcode.autonomous.jewels;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.GlyphRobot;

@Disabled
@Autonomous(name="ServoArm")
public class TestJewel extends OpMode {

    private GlyphRobot glyphRobot;

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

        glyphRobot = new GlyphRobot(hardwareMap);

    }

    @Override
    public void start() {

        // Set servo positions.
        glyphRobot.lrServo.setPosition(defaultMiddle);
        glyphRobot.udServo.setPosition(downPosition);

    }

    @Override
    public void loop() {

        // Lower vertical servo.
        glyphRobot.udServo.setPosition(lerp(glyphRobot.udServo.getPosition(), downPosition, servoSpeed));

        // Useful debug information.
        telemetry.addData("Pushed",String.valueOf(pushed));
        telemetry.addData("Red", glyphRobot.colorSensor.red());
        telemetry.addData("Green", glyphRobot.colorSensor.green());
        telemetry.addData("Blue", glyphRobot.colorSensor.blue());



        if (!pushed) {
            Color foundColor = determineColor(glyphRobot.colorSensor);
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
            glyphRobot.lrServo.setPosition(push);
            timer.reset();

        } else {
            if (timer.seconds() > 2.25)
                glyphRobot.udServo.setPosition(lerp(glyphRobot.udServo.getPosition(), upPosition, servoSpeed * 3));

            if (timer.seconds() > 1.5)
                glyphRobot.lrServo.setPosition(lerp(glyphRobot.lrServo.getPosition(), defaultMiddle, servoSpeed / 5));
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