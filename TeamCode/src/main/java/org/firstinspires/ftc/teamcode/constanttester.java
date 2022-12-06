package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name = "autonomous blue", group = "Autonomous")
public class constanttester extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        waitForStart();
        DcMotor fl;
        fl = hardwareMap.get(DcMotor.class, "FL");
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setDirection(DcMotor.Direction.REVERSE);
        fl.setTargetPosition(1000);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(0.5);
        while(opModeIsActive()){}
    }
}