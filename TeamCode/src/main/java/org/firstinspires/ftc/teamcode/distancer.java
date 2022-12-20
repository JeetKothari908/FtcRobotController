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
import java.util.ArrayList;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="distancetester", group="Driver OP")
public class distancer extends LinearOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    public DistanceSensor distancer;
    double total = 0;
    ArrayList<Double> averager = new ArrayList<Double>();

    double average = total / averager.size();

    @Override
    public void runOpMode() {
            distancer = hardwareMap.get(DistanceSensor.class, "distance_sensor");
        while (opModeIsActive()){

            averager.add(distancer.getDistance(DistanceUnit.METER));
            for (int i = 0; i >= averager.size(); i++){
                total = total + i;
            }
            telemetry.addData("average", average);
            telemetry.addData("distance",distancer.getDistance(DistanceUnit.METER));
            telemetry.update();

        }

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();







        while (opModeIsActive()) {

        }
    }




}