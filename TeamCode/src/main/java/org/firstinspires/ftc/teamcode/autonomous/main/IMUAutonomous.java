package org.firstinspires.ftc.teamcode.autonomous.main;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.HashMap;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

public abstract class IMUAutonomous extends OpMode {

    /*
    Positive is clockwise.
    Negative is counter-clockwise.
     */

    // VUFORIA //
    boolean useVuforia = true;
    private boolean foundVuMark = false;
    public RelicRecoveryVuMark vuMark;
    public RelicRecoveryVuMark getVuMark() {return vuMark;}
    private Vuforia vuforia;
    public HashMap<String, HashMap<RelicRecoveryVuMark, Double>> vuMarkAngles;
    public HashMap<String, HashMap<RelicRecoveryVuMark, Double>> vuMarkDistances;

    // STAGE MANAGEMENT //
    private int stage = 0;
    private Stage[] stages;
    private boolean runSetup = true;

    // IMU //
    private double offset = 0;
    private double heading;
    private boolean useIMU = true;
    private BNO055IMU imu;

    // CONTROL //
    private ElapsedTime runtime;

    @Override public final void start() {
        runtime.reset();
        if (useVuforia) vuforia.start();
    }

    @Override public final void init() {

        msStuckDetectInit = 10000;

        setVuMarkAnglesAndDistances();

        stages = setStages();

        if (useVuforia) vuforia = new Vuforia();

        if (useIMU) {
            imu = hardwareMap.get(BNO055IMU.class, getIMUName());
            imu.initialize(getParameters());
        }
        runtime = new ElapsedTime();

        if (useVuforia) vuforia.init(hardwareMap);
    }

    @Override public final void loop() {
        if (!foundVuMark && useVuforia) {
            foundVuMark = vuforia.loop(telemetry);
            return;
        }
        if (useVuforia) vuMark = vuforia.getVuMark();

        if (stage == stages.length) return;

        boolean cont;

        Orientation angles = null;
        if (useIMU) angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        heading = 0d;
        if (useIMU) heading = angles.firstAngle - offset;

        telemetry.addData("[HEADING]",heading);

        if (runSetup) {

            try {
                stages[stage].setup(heading, runtime);
            } catch (Exception e) {
                telemetry.addData("ERROR", "An exception occured in setup()");

                e.printStackTrace();
            }
        }

        try {
            telemetry.addData("Stage",stages[stage].getName(stage));
            cont = stages[stage].run(heading, runtime);
        } catch (Exception e) {

            cont = false;
            telemetry.addData("ERROR","An exception occurred in run()");

            e.printStackTrace();
        }

        runSetup = false;
        if (cont) {
            offset = heading;
            stage++;
            runSetup = true;
        }
    }

    final protected void enableIMU(boolean b) {useIMU=b;}
    final protected void enableVuforia(boolean b) {useVuforia=b;}

    final double getTargetAngle(String corner) {
//        telemetry.addData("abc","Made it to getTargetAngle(" + corner + ")");
//        HashMap<RelicRecoveryVuMark,Double> angles = vuMarkAngles.get(corner);
//        telemetry.addData("abc","Got angles from vuMarkAngles");
//        Double angle = angles.get(vuMark);
//        telemetry.addData("abc","Got the angle. Returning...");
//        return angle;
        return 0d;
    }

    String getIMUName() {
        return "imu";
    }

    private BNO055IMU.Parameters getParameters() {
        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        return parameters;
    }

    public abstract Stage[] setStages();

    public static abstract class Stage {
        public String getName(int index) {
            return String.valueOf(index);
        }
        public void setup(double heading, ElapsedTime runtime) {}
        abstract public boolean run(double heading, ElapsedTime runtime);
    }

    public static void imuDebug(Telemetry telemetry, double startHeading, double heading, double targetPos) {
        telemetry.addData("IMU","--DEBUG--");
        telemetry.addData("   Heading",heading);
        telemetry.addData("   Start Heading",startHeading);
        telemetry.addData("   Target Pos",targetPos);
        telemetry.addData("   To go",(targetPos+startHeading)-heading);
    }

//    public interface Stage {
//        void setup(double heading, ElapsedTime runtime);
//        boolean run(double heading, ElapsedTime runtime);
//    }

