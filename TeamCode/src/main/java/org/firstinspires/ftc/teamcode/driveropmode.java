package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

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
    public void runOpMode() throws InterruptedException {

        abstraction robot = new abstraction(hardwareMap, gamepad1);

        robot.defineAndStart();
        robot.telemetry=telemetry;
        waitForStart();
        runtime.reset();

        boolean blockDriver=false;

        while (opModeIsActive()) {
            telemetry.addData("distance",robot.distance_sensor.getDistance(DistanceUnit.CM));
            telemetry.addData("fr", robot.fr.getCurrentPosition());
            telemetry.addData("br", robot.br.getCurrentPosition());
            telemetry.addData("fl", robot.fl.getCurrentPosition());
            telemetry.addData("bl", robot.bl.getCurrentPosition());

            robot.move();
            if(gamepad1.right_trigger > 0.5){robot.grabber.setPosition(0.550);
            }
            if(gamepad1.left_trigger > 0.5){robot.grabber.setPosition(0.295);
            }
            if(gamepad1.b){robot.extend(0);}
            if(gamepad1.a){robot.extend(1);}
            if(gamepad1.x){robot.extend(2);}
            if(gamepad1.y){robot.extend(3);}
            if(gamepad1.right_bumper){
                double d = robot.distance_sensor.getDistance(DistanceUnit.CM);
                if(d<55) {
                    robot.move(-.05, (d - 15) / 100, 0, .5);
                        while (robot.fl.isBusy() || robot.fr.isBusy() || robot.bl.isBusy() || robot.br.isBusy()) {sleep(1);}
//                    robot.move(-.1, .05, 0, .5);
//                    while (robot.fl.isBusy() || robot.fr.isBusy() || robot.bl.isBusy() || robot.br.isBusy()) {sleep(10);}

                    robot.fl.setPower(0); // cleaning up after the scan is done
                    robot.fr.setPower(0);
                    robot.bl.setPower(0);
                    robot.br.setPower(0);

                    robot.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // so the driver can actually drive again
                    robot.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
        }
    }
}