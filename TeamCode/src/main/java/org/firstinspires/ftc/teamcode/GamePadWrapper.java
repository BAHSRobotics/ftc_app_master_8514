package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadWrapper {

    private Gamepad gamepad;

    public enum Buttons {
        X,
        Y
    }

    private boolean isXDown;
    private boolean isYDown;

    public GamePadWrapper(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public boolean getButtonDown(Buttons b) {
        switch (b) {
            case X:
                if (gamepad.x && !isXDown) {
                    isXDown = true;
                    return true;
                } else if (!gamepad.x) {
                    isXDown = false;
                }
                return false;
            case Y:
                if (gamepad.y && !isYDown) {
                    isYDown = true;
                    return true;
                } else if (!gamepad.y) {
                    isYDown = false;
                }
                return false;
            default:
                return false;
        }
    }
}