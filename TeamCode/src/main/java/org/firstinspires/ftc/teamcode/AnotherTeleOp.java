package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="AnotherTeleOp", group="Iterative Opmode")
public class AnotherTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private double maxPower = 1;

    private GamePadWrapper g1;

    private Gate gate;
    private Wheels wheels;
    private Arm arm;

    private Vector4 directions = new Vector4();

    public void init() {
        g1 = new GamePadWrapper(gamepad1);

        gate = new Gate(hardwareMap);
        wheels = new Wheels(hardwareMap);
        arm = new Arm(hardwareMap);
    }

    public void start() {
        gate.setPosition(0);
    }

    public void loop() {
        // Controls gate
        if (g1.getButtonDown(GamePadWrapper.Buttons.X)) {
            gate.toggle();
        }

        // Sets maximum power
        if (g1.getButtonDown(GamePadWrapper.Buttons.Y)) {
            if (HelperMath.equals(maxPower, 1)) {
                maxPower = 0.25;
            } else {
                maxPower = 1;
            }
        }

        // Controls wheels
        // Adds and subtracts vectors so the robot can move in different directions using multiple buttons
        directions.resetValues();
        if (gamepad1.dpad_up) { directions.add(Vector4.FORWARD); }
        if (gamepad1.dpad_down) { directions.subtract(Vector4.FORWARD); }
        if (gamepad1.dpad_right) { directions.add(Vector4.RIGHT); }
        if (gamepad1.dpad_left) { directions.subtract(Vector4.RIGHT); }
        if (gamepad1.right_bumper) { directions.add(Vector4.ROTATION); }
        if (gamepad1.left_bumper) { directions.subtract(Vector4.ROTATION); }
        // If none of the above buttons are pressed or cancel out use thumb stick input
        if (directions.equals(Vector4.ZERO)) {
            wheels.move(-gamepad1.right_stick_y, "r");
            wheels.move(-gamepad1.left_stick_y, "l");
        } else {
            wheels.move(maxPower, directions);
        }
    }
}
