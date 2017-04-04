package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.util.Arm;
import org.firstinspires.ftc.teamcode.util.Wheels;

/*
NOTES:
76 encoder ticks per inch
wheel radius is 2 inches
 */

@Autonomous(name="AutoOpCherryholmes", group="Linear Opmode")
public class AutoOpCherryholmes extends LinearOpMode {

    Wheels wheels = new Wheels();
    DcMotor sweeper = null;
    Arm arm = new Arm(null, null);
    GyroSensor gyro = null;

    @Override
    public void runOpMode() {
        wheels.mapWheels(hardwareMap);
        arm.mapHardware(hardwareMap);
        arm.initArm();
        wheels.setDirections();
        wheels.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();

        waitForStart();

        while (gyro.isCalibrating()) {
            telemetry.addLine("Gyro still calibrating");
            telemetry.update();
        }
        telemetry.addLine("Gyro finished calibrating");
        telemetry.update();

        moveWheelsWithEncoder(15, 0.5); // Move 15 inches
        sleep(1000);
        arm.rotateArm(); // Launch the first ball
        sleep(2000);
        arm.sweepPower(0.5); // Put the second ball into place
        sleep(2000);
        arm.rotateArm(); // Launch the second ball
        arm.sweepPower(0); // Stop sweeper
        sleep(2000);
        moveWheelsWithEncoder(36, 0.5); // Move 19 inches
    }

    private void vuforiaStuff(VuforiaTrackables beacons) {
        for (VuforiaTrackable beac : beacons) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

            if (pose != null) {
                    /*
                    VectorF translation = pose.getTranslation();
                    telemetry.addLine(beac.getName() + "-Translation: " + translation);
                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));
                    telemetry.addLine(beac.getName() + "-Degrees: " + degreesToTurn);
                    */
            }
        }
    }

    private void updateTelemetry() {
        telemetry.addLine("Gyro X: " + gyro.rawX());
        telemetry.addLine("Gyro Y: " + gyro.rawY());
        telemetry.addLine("Gyro Z: " + gyro.rawZ());
        telemetry.addLine("Heading: " + gyro.getHeading());
        telemetry.update();
    }

    private void translateWheelsWithEncoder(int inches, double power) {
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        wheels.getWheels()[0].setTargetPosition(inches * 102);
        wheels.getWheels()[1].setTargetPosition(-inches * 102);
        wheels.getWheels()[2].setTargetPosition(-inches * 102);
        wheels.getWheels()[3].setTargetPosition(inches * 102);
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setPower(-power);
        }
        while (wheels.getWheels()[0].isBusy()) {

        }
        wheels.stopWheels();
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    private void moveWheelsWithEncoder(int inches, double power) {
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setTargetPosition(91 * -inches);
        }
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setPower(-power);
        }
        while (wheels.getWheels()[0].isBusy()) {

        }
        wheels.stopWheels();
        for (DcMotor wheel : wheels.getWheels()) {
            wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    private void rotateWheelsWithGyro(int degrees, double power) {
        if (degrees > 0) {
            wheels.moveWheels(power, "right");
            wheels.moveWheels(-power, "left");
            while (gyro.getHeading() <=  degrees) {
                telemetry.addLine(String.valueOf(gyro.getHeading()));
                telemetry.update();
            }
        } else if (degrees < 0) {
            wheels.moveWheels(power, "right");
            wheels.moveWheels(-power, "left");
            while (gyro.getHeading() >=  degrees) {
                telemetry.addLine(String.valueOf(gyro.getHeading()));
                telemetry.update();
            }
        }
        wheels.stopWheels();
    }
}