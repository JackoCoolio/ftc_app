package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

public abstract class IMUAutonomous extends OpMode {

    private int stage = 0;
    protected BNO055IMU imu;

    private Stage[] stages;
    boolean runSetup = true;

    private ElapsedTime runtime;

    @Override public final void start() {
        stages = setStages();
        runtime.reset();
    }

    @Override public final void init() {
        imu = hardwareMap.get(BNO055IMU.class, getIMUName());
        imu.initialize(getParameters());
        runtime = new ElapsedTime();
    }

    @Override public final void loop() {

        if (stage == stages.length) return;

        boolean cont;

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        try {
            if (runSetup) stages[stage].setup(angles, runtime);
            cont = stages[stage].run(angles, runtime);
        } catch (Exception e) {
            cont = false;
            telemetry.addData("ERROR","An exception occurred in user code!");
        }

        runSetup = false;
        if (cont) {
            stage++;
            runSetup = true;
        }
    }

    protected String getIMUName() {
        return "imu";
    }

    protected BNO055IMU.Parameters getParameters() {
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

    public interface Stage {
        void setup(Orientation angles, ElapsedTime runtime);
        boolean run(Orientation angles, ElapsedTime runtime);
    }

}
