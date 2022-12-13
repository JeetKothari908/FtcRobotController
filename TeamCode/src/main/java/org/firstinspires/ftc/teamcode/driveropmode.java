package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="driving", group="Driver OP")
public class driveropmode extends LinearOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    public Servo grabber;

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public ColorSensor color_sensor;

    boolean holdpos = false;

    @Override
    public void runOpMode() {

        fl= hardwareMap.get(DcMotor.class, "FL");
        fr= hardwareMap.get(DcMotor.class, "FR");
        bl= hardwareMap.get(DcMotor.class, "BL");
        br= hardwareMap.get(DcMotor.class, "BR");

        E = hardwareMap.get(DcMotor.class, "E");

        grabber = hardwareMap.get(Servo.class, "grab");

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        E.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();







        while (opModeIsActive()) {

            move();

            if(gamepad1.right_trigger>0.5){ grabber.setPosition(.295);
            }
            if(gamepad1.left_trigger > 0.5){grabber.setPosition(0);
            }

            if(gamepad1.b){extend(0);holdpos=true;}
            if(gamepad1.a){extend(1);holdpos=true;}
            if(gamepad1.x){extend(2);holdpos=true;}
            if(gamepad1.y){extend(  3);holdpos=true;}
            if(gamepad1.right_bumper){
                E.setTargetPosition(500);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(1);
            }

            if(!(gamepad1.a|| gamepad1.b|| gamepad1.x||gamepad1.y)){}else

            {holdpos=false; E.setPower(0);E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
            telemetry.addData("fl",fl.getPower());
            telemetry.addData("fr",fr.getPower());
            telemetry.addData("bl",bl.getPower());
            telemetry.addData("br",br.getPower());
            telemetry.addData("e", E.getCurrentPosition());
            telemetry.update();
        }
    }










   // void grab(){
    //}
    //void ungrab(){
    //}

    void extend(int position) {

        if(holdpos){E.setPower(1);} else{
            switch (position) {
                case 0:
                    E.setTargetPosition(0);
                    E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    E.setPower(1);

                    break;
                case 1:
                    E.setTargetPosition(997);
                    E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    E.setPower(1);

                    break;
                case 2:
                    E.setTargetPosition(2048);
                    E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    E.setPower(1);

                    break;
                case 3:
                    E.setTargetPosition(2990);
                    E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    E.setPower(1);


                    break;

            }}
    }

    void move(){
        double horizontal = -gamepad1.left_stick_x*.5;   // this works so dont question it
        double vertical = -gamepad1.left_stick_y*.5;
        double turn = -gamepad1.right_stick_x*2/3;
      //  E.setPower(gamepad1.left_stick_y);
        fl.setPower(Range.clip((vertical + horizontal + turn), -1, 1));
        fr.setPower(Range.clip((vertical - horizontal - turn), -1, 1));
        bl.setPower(Range.clip((vertical - horizontal + turn), -1, 1));
        br.setPower(Range.clip((vertical + horizontal - turn), -1, 1));
    }

    void move(double X, double Y, double T, double U, double TU, double P){
        // make sure to set motor mode to RUN_TO_POSITION and give it power!

        fl.setTargetPosition(fl.getCurrentPosition() + (int) (U * (Y + X)));//
        fr.setTargetPosition(fl.getCurrentPosition() + (int) (U * (Y - X)));//
        bl.setTargetPosition(fl.getCurrentPosition() + (int) (U * (Y - X)));//
        br.setTargetPosition(fl.getCurrentPosition() + (int) (U * (Y + X)));//

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fl.setPower(P);
        fr.setPower(P);
        bl.setPower(P);
        br.setPower(P);

        fl.setTargetPosition(fl.getCurrentPosition() + (int) (TU * T));
        fr.setTargetPosition(fl.getCurrentPosition() + (int) (TU * -T));
        bl.setTargetPosition(fl.getCurrentPosition() + (int) (TU * T));
        br.setTargetPosition(fl.getCurrentPosition() + (int) (TU * -T));

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
}