package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.util.Wheels;

/*
NOTE TO READER:
This OP Mode is a draft and contents may differ slightly from current software.
 */

@TeleOp(name = "BackupTeleOp", group = "Iterative Opmode")
//@Disabled
public class BackupTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private double rightWheelPower = 0;
    private double leftWheelPower = 0;

    private Wheels wheels = new Wheels();
    private DcMotor catapultArm = null;
    private DcMotor sweeper = null;
    private ColorSensor colorSensor = null;
    //private ModernRoboticsI2cRangeSensor ultrasonic = null;
    private GyroSensor gyro = null;

    private double targetSweeperPower = 0;
    private double sweeperPower = 0;

    // Runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addLine("Status: Initialized");

        wheels.mapWheels(hardwareMap);
        catapultArm = hardwareMap.dcMotor.get("catapultArm");
        sweeper = hardwareMap.dcMotor.get("sweeper");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        //ultrasonic = ModernRoboticsI2cRangeSensor.Register;
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        wheels.setDirections();


    }

    // Runs ONCE when the drive hits PLAY
    @Override
    public void start() {
        runtime.reset();
    }

    /* Runs REPEATEDLY after the driver hits PLAY but before they hit STOP.
    Gets called every ~25 milliseconds (~40 times a second) */
    @Override
    public void loop() {
        controlWheels();

        controlSweeper();

        if (gamepad1.y) {
            catapultArm.setPower(-0.5);
        } else {
            catapultArm.setPower(0);
        }

        updateTelemetry();
    }

    private void controlWheels() {
        if (gamepad1.dpad_right) {
            wheels.translateRight();
        } else if (gamepad1.dpad_left) {
            wheels.translateLeft();
        } else if (gamepad1.dpad_up) {
            wheels.moveWheels(1);
        } else if (gamepad1.dpad_down) {
            wheels.moveWheels(-01);
        } else if (gamepad1.left_bumper) {
            wheels.moveWheels(1, "right");
            wheels.moveWheels(-1, "left");
        } else if (gamepad1.right_bumper) {
            wheels.moveWheels(-1, "right");
            wheels.moveWheels(1, "left");
        } else if (gamepad1.right_stick_y != 0 || gamepad1.left_stick_y != 0) {
            wheels.moveWheels(gamepad1.right_stick_y, "right");
            wheels.moveWheels(gamepad1.left_stick_y, "left");
        } else {
            wheels.moveWheels(0);
        }
    }

    private void controlSweeper() {
        //toggle between forward and reverse if A is pressed
        if (gamepad1.a) {
            targetSweeperPower = 1;
        } else if (gamepad1.x)
            targetSweeperPower = 0;

        // Slow sweeper as it reverses so that the chain does not pop off
        double SWEEPER_ACCELERATION = 0.07;
        if (targetSweeperPower > sweeperPower) {
            sweeperPower += SWEEPER_ACCELERATION;
            if (sweeperPower > targetSweeperPower) {
                sweeperPower = targetSweeperPower;
            }
        } else if (targetSweeperPower < sweeperPower) {
            sweeperPower -= SWEEPER_ACCELERATION;
            if (sweeperPower < targetSweeperPower) {
                sweeperPower = targetSweeperPower;
            }
        }

        sweeper.setPower(sweeperPower);
    }

    // Update what the phone displays on the screen
    private void updateTelemetry() {
        String telemetryMessage = "Status: Running for " + runtime.toString();
        telemetryMessage += "\n----------";
        //telemetryMessage += "\nUltrasonic Sensor: " + ultrasonic.getDistance(DistanceUnit.INCH);
        telemetryMessage += "\nRight Wheels Power: " + String.valueOf(gamepad1.right_stick_y);
        telemetryMessage += "\nLeft Wheels Power: " + String.valueOf(gamepad1.left_stick_y);
        telemetryMessage += "\nEncoder: " + wheels.getWheels()[0].getCurrentPosition();
        telemetryMessage += "\nGyro: " + gyro.getHeading();
        telemetryMessage += "\n----------";
        telemetryMessage += "\nSweeper Power: " + sweeperPower;
        telemetryMessage += "\nCatapult Power: " + catapultArm.getPower();
        telemetryMessage += "\n----------";
        telemetryMessage += "\nRed: " + colorSensor.red();
        telemetryMessage += "\nGreen: " + colorSensor.green();
        telemetryMessage += "\nBlue: " + colorSensor.blue();
        telemetry.addLine(telemetryMessage);
    }
}