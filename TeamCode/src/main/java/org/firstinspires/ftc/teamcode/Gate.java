package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gate {

    private Servo gate;
    private boolean isOpen = true;

    public Gate(HardwareMap hardwareMap, String name) {
        gate = hardwareMap.servo.get(name);
    }

    public void setPosition(double position) {
        gate.setPosition(position);
    }

    public void toggle() {
        if (isOpen) {
            gate.setPosition(0.5);
            isOpen = false;
        } else {
            gate.setPosition(0);
            isOpen = true;
        }
    }
}
