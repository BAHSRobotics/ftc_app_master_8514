package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Lift {
    private Servo release;
    private DcMotor liftMotor;
    private int multiplier = 4;

    public Lift(DcMotor motor, Servo servo) {
        /* Initializes Lift */
        liftMotor = motor;
        release = servo;
    }

    public void mapHardware(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.dcMotor.get("lift");
        release = hardwareMap.servo.get("release");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);

    }
    public void init() {
        /* Initializes catapult lift and sets the desired position */
        release.setPosition(0.0);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(0);
    }

    public void lift() {
        liftMotor.setTargetPosition(multiplier * 1650);
        liftMotor.setPower(100);
    }
    public void drop() {
        if(release.getPosition() > 0.7) {
            release.setPosition(0.0);
        }
        else if(release.getPosition() < 0.3) {
            release.setPosition(1.0);
        }
    }
    public void reset() {
        liftMotor.setTargetPosition(0);
        liftMotor.setPower(100);
    }

}
