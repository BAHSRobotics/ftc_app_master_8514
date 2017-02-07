package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
NOTE TO READER:
This Utility File is a draft and contents may differ slightly from current software.
 */

public class Wheels {

    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor[] wheelsArray = {
            rightFrontMotor,
            rightBackMotor,
            leftFrontMotor,
            leftBackMotor
    };

    public Wheels(DcMotor rightFrontMotor, DcMotor rightBackMotor,
                  DcMotor leftFrontMotor, DcMotor leftBackMotor) {
        /* Initializes wheels */
        this.rightFrontMotor = rightFrontMotor;
        this.rightBackMotor = rightBackMotor;
        this.leftFrontMotor = leftFrontMotor;
        this.leftBackMotor = leftBackMotor;
    }

    public void mapWheels(HardwareMap hardwareMap) {
        /* Assigns software variables to hardware */
        wheelsArray[2] = hardwareMap.dcMotor.get("rightFrontMotor");
        wheelsArray[3] = hardwareMap.dcMotor.get("rightBackMotor");
        wheelsArray[0] = hardwareMap.dcMotor.get("leftFrontMotor");
        wheelsArray[1] = hardwareMap.dcMotor.get("leftBackMotor");
    }

    public void moveWheels(double power) {
        // Moves all 4 wheels
        for (DcMotor aWheelsArray : wheelsArray) {
            aWheelsArray.setPower(-power);
        }
    }
    public void stopWheels() {
        for (DcMotor aWheelsArray : wheelsArray) {
            aWheelsArray.setPower(0);
        }
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
        }
    }
    public void translate(double power, String direction) {
        switch (direction) {
            case "left":
                wheelsArray[2].setPower(power);
                wheelsArray[1].setPower(power);
                wheelsArray[0].setPower(-power);
                wheelsArray[3].setPower(-power);
                break;
            case "right":
                wheelsArray[2].setPower(-power);
                wheelsArray[1].setPower(-power);
                wheelsArray[0].setPower(power);
                wheelsArray[3].setPower(power);
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
}
