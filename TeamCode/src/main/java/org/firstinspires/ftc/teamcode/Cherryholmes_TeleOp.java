package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.util.Arm;
@TeleOp(name="CherryHolmes", group="Iterative Opmode")

public class Cherryholmes_TeleOp extends OpMode
{
    /* Declare OpMode members. */
    private int i = 0;
    private boolean canShoot = true;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fr = null;
    private DcMotor fl = null;
    private DcMotor br = null;
    private DcMotor bl = null;
    private Servo ballDrop = null;
    private Servo liftDrop = null;
    private Arm arm = new Arm(null, null);
    private DcMotor wheels[] = new DcMotor[4];
    private Double max = 1.0;
    private Double[] power = {0.0, 0.0, 0.0, 0.0};
    private int[][] direction = {
            { 1,  1,  1,  1}, //forward vector
            { 1, -1, -1,  1}, //right vector
            {-1,  1, -1,  1}, //rotate vector
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
        ballDrop = hardwareMap.servo.get("ballDrop");
        liftDrop = hardwareMap.servo.get("liftDrop");
        arm.mapHardware(hardwareMap);

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);
        ballDrop.setPosition(0.0);
        liftDrop.setPosition(0.0);
        arm.initArm();
         telemetry.addData("Status", "Initialized");

        wheels[0] = br; //odd numbered are in the back
        wheels[2] = fr; //even numbered are in the front
        wheels[1] = bl; //higher numbers are on the left
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
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        if (gamepad1.dpad_down || gamepad1.dpad_up || gamepad1.dpad_right || gamepad1.dpad_left || gamepad1.left_bumper || gamepad1.right_bumper) {
            if (gamepad1.dpad_up)       for (i = 0; i < 4; i++) power[i] = power[i] + direction[0][i]*max;
            if (gamepad1.dpad_down)     for (i = 0; i < 4; i++) power[i] = power[i] - direction[0][i]*max;
            //TODO:
            if (gamepad1.dpad_right)    for (i = 0; i < 4; i++) power[i] = power[i] + direction[1][i]*max;
            if (gamepad1.dpad_left)     for (i = 0; i < 4; i++) power[i] = power[i] - direction[1][i]*max;

            if (gamepad1.left_bumper)   for (i = 0; i < 4; i++) power[i] = power[i] + direction[2][i]*max;
            if (gamepad1.right_bumper)  for (i = 0; i < 4; i++) power[i] = power[i] - direction[2][i]*max;
            for (i=0; i<4; i++) wheels[i].setPower(power[i]);
        } else {
            wheels[0].setPower(-gamepad1.right_stick_y);
            wheels[2].setPower(-gamepad1.right_stick_y);
            wheels[1].setPower(-gamepad1.left_stick_y);
            wheels[3].setPower(-gamepad1.left_stick_y);
        }
        for (int i = 0; i < 4; i++) {
            power[i] = 0.0;
        }

        // arm and sweeper
        if (arm.revComplete() && (gamepad2.right_trigger > 0.3) && canShoot) {
            arm.rotateArm();
            canShoot = false;
        } else if (!gamepad2.start && !canShoot) {
            canShoot = true;
        }
        // boolean Arm and sweeper
        if (gamepad1.a) {
            arm.sweepPower(1);
        }
        if (gamepad1.x){
            arm.sweepPower(0);
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}