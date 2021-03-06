//package org.firstinspires.ftc.teamcode.autonomous;
//
//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.autonomous.main.IMUAutonomous;
//import org.firstinspires.ftc.teamcode.hardware.Robot;
//
//public class JewelHitter {
//
//    public enum Color {
//        Unknown,
//        Red,
//        Blue
//    }
//
//    protected JewelHitter.Color friendlyColor = Color.Blue;
//
//    // CONFIG FOR EASY TWEAKING //
//    private final double defaultMiddle = 0.5;
//    private final double pushDistance = 0.25;
//    private final double servoSpeed = 0.02;
//    private final double downPosition = 1;
//    private final double upPosition = 0.1;
//
//    private boolean pushed = false;
//    double push;
//    private ElapsedTime timer = new ElapsedTime();
//    private ElapsedTime giveUpTimer = new ElapsedTime();
//
//    private boolean seen = false;
//
//    private Robot robot;
//
//    public JewelHitter(Robot robot, Color friendlyColor) {
//        this.robot = robot;
//        this.friendlyColor = friendlyColor;
//    }
//
//    public void start() {
//        robot.lrServo.setPosition(defaultMiddle);
//        robot.udServo.setPosition(downPosition);
//        giveUpTimer.reset();
//    }
//
//    public boolean run(Telemetry telemetry) {
//        // Lower vertical servo.
//        robot.udServo.setPosition(lerp(robot.udServo.getPosition(), downPosition, servoSpeed));
//
//        // Useful debug information.
//        telemetry.addData("Seen",String.valueOf(seen));
//        telemetry.addData("Pushed",String.valueOf(pushed));
//        telemetry.addData("Red",robot.colorSensor.red());
//        telemetry.addData("Green",robot.colorSensor.green());
//        telemetry.addData("Blue",robot.colorSensor.blue());
//
//
//
//        if (!seen) {
//            Color foundColor = determineColor(robot.colorSensor);
////            telemetry.addData("Color", foundColor.toString());
//
//            timer.reset(); // This will run for the last time when we see the ball.
//
//            if (foundColor.equals(JewelHitter.Color.Unknown)) { // Determine how much/whether or not to push.
//                push = defaultMiddle;
//
//                if (giveUpTimer.seconds() > 5) {
//                    seen = true;
//                }
//            } else if (foundColor.equals(friendlyColor)) {
//                seen = true;
//                push = defaultMiddle + pushDistance;
//            } else {
//                push = defaultMiddle - pushDistance;
//                seen = true;
//            }
//
//            return false;
//
//        } else {
//            if (timer.seconds() > .25 && timer.seconds() < 1.5) { //Wait to make sure that the arm is all the way down.
//                robot.lrServo.setPosition(push);
//                pushed = true;
//            }
//
//            if (timer.seconds() > 1)
//                robot.udServo.setPosition(lerp(robot.udServo.getPosition(), upPosition, servoSpeed * 3));
//
//            if (timer.seconds() > 1.5)
//                robot.lrServo.setPosition(lerp(robot.lrServo.getPosition(), defaultMiddle, servoSpeed / 3));
//
//            if (timer.seconds() > 2.5 && robot.udServo.getPosition() == upPosition)
//                return true;
//
//            return false;
//        }
//    }
//
//    private double lerp(double start, double target, double speed) {
//
//        if (start == target) return start; // If we are already at the target position, don't change anything.
//
//        double result = start;
//
//        if (target > start) {
//            result += speed;
//            if (result > target)
//                result = target;
//        } else {
//            result -= speed;
//            if (result < target)
//                result = target;
//        }
//
//        return result;
//
//    }
//
//    private Color determineColor(ColorSensor sensor) {
//
//        if (sensor.red() > sensor.blue()+2) {
//            return Color.Red;
//        } else if (sensor.blue() > sensor.red()+2) {
//            return Color.Blue;
//        } else {
//            return Color.Unknown;
//        }
//
//    }
//
//    public static IMUAutonomous.Stage getStage(final Robot robot, final Color friendlyColor, final Telemetry telemetry) {
//        return new IMUAutonomous.Stage() {
//
//            JewelHitter jewelHitter;
//
//            @Override public void setup(double heading, ElapsedTime runtime) {
//                jewelHitter = new JewelHitter(robot, friendlyColor);
//                jewelHitter.start();
//            }
//
//            @Override public boolean run(double heading, ElapsedTime runtime) { return jewelHitter.run(telemetry); }
//        };
//    }
//
//}
