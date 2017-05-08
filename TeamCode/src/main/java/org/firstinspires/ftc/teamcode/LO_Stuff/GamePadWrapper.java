package org.firstinspires.ftc.teamcode.LO_Stuff;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadWrapper {

    private Gamepad gamepad;

    public enum Buttons {
        X
    }

    private boolean isXDown;

    public GamePadWrapper(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public boolean getButtonDown(Buttons b) {
        switch (b) {
            case X:
                if (gamepad.x && !isXDown) {
                    isXDown = true;
                    return true;
                }
                else if (!gamepad.x) {
                    isXDown = false;
                }
                return false;
            default:
                return false;
        }
    }
}
