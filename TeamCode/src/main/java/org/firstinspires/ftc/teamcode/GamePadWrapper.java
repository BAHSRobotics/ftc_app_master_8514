package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadWrapper {

    private Gamepad gamepad;

    public enum Buttons {
        START,
        X,
        Y
    }

    private boolean isStartDown;
    private boolean isXDown;
    private boolean isYDown;

    public GamePadWrapper(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public boolean getButtonDown(Buttons b) {
        switch (b) {
            case START:
                if (gamepad.start && !isStartDown) {
                    isStartDown = true;
                    return true;
                } else if (!gamepad.start) {
                    isStartDown = false;
                }
                return false;
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