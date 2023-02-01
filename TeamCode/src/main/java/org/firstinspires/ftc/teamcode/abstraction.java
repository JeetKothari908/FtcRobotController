package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class abstraction {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    public double coneLocationFromOrigin;



    public Servo grabber;

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public ColorSensor color_sensor;
    public DistanceSensor distance_sensor;

    public HardwareMap hardwareMap;
    public Gamepad gamepad1;

    public boolean blockDriver=false; // bad if set to true under circumstances beyond nominal operation

    public abstraction(HardwareMap hard, Gamepad g){
        hardwareMap=hard;
        gamepad1=g;
    }

    public opencvpipelines detector;

    public void defineAndStart(){

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);

        detector = new opencvpipelines();
        camera.setPipeline(detector);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });


    }

    void extend(int position) {

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
                E.setTargetPosition(1994);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(1);

                break;
            case 3:
                E.setTargetPosition(2990);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(1);


                break;
        }
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

    void move(double X, double Y, double T, double U, double TU, double P) {
        // make sure to set motor mode to RUN_TO_POSITION and give it power!
        //x += X
        //y += Y
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

    void settargetposition(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);
    }


    public void jiggle_and_move(double deg){
        // abstraction robot = new abstraction(hardwareMap, gamepad1);

        blockDriver=true;
        move(0, 0, -deg, 0, 12.05, 1);

        // make sure arraylist exists & is empty


        // and then we jiggle;

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        int position = (int) (2*deg * 12.05)*-1; // jeet wtf is this code?
        settargetposition(fl, -position); // and then you negate the negative? WTF you're wasting clock cycles!
        settargetposition(bl, -position);
        settargetposition(br, position);
        settargetposition(fr, position);
        double last_data = 0;
        double main_distance;
        while (fl.isBusy()){
            double d = distance_sensor.getDistance(DistanceUnit.CM);
            if (last_data == 0){last_data = d;}
            double difference = Math.abs(d-last_data);
            if (difference > 0.5*d){
                fl.setPower(0);
                fr.setPower(0);
                bl.setPower(0);
                br.setPower(0);

                main_distance = last_data;
                move(0, 0, 0.5*deg, 0, 12.05, 1);
                //       telemetry.addLine(""+main_distance);
                float m_distance_prime = (float) Math.sqrt(main_distance*main_distance-(200-200*Math.cos(12.5)));
                move(0, m_distance_prime, 0, 0, 12.05, 1);
                // then pick up or drop cone


                //this is the distance of the pole/cone when outside of the sensors fov
                //then rotate half of fov radius- not just the sensor
                // if the whole robot moves
                // then move forward sqrt((main_distance)^2-(r2+r2﹣2r^2cosγ)) where y is 0.5 of FOV radius, and r is the distance of the sensor to its center of rotation
                // -- this value can be a constant we trial and error
                //
                //
            }
            last_data = d;



        }
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);



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