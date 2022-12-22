package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="THE DRIVER OP", group="Driver OP")
public class driveropmode extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        abstraction robot = new abstraction(hardwareMap, gamepad1);

        robot.defineAndStart();
        robot.telemetry=telemetry;
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addLine(""+robot.distance_sensor.getDistance(DistanceUnit.CM));

            robot.move();
            if(gamepad1.right_trigger > 0.5){robot.grabber.setPosition(0.595);
            }
            if(gamepad1.left_trigger > 0.5){robot.grabber.setPosition(0.295);
            }
            if(gamepad1.b){robot.extend(0);}
            if(gamepad1.a){robot.extend(1);}
            if(gamepad1.x){robot.extend(2);}
            if(gamepad1.y){robot.extend(3);}
            if(gamepad1.right_bumper){robot.jiggle_and_move(25);}
            telemetry.update();
        }
    }
}