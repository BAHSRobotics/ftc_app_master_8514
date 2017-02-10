package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.BackupWheels;


@TeleOp(name = "SmallTeleOp", group = "Iterative Opmode")
public class SmallTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private double rightWheelPower = 0;
    private double leftWheelPower = 0;

    private BackupWheels wheels = new BackupWheels();

    private ColorSensor colorSensor = null;
    private Servo servo = null;


    public void init() {
        telemetry.addLine("Status: Initialized");
        wheels.mapWheels(hardwareMap);
        wheels.setDirections();
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        colorSensor.enableLed(false);
        servo = hardwareMap.servo.get("servo");
    }

    public void loop() {
        controlWheels();

        if (colorSensor.blue() >= 5) {
            servo.setPosition(0.2);
        } else if (colorSensor.red() >= 5) {
            servo.setPosition(0);
        }

        String telemetryMessage = "";
        telemetryMessage += "Red: " + colorSensor.red();
        telemetryMessage += "\nGreen: " + colorSensor.green();
        telemetryMessage += "\nBlue: " + colorSensor.blue();
        telemetryMessage += "\nAlpha: " + colorSensor.alpha();
        telemetryMessage += "\nServo Position" + servo.getPosition();
        telemetryMessage += "\n";

        telemetry.addLine(telemetryMessage);
    }

    public void controlWheels() {
        if (gamepad1.dpad_right) {
            wheels.translateRight();
        } else if (gamepad1.dpad_left) {
            wheels.translateLeft();
        } else if (gamepad1.dpad_up) {
            wheels.moveWheels(1);
        } else if (gamepad1.dpad_down) {
            wheels.moveWheels(-1);
        } else if (gamepad1.right_bumper) {
            wheels.moveWheels(1, "right");
            wheels.moveWheels(-1, "left");
        } else if (gamepad1.left_bumper) {
            wheels.moveWheels(-1, "right");
            wheels.moveWheels(1, "left");
        } else if (gamepad1.right_stick_y != 0 || gamepad1.left_stick_y != 0) {
            wheels.moveWheels(gamepad1.right_stick_y, "right");
            wheels.moveWheels(gamepad1.left_stick_y, "left");
        } else {
            wheels.moveWheels(0);
        }
    }
}
