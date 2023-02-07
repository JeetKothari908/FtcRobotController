package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

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

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@TeleOp(name="tester", group="Driver OP")
public class thing extends LinearOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    public Servo grabber;
    //  public Telemetry telemetry;
    double powersetterr = 1;

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public ColorSensor color_sensor;

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
        defineAndStart();
        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();







        while (opModeIsActive()) {

            move1();
            if (gamepad1.dpad_left){
                if (powersetterr == 0.5){
                    powersetterr = 1.0;
                }
                if (powersetterr == 1.0){
                    powersetterr = 0.5;
                }
            }
/*
            if(gamepad1.dpad_left){
                if (powersetter > 0.5){
                    powersetter = 0.5;
                }
                else{
                    powersetter = 1;
                }
            }*/
            if(gamepad1.right_trigger > 0.5){ grabber.setPosition(.295);
            }
            if(gamepad1.left_trigger > 0.5){grabber.setPosition(0);}
            if(gamepad1.right_bumper){ jiggle_v2();}
            if(gamepad1.left_trigger > 0.5){go = false;}

            telemetry.addData("fl",fl.getPower());
            telemetry.addData("fr",fr.getPower());
            telemetry.addData("bl",bl.getPower());
            telemetry.addData("e",E.getCurrentPosition());
            telemetry.addData("grab", grabber.getPosition());
            telemetry.update();
        }

    }










    // void grab(){
    //}
    //void ungrab(){
    //}

    void extend(int position) {

        switch (position) {
            case 0:
                if(E.getCurrentPosition()>10) {
                    E.setTargetPosition(0);
                    E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    E.setPower(0.75);
                }else{
                    E.setPower(0);
                }
                break;
            case 1:
                E.setTargetPosition(1300);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(0.75);

                break;
            case 2:
                E.setTargetPosition(1994);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(0.75);

                break;
            case 3:
                E.setTargetPosition(2990);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(0.75);


                break;
        }
    }


    void move1(){
        double horizontal = -gamepad1.left_stick_x*.5;   // this works so dont question it
        double vertical = gamepad1.left_stick_y*.5;
        double turn = -gamepad1.right_stick_x*2/3;
        //  E.setPower(gamepad1.left_stick_y);
        fl.setPower((Range.clip((vertical + horizontal + turn), -1, 1))*powersetterr);
        fr.setPower((Range.clip((vertical - horizontal - turn), -1, 1))*powersetterr);
        bl.setPower((Range.clip((vertical - horizontal + turn), -1, 1))*powersetterr);
        br.setPower((Range.clip((vertical + horizontal - turn), -1, 1))*powersetterr);
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

    public opencvpipelines detector;

    public void defineAndStart(){

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        telemetry.addData("webcam defined", true);

        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        telemetry.addData("camera defined", true);

        detector = new opencvpipelines();
        camera.setPipeline(detector);
        telemetry.addData("pipeline defined", true);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                telemetry.addData("opened", true);
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                telemetry.addData("starting stream", true);
            }
            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("error on stream", true);
            }
        });


    }




    void move(){
        double horizontal = -gamepad1.left_stick_x*.5;   // this works so dont question it
        double vertical = gamepad1.left_stick_y*.5;
        double turn = -gamepad1.right_stick_x*2/3;
        //  E.setPower(gamepad1.left_stick_y);
        fl.setPower(Range.clip((vertical + horizontal + turn), -1, 1));
        fr.setPower(Range.clip((vertical - horizontal - turn), -1, 1));
        bl.setPower(Range.clip((vertical - horizontal + turn), -1, 1));
        br.setPower(Range.clip((vertical + horizontal - turn), -1, 1));
    }



    void settargetposition(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);
    }


    public double mse( ArrayList<Double> rgb){
        double[] yellow = {255,255,0};
        double sum = 0;
        for (int i = 0; i <rgb.size(); i++) sum += Math.pow(yellow[i] - rgb.get(i), 2);

        return sum/3;

    }
    public boolean centered = false;
    public boolean forward = false;
    public boolean go = true;
    public void jiggle_v2(){
        while (!centered && go){
            boolean l1 = center(detector.get_pixels());
            if (!centered) {
                if (l1) {
                    move(0, 0, 1, 0, 12.05, 1);
                }
                else {
                    move(0, 0, -1, 0, 12.05, 1);
                }
            }
        }
        while (!forward && go){
            boolean l1 = f(detector.get_pixels());
            if (!forward) {
                if (l1) {
                    move(0, 2, 0, 0, 12.05, 1);
                }

            }
        }

    }
    public boolean center( ArrayList<ArrayList<ArrayList<Double>>>  array_of_pixels){
        int line_num  = (int)array_of_pixels.size()/3;
        ArrayList<ArrayList<Double>>  line_of_pixels = array_of_pixels.get(line_num);
        int yellow_counter = 0;
        int yellows = 0;
        boolean prev_data = false;
        int highest_num_yellow = 0;
        for (int i = 0; i <line_of_pixels.size(); i++){
            double j = mse(line_of_pixels.get(i));
            if (j <= 500){
                yellow_counter += 1;
                prev_data = true;
            }
            else if(prev_data && highest_num_yellow <= yellow_counter){
                highest_num_yellow = yellow_counter;
                yellows = (int) (i - yellow_counter)/2;
            }
        }
        if (yellows > line_of_pixels.size()/2){
            return true;
        }
        else if (yellows ==line_of_pixels.size()/2) {
            centered = true;
        }

        return false;



    }
    public boolean f( ArrayList<ArrayList<ArrayList<Double>>> array_of_pixels){
        int line_num  = (int)array_of_pixels.size()/3;
        ArrayList<ArrayList<Double>>  line_of_pixels = array_of_pixels.get(line_num);
        int yellow_counter = 0;
        int yellows = 0;
        boolean prev_data = false;
        int highest_num_yellow = 0;
        for (int i = 0; i <line_of_pixels.size(); i++){
            double j = mse(line_of_pixels.get(i));
            if (j <= 30){
                yellow_counter += 1;
                prev_data = true;
            }
            else if(prev_data && highest_num_yellow <= yellow_counter){
                highest_num_yellow = yellow_counter;
                yellows = (int) (i - yellow_counter)/2;
            }
        }
        if (yellow_counter == line_of_pixels.size()){
            forward = true;
            return true;

        }
        return false;



    }

}
