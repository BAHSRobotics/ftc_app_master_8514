/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.LO_Stuff.GamePadWrapper;
import org.firstinspires.ftc.teamcode.LO_Stuff.Gate;

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

@TeleOp(name="Cherryholmes: TeleOp", group="Iterative Opmode")  // @Autonomous(...) is the other common choice

public class Cherryholmes_TeleOp extends OpMode
{
    /* Declare OpMode members. */
    private int i = 0;
    private boolean gate_status = true;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fr = null;
    private DcMotor fl = null;
    private DcMotor br = null;
    private DcMotor bl = null;
    private Gate gate = null;
    private GamePadWrapper g1 = null;
    private DcMotor sweeper = null;
    private DcMotor arm = null;
    private DcMotor wheels[] = new DcMotor[4];
    private Double maxSpeed = 1.0;
    private int[] direction = {0, 0, 0, 0};
    private int[][] vectors = {
            { 1,  1,  1,  1}, //forward vector
            { 1,  1,  -1,  -1}, //right vector
            {-1,  1, -1,  1} //rotate vector
    };

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
        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");
        gate = new Gate(hardwareMap);
        g1 = new GamePadWrapper(gamepad1);
        sweeper = hardwareMap.dcMotor.get("sweeper");
        arm = hardwareMap.dcMotor.get("arm");

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        sweeper.setDirection(DcMotor.Direction.FORWARD);
        // telemetry.addData("Status", "Initialized");

        wheels[0] = br; //
        wheels[1] = bl; //
        wheels[2] = fr; //
        wheels[3] = fl;
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
        gate.setPosition(0);
        gate_status = false;
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        // 0.0 is closed (false)
        // 1.0 is open (true)
        if (g1.getButtonDown(GamePadWrapper.Buttons.X)) {
            gate.toggle();
        }

        if (gamepad1.y) maxSpeed = 1.0;
        if (gamepad1.b) maxSpeed = 0.5;
        if (gamepad1.a) maxSpeed = 0.25;

        if (gamepad1.dpad_down || gamepad1.dpad_up || gamepad1.dpad_right || gamepad1.dpad_left || gamepad1.left_bumper || gamepad1.right_bumper) {
            if (gamepad1.dpad_up)       for (i = 0; i <= 3; i++) direction[i] = direction[i] + vectors[0][i];
            if (gamepad1.dpad_down)     for (i = 0; i <= 3; i++) direction[i] = direction[i] - vectors[0][i];
            if (gamepad1.dpad_right)    for (i = 0; i <= 3; i++) direction[i] = direction[i] + vectors[1][i];
            if (gamepad1.dpad_left)     for (i = 0; i <= 3; i++) direction[i] = direction[i] - vectors[1][i];
            if (gamepad1.left_bumper)   for (i = 0; i <= 3; i++) direction[i] = direction[i] - vectors[2][i];
            if (gamepad1.right_bumper)  for (i = 0; i <= 3; i++) direction[i] = direction[i] + vectors[2][i];
            move();
        } else {
            wheels[0].setPower(-gamepad1.right_stick_y);
            wheels[2].setPower(-gamepad1.right_stick_y);
            wheels[1].setPower(-gamepad1.left_stick_y);
            wheels[3].setPower(-gamepad1.left_stick_y);
        }

        // arm and sweeper
        arm.setPower(gamepad1.right_trigger);
        sweeper.setPower(gamepad1.left_trigger);


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public void move() {
        for (i=0; i<=3; i++) wheels[i].setPower(direction[i]*maxSpeed);
        for (i = 0; i <= 3; i++) direction[i] = 0;
    }

}
