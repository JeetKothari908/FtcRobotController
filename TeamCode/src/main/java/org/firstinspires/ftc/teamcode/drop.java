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
public class drop extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        abs robot = new abs(hardwareMap, gamepad1);
        robot.defineAndStart();
        robot.telemetry = telemetry;
        waitForStart();

        robot.E.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("fr", robot.fr.getCurrentPosition());
            telemetry.addData("br", robot.br.getCurrentPosition());
            telemetry.addData("fl", robot.fl.getCurrentPosition());
            telemetry.addData("bl", robot.bl.getCurrentPosition());

            robot.move(); // this is to ensure that the joystick in idle wont slow the scan


            if (gamepad1.right_trigger > 0.5) {
                robot.grabber.setPosition(0.550);
            }
            if (gamepad1.left_trigger > 0.5) {
                robot.grabber.setPosition(.31);
            }
            if (gamepad1.b) {
                robot.extend(0);
            }
            if (gamepad1.a) {
                robot.extend(1);
            }
            if (gamepad1.x) {
                robot.extend(2);
            }
            if (gamepad1.y) {
                robot.extend(3);
            }
        }
    }
}