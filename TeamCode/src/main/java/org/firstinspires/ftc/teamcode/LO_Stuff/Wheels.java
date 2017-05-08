package org.firstinspires.ftc.teamcode.LO_Stuff;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Wheels {

    private DcMotor fr; // Front right
    private DcMotor fl; // Front left
    private DcMotor br; // Back right
    private DcMotor bl; // Back left

    public Wheels(HardwareMap hardwareMap) {
        // Maps hardware
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");

        // Sets directions
        br.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.FORWARD);
    }

    public void move(double power) {
        br.setPower(power);
        bl.setPower(power);
        fr.setPower(power);
        fl.setPower(power);
    }

    public void move(double power, Vector4 directions) {
        br.setPower(directions.getW() * power);
        bl.setPower(directions.getX() * power);
        fr.setPower(directions.getY() * power);
        fl.setPower(directions.getZ() * power);
    }

    public void move(double power, String side) {
        if (side.equals("r")) {
            br.setPower(power);
            fr.setPower(power);
        } else if (side.equals("l")) {
            bl.setPower(power);
            fl.setPower(power);
        }
    }
}
