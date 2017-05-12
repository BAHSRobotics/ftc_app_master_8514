package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Lift {
    private Gate release;
    private DcMotor liftMotor;
    private int multiplier = 4;
    private boolean hasDropped = false;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.dcMotor.get("lift");
        release = new Gate(hardwareMap, "release");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void init() {
        /* Initializes catapult lift and sets the desired position */
        release.setPosition(0.0);
    }

    public void raise() {
        liftMotor.setPower(1);
    }

    public void lower() {
        liftMotor.setPower(-1);
    }

    public void stop() {
        liftMotor.setPower(0);
    }

    public void drop() {
        release.toggle();
        hasDropped = true;
    }

    public boolean hasDropped() {
        return hasDropped;
    }
}
