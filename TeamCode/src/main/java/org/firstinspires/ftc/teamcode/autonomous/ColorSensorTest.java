package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="ServoArm")
public class ColorSensorTest extends OpMode {

    private enum Color {
        Unknown,
        Red,
        Blue
    }

    private Color friendlyColor = Color.Blue;

    // CONFIG FOR EASY TWEAKING //
    private final double defaultMiddle = 0.5;
    private final double pushDistance = 0.25;
    private final double servoSpeed = 0.001;
    private final double downPosition = -0;

    private Servo lrServo;
    private Servo udServo;

    private ColorSensor sensor;

    private boolean pushed = false;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {

        // Load hardware.
        initHardware();

    }

    @Override
    public void loop() {

        telemetry.addData("UD Servo", udServo.getPosition());
        telemetry.addData("LR Servo", lrServo.getPosition());

        // Lower vertical servo.
        udServo.setPosition(lerp(udServo.getPosition(), downPosition, servoSpeed));

        // Useful debug information.
        telemetry.addData("Pushed",String.valueOf(pushed));
        telemetry.addData("Red",sensor.red());
        telemetry.addData("Green",sensor.green());
        telemetry.addData("Blue",sensor.blue());



        if (!pushed) {
            Color foundColor = determineColor(sensor);
            telemetry.addData("Color", foundColor.toString());

            double push = 0;
            if (foundColor.equals(Color.Unknown)) {
                push = defaultMiddle;
            } else if (foundColor.equals(friendlyColor)) {
                push = defaultMiddle + pushDistance;
                pushed = true;
            } else {
                push = defaultMiddle - pushDistance;
                pushed = true;
            }


            //lrServo.setPosition(lerp(lrServo.getPosition(), push, pushSpeed));
            lrServo.setPosition(push);
            timer.reset();

        } else {
            if (timer.seconds() > 1.5)
                udServo.setPosition(lerp(udServo.getPosition(), 0.5, servoSpeed * 3));

            if (timer.seconds() > 3)
                lrServo.setPosition(lerp(lrServo.getPosition(), 0.5, servoSpeed));
        }

    }

    private void initHardware() {

        lrServo = hardwareMap.servo.get("left_right");
        udServo = hardwareMap.servo.get("up_down");

        sensor = hardwareMap.colorSensor.get("sensor");

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