package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

  // @Autonomous(...) is the other common choice

public class DavidLopez_TeleOp2 extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fl = null;
    private DcMotor fr = null;
    private DcMotor bl = null;
    private DcMotor br = null;
    private DcMotor sweeper = null;

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
         br  = hardwareMap.dcMotor.get("br");
         bl = hardwareMap.dcMotor.get("bl");
         fr  = hardwareMap.dcMotor.get("fr");
         fl = hardwareMap.dcMotor.get("fl");
        sweeper = hardwareMap.dcMotor.get("sweeper");
        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        br.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        bl.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        fr.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        fl.setDirection(DcMotor.Direction.FORWARD);
        // telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {



    }
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



        if (gamepad1.right_bumper) {
            br.setPower(-0.5);
            fr.setPower(0.5);
            fl.setPower(0.5);
            bl.setPower(-0.5);
        } else if (gamepad1.left_bumper){
            br.setPower(0.5);
            fr.setPower(-0.5);
            fl.setPower(-0.5);
            bl.setPower(0.5);
        } else {
            fr.setPower(gamepad1.right_stick_y);
            br.setPower(gamepad1.right_stick_y);
            bl.setPower(gamepad1.left_stick_y);
            fl.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.x)  {
            sweeper.setPower(gamepad1.right_trigger);

        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
