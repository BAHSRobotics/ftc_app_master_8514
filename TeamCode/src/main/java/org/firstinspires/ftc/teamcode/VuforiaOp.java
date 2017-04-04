package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.util.Wheels;

/*
NOTES:
76 encoder ticks per inch
wheel radius is 2 inches
 */

@Autonomous(name="VuforiaOp", group="Linear Opmode")
public class VuforiaOp extends LinearOpMode {

    VuforiaTrackables beacons;
    VuforiaTrackableDefaultListener wheelsImg;

    Wheels wheels = new Wheels();
    DcMotor catapultArm = null;
    DcMotor sweeper = null;
    GyroSensor gyro = null;
    ColorSensor colorSensor = null;

    @Override
    public void runOpMode() {
        initVuforia();
        initRobot();
        waitForStart();

        while (gyro.isCalibrating() && opModeIsActive()) {
            telemetry.addLine("Gyro still calibrating");
            telemetry.update();
        }
        telemetry.addLine("Gyro finished calibrating");
        telemetry.update();

        beacons.activate();

//        catapultArm.setPower(-0.5); // Launch the first ball
//        sleep(1750);
//        sweeper.setPower(0.5); // Turn on the sweeper
//        catapultArm.setPower(0); // Stop the catapult
//        sleep(1750);
//        sweeper.setPower(0); // Turn off the sweeper
//        catapultArm.setPower(-0.5); // Launch the second ball
//        sleep(2000);
//        catapultArm.setPower(0); // Turn off the catapult
//        sleep(1000);

        wheels.moveWheels(0.5); // Move forward a little bit
        sleep(1250 );
        wheels.stopWheels();
        sleep(750);
        rotateWheelsWithGyro(170, 0.15); // Spin 180 degrees
        sleep(500);
        wheels.translateRight(0.5);
        sleep(3000);
        wheels.stopWheels();
        sleep(500);
        wheels.moveWheels(-0.15);

        wheelsImg = (VuforiaTrackableDefaultListener) beacons.get(0).getListener();

        while (opModeIsActive()) {
            if (wheelsImg.getRawPose() != null) {
                float x = wheelsImg.getRawPose().getData()[wheelsImg.getRawPose().getData().length - 4];
                if (x <= 50) {
                    telemetry.addLine("OMG IT WORKS!!! :D");
                    telemetry.update();
                    break;
                }
                telemetry.addLine("Wheel Pose: " + wheelsImg.getRawPose().formatAsTransform());
                telemetry.update();
            } else {
                telemetry.addLine("It's null");
                telemetry.update();
            }
        }
        wheels.stopWheels();

        while (opModeIsActive()) {
            telemetry.addLine("Red: " + colorSensor.red());
            telemetry.addLine("Green: " + colorSensor.green());
            telemetry.addLine("Blue: " + colorSensor.blue());
            telemetry.update();
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        params.vuforiaLicenseKey = "AU6WfDH/////AAAAGWjI+Tlqe0yFonQoCA8wjIQXeCr9sHJmsD9pGx9ZZXBJHx7poA6wSqkAVJueGJnExOdaRZPe93roQeWFthIEmaiIxiao76CK+ASBbIXn4NH4gmhOgGDkRO3TFsLRFaT9Ek+37RH8uQioH6qeqRIQr/p8ZbRUsfICHOoXyEm2wKCBDvYAx9P99KsLuXBC+vXojXF3s7jNDvQrbPnaG1JB/S45+vwkSXYsSyVbawKBATLUnyVnEs+MIXKRjn3Px1S0jf+3shxw6tTLVznsQguji1NaDB3W5oqpAIopEy6o0bOAdPhKAraWRIj0He5iddQbG9ntSUE3Xc577aTy22dNEFTaSHalE/wmCOyZdL7OzhGx";
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");
    }

    private void initRobot() {
        wheels.mapWheels(hardwareMap);
        wheels.setDirections();
        wheels.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        catapultArm = hardwareMap.dcMotor.get("catapultArm");
        sweeper = hardwareMap.dcMotor.get("sweeper");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    private void vuforiaStuff(VuforiaTrackables beacons) {
        for (VuforiaTrackable beac : beacons) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

            if (pose != null) {

                VectorF translation = pose.getTranslation();

                telemetry.addLine(beac.getName() + "-Translation: " + translation);

                double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                telemetry.addLine(beac.getName() + "-Degrees: " + degreesToTurn);
                telemetry.update();

                wheels.stopWheels();
            }
        }
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
        while (wheels.getWheels()[0].getCurrentPosition() < wheels.getWheels()[0].getTargetPosition()) {
            for (DcMotor wheel : wheels.getWheels()) {
                wheel.setPower(-power);

            }
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
            while (gyro.getHeading() <=  degrees || gyro.getHeading() >= 270) {
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