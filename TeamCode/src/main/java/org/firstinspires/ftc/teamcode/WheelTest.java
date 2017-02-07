
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Arm;
import org.firstinspires.ftc.teamcode.util.Wheels;

/*
NOTE TO READER:
This OP Mode is a draft and contents may differ slightly from current software.
 */

@TeleOp(name = "WheelTest", group = "Iterative Opmode")
//@Disabled
public class WheelTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private double rightPower = 0;
    private double leftPower = 0;

    private double powerModifier = 1; // Used to change the maximum power while running
    private double[] powerModifierArray = {
            1,
            0.75,
            0.5,
            0.25
    };
    // When "start" is pressed on the game pad, the value of "powerModifier" goes to the next value
    private int powerModifierIndex = 0;
    private boolean isStartDown = true;
    private boolean canShoot = true;
    private double sweeperPower = 0;
    private double targetSweeperPower = 0;

    private Wheels wheels = new Wheels(null, null, null, null);
    //private Arm arm = new Arm(null, null);

    // Runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        wheels.mapWheels(hardwareMap); // Tells the program which motor is which
        //arm.mapHardware(hardwareMap);
        // Tells the program that the wheels should spin in the same direction
        wheels.setDirections();
        //arm.initArm(); // Sets runmodes of arm and zeros encoder position
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
        // Update what the phone displays on the screen
        updateTelemetry();

        // Press start to change the max velocity
        changeMaxVelocity();

        // Controls the wheels
        handleAcceleration();
        // Left stick moves the two left wheels and turns right
        wheels.moveWheels(leftPower, "left");
        // Right stick moves the two right wheels and turns left
        wheels.moveWheels(rightPower, "right");
        if (gamepad1.dpad_left){ wheels.translate(1, "left");}
        else if (gamepad1.dpad_right) {wheels.translate(1, "right");}
        // Rotates the arm 1 revolution when RT pressed
        /*if (arm.revComplete() && (gamepad2.right_trigger > 0.3) && canShoot) {
            arm.rotateArm();
            canShoot = false;
        } else if (!gamepad2.start && !canShoot) {
            canShoot = true;
        }*/
        // Determines state of sweeper (forward, reverse, stop)
        //controlSweeper();
    }

    private void handleAcceleration() {
    /* Instead of going from no power to max power instantly, it accelerates.
       To control how much it accelerates, change MAX_ACCELERATION at the top */
        // Acceleration handling for right wheels
        double MAX_ACCELERATION = 0.05;
        // If they move the thumb stick up more than the current power...
        if (gamepad1.right_stick_y * powerModifier > rightPower) {
            // Increase the power steadily instead of instantly going to the thumb stick position.
            rightPower += MAX_ACCELERATION;
            if (rightPower > gamepad1.right_stick_y * powerModifier) { // In case it adds too much
                // Set it exactly to the right stick position
                rightPower = gamepad1.right_stick_y * powerModifier;
            }
        }
        // Same as above except if the thumb stick is moved down more than the current power
        else if (gamepad1.right_stick_y * powerModifier < rightPower) {
            rightPower -= MAX_ACCELERATION;
            if (rightPower < gamepad1.right_stick_y * powerModifier) {
                rightPower = gamepad1.right_stick_y * powerModifier;
            }
        }
        // Same code as above except for the left wheels
        else if (gamepad1.left_stick_y * powerModifier > leftPower) {
            leftPower += MAX_ACCELERATION;
            if (leftPower > gamepad1.left_stick_y * powerModifier) {
                leftPower = gamepad1.left_stick_y * powerModifier;
            }
        }
        else if (gamepad1.left_stick_y * powerModifier < leftPower) {
            leftPower -= MAX_ACCELERATION;
            if (leftPower < gamepad1.left_stick_y * powerModifier) {
                leftPower = gamepad1.left_stick_y * powerModifier;
            }
        }
        // Move entire robot forward
        else if (gamepad1.dpad_up) {
            rightPower += MAX_ACCELERATION;
            leftPower += MAX_ACCELERATION; // Increase the power steadily.
            if (rightPower < powerModifier || leftPower < powerModifier) {
                // In case it adds too much, set it exactly to the right stick position
                rightPower = -powerModifier;
                leftPower = -powerModifier;
            }
        }
        // Move entire robot reverse
        else if (gamepad1.dpad_down) {
            rightPower -= MAX_ACCELERATION;
            leftPower -= MAX_ACCELERATION;
            if (rightPower < powerModifier || leftPower < powerModifier) {
                rightPower = powerModifier;
                leftPower = powerModifier;
            }
        }


    }

    // Press "start" to change the max velocity of the robot
    private void changeMaxVelocity() {
        if (gamepad1.start && isStartDown) {
            isStartDown = false;
            if (powerModifierIndex + 1 < powerModifierArray.length) {
                powerModifierIndex += 1;
            } else {
                powerModifierIndex = 0;
            }
            powerModifier = powerModifierArray[powerModifierIndex];
        } else if (!gamepad1.start) {
            isStartDown = true;
        }
    }
    // Controls the sweeper


    /*private void controlSweeper() {
        //toggle between forward and reverse if A is pressed
        if (gamepad2.a && targetSweeperPower != 1) {
            targetSweeperPower = 1;
        }
        // Stop sweeper id B is pressed
        else if (gamepad2.b)
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

        arm.sweepPower(sweeperPower);
    }*/
    // Update what the phone displays on the screen
    private void updateTelemetry() {
        String telemetryMessage = "Status: Running for " + runtime.toString();
        telemetryMessage += "\n----------";
        telemetryMessage += "\nVelocity Modifier: " + String.valueOf(powerModifier * 100) + "%";
        telemetryMessage += "\nRight Wheels Power: " + String.valueOf(rightPower);
        telemetryMessage += "\nLeft Wheels Power: " + String.valueOf(leftPower);
        telemetryMessage += "\n----------";
        telemetryMessage += "\nSweeper Power: " + sweeperPower;
       // telemetryMessage += "\nArm Position: " + Integer.toString(arm.armPosition());
        telemetry.addLine(telemetryMessage);
    }
}
