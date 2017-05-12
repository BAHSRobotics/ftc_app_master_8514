package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Lift {
    private Gate release;
    private DcMotor liftMotor;
    private int multiplier = 4;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.dcMotor.get("lift");
        release = new Gate(hardwareMap, "release");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        release.setPosition(0.0);
    }
    public void raise() {
        liftMotor.setPower(1);
    }
    public void drop() {
        release.toggle();
    }
    public void reset() {
        release.setPosition(0.0);
    }
}