    private void setVuMarkAnglesAndDistances() { // - is clockwise
        vuMarkAngles = new HashMap<>();

        vuMarkAngles.put("A", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkAngles.put("B", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkAngles.put("C", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkAngles.put("D", new HashMap<RelicRecoveryVuMark, Double>());

        vuMarkAngles.get("A").put(RelicRecoveryVuMark.LEFT, 70d);
        vuMarkAngles.get("A").put(RelicRecoveryVuMark.CENTER, 55d);
        vuMarkAngles.get("A").put(RelicRecoveryVuMark.RIGHT, 40d);

        vuMarkAngles.get("B").put(RelicRecoveryVuMark.LEFT, -20d);
        vuMarkAngles.get("B").put(RelicRecoveryVuMark.CENTER, -35d);
        vuMarkAngles.get("B").put(RelicRecoveryVuMark.RIGHT, -50d);

        vuMarkAngles.get("C").put(RelicRecoveryVuMark.LEFT, 110d);
        vuMarkAngles.get("C").put(RelicRecoveryVuMark.CENTER, 125d);
        vuMarkAngles.get("C").put(RelicRecoveryVuMark.RIGHT, 140d);

        vuMarkAngles.get("D").put(RelicRecoveryVuMark.LEFT, -160d);
        vuMarkAngles.get("D").put(RelicRecoveryVuMark.CENTER, -145d);
        vuMarkAngles.get("D").put(RelicRecoveryVuMark.RIGHT, -130d);

        vuMarkDistances = new HashMap<>();

        vuMarkDistances.put("A", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkDistances.put("B", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkDistances.put("C", new HashMap<RelicRecoveryVuMark, Double>());
        vuMarkDistances.put("D", new HashMap<RelicRecoveryVuMark, Double>());

        vuMarkDistances.get("A").put(RelicRecoveryVuMark.LEFT, 7.5);
        vuMarkDistances.get("A").put(RelicRecoveryVuMark.CENTER, 11.5);
        vuMarkDistances.get("A").put(RelicRecoveryVuMark.RIGHT, 15.5);

        vuMarkDistances.get("B").put(RelicRecoveryVuMark.LEFT, 7.5);
        vuMarkDistances.get("B").put(RelicRecoveryVuMark.CENTER, 8.5);
        vuMarkDistances.get("B").put(RelicRecoveryVuMark.RIGHT, 12.5);

        vuMarkDistances.get("C").put(RelicRecoveryVuMark.LEFT, 15.5);
        vuMarkDistances.get("C").put(RelicRecoveryVuMark.CENTER, 11.5);
        vuMarkDistances.get("C").put(RelicRecoveryVuMark.RIGHT, 8.0);

        vuMarkDistances.get("D").put(RelicRecoveryVuMark.LEFT, 12.5);
        vuMarkDistances.get("D").put(RelicRecoveryVuMark.CENTER, 8.5);
        vuMarkDistances.get("D").put(RelicRecoveryVuMark.RIGHT, 7.5);


    }

    private static class Vuforia {
        private boolean foundVuMark = false;
        private RelicRecoveryVuMark myVuMark;

        // VUFORIA //
        public static final String TAG = "Vuforia VuMark Sample";

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */
        VuforiaLocalizer vuforia;

        VuforiaTrackables relicTrackables;
        VuforiaTrackable relicTemplate;

        ElapsedTime timer;

        public void init(HardwareMap hardwareMap) {
            timer = new ElapsedTime();

            /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            // OR...  Do Not Activate the Camera Monitor View, to save power
            // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
            parameters.vuforiaLicenseKey = "AWMlC9f/////AAAAGeOXa++ktE94mmUviVs0/wFNxTbeNFXNoDnFbtRGw0i7LV4HQy9CcOMV6LjFVuq98bJ+KcjR97ZSI6DxLWJtxUebeki327eC2XZqu9KeYoUSX6GojrXcOZo5xFBwqvCSiw+WmS8aNuxL3P/RiAe0UoejfeocHRDARewCHM3LUrctLJFXeEbUTscdlbirMkUbwjJ1vCtNJqeOIcapzSe/sYUA8EWthiK4mskGjt99MNpCt9y9QQcPwlCeFT+HMOXyYi2+LRi+hdyA/XlZnkc90B7R8k6GAp4pWHSmKmdRQtows4qw+TJwgiZjqPbdMuKTM/UZjuaoCAIGwCYFDslpLtmOCvX1XdEv46kYk0v/9nzd";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

            /**
             * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
             * in this data set: all three of the VuMarks in the game were created from this one template,
             * but differ in their instance id information.
             * @see VuMarkInstanceId
             */
            relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            relicTemplate = relicTrackables.get(0);
            relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        }

        public void start() {
            relicTrackables.activate();
            timer.reset();
        }

        public boolean loop(Telemetry telemetry) {
            if (!foundVuMark) {
                /**
                 * See if any of the instances of {@link relicTemplate} are currently visible.
                 * {@link RelicRecoveryVuMark} is an enum which can have the following values:
                 * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
                 * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
                 */
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                    myVuMark = vuMark;

                    /* Found an instance of the template. In the actual game, you will probably
                     * loop until this condition occurs, then move on to act accordingly depending
                     * on which VuMark was visible. */
                    telemetry.addData("VuMark", "%s visible", vuMark);

                    foundVuMark = true;

                    return true;
                } else {
                    telemetry.addData("VuMark", "not visible");
                }
            }

            if (timer.seconds() > 5) { // If Vuforia hasn't found the target in 5 seconds, give up.
                myVuMark = RelicRecoveryVuMark.CENTER; // Just say that we found the center one (leaves room for error)
                return true;
            }

            return false;
        }

        public RelicRecoveryVuMark getVuMark() {
            return myVuMark;
        }
    }

}
