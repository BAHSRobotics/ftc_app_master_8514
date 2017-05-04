package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class VectorWheels {

    private int i = 0;
    private DcMotor wheels[] = new DcMotor[4];
    private Double max = 1.0;
    private Double[] power = {0.0, 0.0, 0.0, 0.0};
    private int[][] directionPower = {
            { 1,  1,  1,  1}, //forward vector
            { -1, 1, 1,  -1}, //right vector
            {-1,  1, -1,  1}, //rotate vector
    };

    public VectorWheels(DcMotor fr, DcMotor fl, DcMotor br, DcMotor bl) {
        wheels[0] = br;
        wheels[1] = bl;
        wheels[2] = fr;
        wheels[3] = fl;
    }
    public void mapHardware(HardwareMap hardwareMap) {
        wheels[0] = hardwareMap.dcMotor.get("rightFrontMotor");
        wheels[1] = hardwareMap.dcMotor.get("rightBackMotor");
        wheels[2] = hardwareMap.dcMotor.get("leftFrontMotor");
        wheels[3] = hardwareMap.dcMotor.get("leftBackMotor");
    }
    public void setDirection() {
        wheels[2].setDirection(DcMotor.Direction.REVERSE);
        wheels[0].setDirection(DcMotor.Direction.REVERSE);
        wheels[1].setDirection(DcMotor.Direction.FORWARD);
        wheels[3].setDirection(DcMotor.Direction.FORWARD);
    }
    public void move(String direction) {
        switch (direction) {
            case "forward":
                for (i = 0; i < 4; i++) power[i] = power[i] + directionPower[0][i] * max;
                break;
            case "backward":
                for (i = 0; i < 4; i++) power[i] = power[i] - directionPower[0][i] * max;
                break;
            case "left":
                for (i = 0; i < 4; i++) power[i] = power[i] - directionPower[1][i] * max;
                break;
            case "right":
                for (i = 0; i < 4; i++) power[i] = power[i] + directionPower[1][i] * max;
                break;
            case "cw":
                for (i = 0; i < 4; i++) power[i] = power[i] - directionPower[2][i] * max;
                break;
            case "ccw":
                for (i = 0; i < 4; i++) power[i] = power[i] + directionPower[2][i] * max;
                break;
        }
        for (i = 0; i < 4; i++)     wheels[i].setPower(power[i]);
        for (int i = 0; i < 4; i++) power[i] = 0.0;
    }
    public void move(double rightPower, double leftPower) {
        for (i = 0; i < 4; i++) {
            if (i%2 == 0)   wheels[i].setPower(rightPower); //if i is even (righthand motor), set power to rightPower
            else            wheels[i].setPower(leftPower);
        }
    }
}
