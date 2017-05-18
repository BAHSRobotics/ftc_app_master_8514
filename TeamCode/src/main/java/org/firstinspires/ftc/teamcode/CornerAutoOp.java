package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

@Autonomous(name="CornerAutoOp", group="Linear Opmode")
public class CornerAutoOp extends LinearOpMode {

    private Wheels wheels;
    private Gate gate;
    private DcMotor catapult;
    private GyroSensor gyro;

    public void runOpMode() {
        wheels = new Wheels(hardwareMap);
        gate = new Gate(hardwareMap, "gate");
        catapult = hardwareMap.dcMotor.get("catapultArm");
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();

        waitForStart();

        // Make the robot move forward a little bit
        if (!opModeIsActive()) { return; }
        wheels.move(0.5);
        sleep(1750);

        // Stop moving
        if (!opModeIsActive()) { return; }
        wheels.move(0);
        sleep(1000);

        // Rotate so it can get in position to shoot balls
        wheels.move(0.2, Vector4.ROTATION);
        while ((gyro.getHeading() > 285 || gyro.getHeading() < 50) && opModeIsActive()) { // "|| gyro.getHeading() < 50" is so the gyro is starts rotating
                                                                                          // if the gyro reads 1 degree.
            idle();
        }

        // Stop moving
        if (!opModeIsActive()) { return; }
        wheels.move(0);
        sleep(500);

        // Turn on the catapult
        if (!opModeIsActive()) { return; }
        catapult.setPower(0.3);
        sleep(2000);

        // Turn off the catapult
        if (!opModeIsActive()) { return; }
        catapult.setPower(0);
        sleep(500);

        // Open gate and wait for ball to fall into place
        if (!opModeIsActive()) { return; }
        gate.toggle();
        sleep(2000);

        // Close gate and turn on the catapult
        if (!opModeIsActive()) { return; }
        gate.toggle();
        catapult.setPower(0.3);
        sleep(2000);

        // Stop catapult
        if (!opModeIsActive()) { return; }
        catapult.setPower(0);
        sleep(1000);

        // Move robot to the left
        if (!opModeIsActive()) { return; }
        wheels.move(-1, Vector4.RIGHT);
        sleep(1500);

        // Stop
        wheels.move(0);
    }

    // Override so the driver can know when the gyro is done calibrating.
    @Override
    public synchronized void waitForStart() {
        while (!isStarted()) {
            synchronized (this) {
                try {
                    this.wait();
                    if (gyro.isCalibrating()) {
                        telemetry.addLine("Gyro is calibrating. DON'T PRESS PLAY!!!");
                    } else {
                        telemetry.addLine("Gyro is done calibrating. You can press play now.");
                    }
                    telemetry.update();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}