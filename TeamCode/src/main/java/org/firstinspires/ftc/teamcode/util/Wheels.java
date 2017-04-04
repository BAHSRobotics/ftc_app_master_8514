package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
NOTE TO READER:
This Utility File is a draft and contents may differ slightly from current software.
 */

public class Wheels {

    private DcMotor[] wheelsArray = new DcMotor[4];

    public void mapWheels(HardwareMap hardwareMap) {
        /* Assigns software variables to hardware */
        wheelsArray[0] = hardwareMap.dcMotor.get("rightFrontMotor");
        wheelsArray[1] = hardwareMap.dcMotor.get("rightBackMotor");
        wheelsArray[2] = hardwareMap.dcMotor.get("leftFrontMotor");
        wheelsArray[3] = hardwareMap.dcMotor.get("leftBackMotor");

    }

    public void moveWheels(double power) {
        /* Moves all 4 wheels */
        for (DcMotor aWheelsArray : wheelsArray) {
            aWheelsArray.setPower(power * -1);
        }
    }

    public DcMotor[] getWheels() {
        return wheelsArray;
    }

    public void stopWheels() {
        for (DcMotor aWheelsArray : wheelsArray) {
            aWheelsArray.setPower(0);
        }
    }

    public void justOneWheel(int x, double power) {
        wheelsArray[x].setPower(power);
    }

    public void moveWheels(double power, String wheelGroup) {
        /* Moves entire left and/or right wheels. */
        switch (wheelGroup) {
            case "left":
                wheelsArray[2].setPower(power);
                wheelsArray[3].setPower(power);
                break;
            case "right":
                wheelsArray[0].setPower(power);
                wheelsArray[1].setPower(power);
                break;
            case "diagonalLeft":
                wheelsArray[0].setPower(power);
                wheelsArray[3].setPower(power);
                break;
            case "diagonalRight":
                wheelsArray[1].setPower(power);
                wheelsArray[2].setPower(power);
                break;
        }
    }

    public void setDirections() {
        /* Initializes wheel directions */
        wheelsArray[0].setDirection(DcMotor.Direction.REVERSE);
        wheelsArray[1].setDirection(DcMotor.Direction.REVERSE);
        wheelsArray[2].setDirection(DcMotor.Direction.FORWARD);
        wheelsArray[3].setDirection(DcMotor.Direction.FORWARD);
    }

    public void setMode(DcMotor.RunMode mode) {
        for (DcMotor wheel : wheelsArray) {
            wheel.setMode(mode);
        }
    }

    public void translateRight() {
        wheelsArray[0].setPower(1); // Right Front
        wheelsArray[1].setPower(-1); // Right Back
        wheelsArray[2].setPower(-1); // Left Front
        wheelsArray[3].setPower(1); // Left Back
    }

    public void translateLeft() {
        wheelsArray[0].setPower(-1);
        wheelsArray[1].setPower(1);
        wheelsArray[2].setPower(1);
        wheelsArray[3].setPower(-1);
    }

    public void translateRight(double power) {
        wheelsArray[0].setPower(power); // Right Front
        wheelsArray[1].setPower(-power); // Right Back
        wheelsArray[2].setPower(-power); // Left Front
        wheelsArray[3].setPower(power); // Left Back
    }

    public void translateLeft(double power) {
        wheelsArray[0].setPower(-power); // Right Front
        wheelsArray[1].setPower(power); // Right Back
        wheelsArray[2].setPower(power); // Left Front
        wheelsArray[3].setPower(-power); // Left Back
    }

    public void runWithEncoder() {
        for (DcMotor wheel : wheelsArray) {
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            wheel.setPower(0.25);
        }
    }
}