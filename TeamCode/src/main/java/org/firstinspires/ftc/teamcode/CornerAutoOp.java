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

        if (!opModeIsActive()) { return; }
        wheels.move(0.5);
        sleep(1750);


        wheels.move(0);
        sleep(1000);
        wheels.move(0.2, Vector4.ROTATION);
        while (gyro.getHeading() > 285 || gyro.getHeading() < 100) {
            idle();
        }
        wheels.move(0);
        sleep(500);
        catapult.setPower(0.3);
        sleep(2000);
        catapult.setPower(0);
        sleep(500);
        gate.toggle();
        sleep(2000);
        gate.toggle();
        catapult.setPower(0.3);
        sleep(2000);
        catapult.setPower(0);
        sleep(1000);
        wheels.move(0.5, Vector4.LEFT);
        sleep(1500);
        wheels.move(0);

    }

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