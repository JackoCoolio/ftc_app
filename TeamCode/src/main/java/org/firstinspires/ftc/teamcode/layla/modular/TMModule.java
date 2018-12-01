package org.firstinspires.ftc.teamcode.layla.modular;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Module;

import java.text.DecimalFormat;

public class TMModule extends Module {

    private DcMotor tapeMeasure;
    private double power;

    enum Status {
        Extending,
        Retracting,
        Idle
    }
    private Status status;

    public TMModule(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {
        super(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init() {
        tapeMeasure = hardwareMap.dcMotor.get("tape_measure");
    }

    @Override
    public void loop() {
        power = -gamepad2.left_stick_y;
        tapeMeasure.setPower(power);
        updateStatus();
    }

    private void updateStatus() {
        if (power > 0) {
            status = Status.Extending;
        } else if (power < 0) {
            status = Status.Retracting;
        } else {
            status = Status.Idle;
        }
    }

    @Override
    public void telemetry() {
        telemetry.addData("Tape Measure Power ", power + " " + status.toString());
    }
}
