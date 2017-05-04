package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.util.Arm;
import org.firstinspires.ftc.teamcode.util.Lift;
import org.firstinspires.ftc.teamcode.util.VectorWheels;

@TeleOp(name="CherryHolmes", group="Iterative Opmode")

public class Cherryholmes_TeleOp extends OpMode
{
    /* Declare OpMode members. */
    private boolean canShoot = true;
    private ElapsedTime runtime = new ElapsedTime();
    private VectorWheels drive = new VectorWheels(null, null, null, null);
    private Arm arm = new Arm(null, null, null);
    private Lift capLift = new Lift(null, null);

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        capLift.mapHardware(hardwareMap);
        arm.mapHardware(hardwareMap);
        drive.mapHardware(hardwareMap);

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        arm.init();
        drive.setDirection();
        capLift.init();
        telemetry.addData("Status", "Initialized");

    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        if (gamepad1.dpad_down || gamepad1.dpad_up || gamepad1.dpad_right || gamepad1.dpad_left || gamepad1.left_bumper || gamepad1.right_bumper) {
            if (gamepad1.dpad_up)       drive.move("forward");
            if (gamepad1.dpad_down)     drive.move("backward");
            if (gamepad1.dpad_right)    drive.move("right");
            if (gamepad1.dpad_left)     drive.move("left");
            if (gamepad1.left_bumper)   drive.move("ccw");
            if (gamepad1.right_bumper)  drive.move("cw");
        } else {
            drive.move(-gamepad1.right_stick_y, -gamepad1.left_stick_y);
        }

        if (gamepad2.b) {
            arm.drop();
        }
        // arm and sweeper
        if (arm.revComplete() && (gamepad2.right_trigger > 0.3) && canShoot) {
            arm.rotateArm();
            canShoot = false;
        } else if (!gamepad2.start && !canShoot) {
            canShoot = true;
        }
        if (gamepad2.a) {
            capLift.drop();
        }
        else if (gamepad2.y) {
            capLift.lift();
        }

        // boolean Arm and sweeper
        arm.sweepPower(gamepad2.left_trigger);
    }
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    capLift.reset();
    }

}


/*
* TODO: 2 teleop: 1 driver, 2 driver
* */