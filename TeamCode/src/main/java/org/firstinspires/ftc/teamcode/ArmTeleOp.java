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

@TeleOp(name="Arm TeleOp", group="Iterative Opmode")
public class ArmTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private double maxPower = 1;

    private GamePadWrapper g1;

    private Gate gate;
    private Arm arm;
    private boolean canShoot = true;

    public void init() {
        g1 = new GamePadWrapper(gamepad1);
        gate = new Gate(hardwareMap, "gate");
        arm = new Arm(hardwareMap);
        gate.setPosition(0);
        arm.init();
    }

    public void start() {
        runtime.reset();

    }

    public void loop() {
        //telemetry.addData("Status", "Running: " + runtime.toString());

        // Controls gate
        if (g1.getButtonDown(GamePadWrapper.Buttons.X)) {
            gate.toggle();
        }

        // Automated control for catapult
        if (arm.revComplete() && (gamepad1.right_trigger > 0.3) && canShoot) {
            arm.rotateArm();
            canShoot = false;
        } else if(arm.revComplete()){
            canShoot = true;
        } /*else {
            arm.stopArm();
        }*/
        arm.sweepPower(gamepad1.left_trigger);
    }
}
