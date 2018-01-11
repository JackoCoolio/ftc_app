package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by jacktwamb52 on 1/10/2018.
 */

public abstract class IMUAutonomous extends OpMode {

    private int stage = 0;
    protected BNO055IMU imu;

    private ArrayList<Callable<Boolean>> stages;

    @Override public final void start() {
        stages = setStages();
    }

    @Override public final void init() {
        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU".
        imu = hardwareMap.get(BNO055IMU.class, getIMUName());
        imu.initialize(getParameters());
    }

    @Override public final void loop() {
        boolean cont;

        try {
            cont = stages.get(stage).call();
        } catch (Exception e) {
            cont = false;
            telemetry.addData("ERROR","An exception occurred in user code!");
        }

        if (cont) stage++;
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

    public abstract ArrayList<Callable<Boolean>> setStages();

}
