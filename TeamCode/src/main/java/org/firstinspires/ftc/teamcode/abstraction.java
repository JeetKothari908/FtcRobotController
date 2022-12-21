package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class abstraction {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    public double coneLocationFromOrigin;
    //TODO average all values and find values that are proven statistically different through chi^2, (bio reference)
    //TODO average location of continuous, statistically different values to get the middle of a pole and the distance to it.
    //TODO implement aiden's mathematics

    public ArrayList<Double> rawJiggleData;

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



    public void defineAndStart(){

        fl= hardwareMap.get(DcMotor.class, "FL");
        fr= hardwareMap.get(DcMotor.class, "FR");
        bl= hardwareMap.get(DcMotor.class, "BL");
        br= hardwareMap.get(DcMotor.class, "BR");

        distance_sensor = hardwareMap.get(DistanceSensor.class, "DS");

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


    public void jiggle(double deg) {
        blockDriver=true;
        move(0, 0, -deg, 0, 12.05, 1);

        // make sure arraylist exists & is empty
        rawJiggleData = new ArrayList<Double>();
        rawJiggleData.clear();

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
        while (fl.isBusy()){rawJiggleData.add(distance_sensor.getDistance(DistanceUnit.CM));}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);




        blockDriver=false;
    }
    public void jiggle_and_move(){
        abstraction robot = new abstraction(hardwareMap, gamepad1);

        robot.defineAndStart();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            robot.jiggle(25);
            try {


                double last_data = 0;
                double main_distance;
                for(Double d: robot.rawJiggleData) {
                    if (last_data == 0){last_data = d;}
                    double difference = Math.abs(d-last_data);
                    if (difference > 0.5*d){
                        main_distance = last_data;
                        robot.move(0, 0, -12.5, 0, 12.05, 1);
                        telemetry.addLine(""+main_distance);
                        float m_distance_prime = (float) Math.sqrt(main_distance*main_distance-(200-200*Math.cos(12.5)));
                        robot.move(0, m_distance_prime, 0, 0, 12.05, 1);
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


            } catch (IOException e) {
                telemetry.addLine("dies");
            }
            telemetry.addData("jiggles: ",robot.rawJiggleData);
            telemetry.update();


            sleep(10000);
        }
    }

}
