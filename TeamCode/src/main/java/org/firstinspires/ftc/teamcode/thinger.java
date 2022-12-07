package org.firstinspires.ftc.teamcode;
//
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

//
//
@Autonomous(name = "autonomous red", group = "Autonomous")
public class thinger extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
//
    public DcMotor thing;
    @Override
    public void runOpMode() {
        waitForStart();
        thing= hardwareMap.get(DcMotor.class, "FL");
        thing.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        thing.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        thing.setDirection(DcMotorSimple.Direction.FORWARD);
        thing.setTargetPosition(1000);
        thing.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        thing.setPower(0.5);
        while (opModeIsActive()){}
//        r=new RobotHardware(hardwareMap);
//        r.encoderDrive(0,-55,0,1,r.CM);
//        r.encoderDrive(60,0,0,1,r.CM);
//
//        r.arm.setTargetPosition(360);
//        r.arm.setPower(1);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        r.armEnd.setPosition(75);
//
//        r.encoderDrive(0,0,180,1,r.CM);
//
//        r.encoderDrive(0,-55,0,1,r.CM);
//        r.encoderDrive(-110,0,0,1,r.CM);
//
////        r.carousel.setPower(1);
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();  dasdfsa
////        }
////        r.carousel.setPower(0);
//
//        r.encoderDrive(-15,55,0,1,r.CM);
//
   }
}