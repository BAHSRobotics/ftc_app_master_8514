package org.firstinspires.ftc.teamcode;

/*
CONTROLS:
a = lower cap ball lift
b = raise cap ball lift
x = open and close gate for the arm
y = change max power between 1 and 0.25
joysticks = tank controls for the wheels
d-pad = controls wheels
bumpers = rotates robot
right trigger = rotates arm
left trigger = rotates sweeper
start = releases cap ball lift
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Two TeleOp", group="Iterative Opmode")
public class TwoTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private double maxPower = 1;

    private GamePadWrapper g1;

    private Gate gate;
    private Wheels wheels;
    private Arm arm;
    private Lift lift;
    private Vector4 directions = new Vector4();

    public void init() {
        g1 = new GamePadWrapper(gamepad1);
        gate = new Gate(hardwareMap, "gate");
        wheels = new Wheels(hardwareMap);
        arm = new Arm(hardwareMap);
        lift = new Lift(hardwareMap);
    }

    public void start() {
        runtime.reset();
        gate.setPosition(0);
        arm.init();
    }

    public void loop() {
        //telemetry.addData("Status", "Running: " + runtime.toString());

        // Controls gate
        if (g1.getButtonDown(GamePadWrapper.Buttons.X)) {
            gate.toggle();
        }

        // Sets maximum power
        if (g1.getButtonDown(GamePadWrapper.Buttons.Y)) {
            if (maxPower == 1) {
                maxPower = 0.25;
            } else {
                maxPower = 1;
            }
        }

        // Drops cap ball holder
        if (g1.getButtonDown(GamePadWrapper.Buttons.START)) { // TODO: Add "&& runtime.seconds() >= 100" when we go to competition
            lift.drop();
        }

        // Uses motor to raise cap ball
        if (gamepad1.b && lift.hasDropped()) {
            lift.raise();
            telemetry.addLine("Raising");
        } else if (gamepad1.a && lift.hasDropped()) {
            lift.lower();
            telemetry.addLine("Lowering");
        } else {
            lift.stop();
            telemetry.addLine("Stopped");
        }
        telemetry.update();

        // Automated control for catapult
        if (arm.revComplete() && (gamepad1.right_trigger > 0.3)) {
            arm.rotateArm();
        } else {
            arm.stopArm();
        }
        arm.sweepPower(gamepad1.left_trigger);

        controlWheels();
    }


    public void controlWheels() {
        // Controls wheels
        // Adds and subtracts vectors so the robot can move in different directions using multiple buttons
        directions.resetValues();
        if (gamepad1.dpad_up)       directions.add(Vector4.FORWARD);
        if (gamepad1.dpad_down)     directions.subtract(Vector4.FORWARD);
        if (gamepad1.dpad_right)    directions.add(Vector4.RIGHT);
        if (gamepad1.dpad_left)     directions.subtract(Vector4.RIGHT);
        if (gamepad1.right_bumper)  directions.add(Vector4.ROTATION);
        if (gamepad1.left_bumper)   directions.subtract(Vector4.ROTATION);

        // If none of the above buttons are pressed or cancel out use thumb stick input
        if(directions.equals(Vector4.ZERO)) {
            wheels.move(-gamepad1.right_stick_y, "r");
            wheels.move(-gamepad1.left_stick_y, "l");
        } else {
            wheels.move(maxPower, directions);
        }
    }
}
