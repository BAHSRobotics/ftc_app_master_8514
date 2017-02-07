package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
NOTE TO READER:
This Utility File is a draft and contents may differ slightly from current software.
 */

public class Arm {
    private DcMotorSimple sweeper;
    private DcMotor armMotor;
    private int multiplier = 1;

    public Arm(DcMotor motor, DcMotorSimple motorSimple) {
        /* Initializes Arm */
        armMotor = motor;
        sweeper = motorSimple;

    }

    public void mapHardware(HardwareMap hardwareMap) {
        armMotor = hardwareMap.dcMotor.get("catapultArm");
        sweeper = hardwareMap.dcMotor.get("sweeper");
        armMotor.setDirection(DcMotor.Direction.REVERSE);

    }
    public void initArm() {
        /* Initializes catapult arm and sets the desired position */
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(0);
    }

    public void rotateArm() {
        /* Turns Motor a full revolution, explained in Control Math. */
        armMotor.setTargetPosition(multiplier * 1650);
        armMotor.setPower(100);
        multiplier++;
    }
    public void sweepPower(double power) {
        /* Sets the power of the sweeping device */
        sweeper.setPower(power);
    }

    public boolean revComplete() {
        if (armPosition() >= armRange()[0] && armPosition() <= armRange()[1]) {
            return true;
        } else {
            return false;
        }
    }
    private int[] armRange() {
        return new int[]{
                armMotor.getTargetPosition() - 15,
                armMotor.getTargetPosition() + 15
        };
    }

    public int armPosition() {
        /* Returns the current position of the catapult */
        return armMotor.getCurrentPosition();
    }
}