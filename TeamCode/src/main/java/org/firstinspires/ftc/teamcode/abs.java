package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.ArrayList;

public class abs {


    public ArrayList<Double> rawJiggleData= new ArrayList<>();
    public ArrayList<Integer> frontleft= new ArrayList<>();
    public ArrayList<Integer> frontright= new ArrayList<>();
    public ArrayList<Integer> backleft= new ArrayList<>();
    public ArrayList<Integer> backright= new ArrayList<>();

    public Servo grabber;

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public DistanceSensor distance_sensor;
    double moveconstant = 1739.51219512;
    double motorrotation = 538;
    double turnconstant = 12.05; // per degree, so its rly small
    double strafeconstant = 2018.37022547; //untested, need to test
    public HardwareMap hardwareMap;
    public Gamepad gamepad1;

    public Telemetry telemetry;

    public abs(HardwareMap hard, Gamepad g){
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
                E.setTargetPosition(1300);
                E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                E.setPower(1);

                break;
            case 2:
                E.setTargetPosition(2200);
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

    void move(double X, double Y, double T, double P) {
        fl.setDirection(DcMotor.Direction.REVERSE); // jeet messes with these so i have to set them back here
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setTargetPosition(fl.getCurrentPosition()+(int) ((-Y*moveconstant) + (X*strafeconstant)  + (turnconstant * T)));
        fr.setTargetPosition(fr.getCurrentPosition()+(int) ((-Y*moveconstant) + (-X*strafeconstant) + (turnconstant * -T)));
        bl.setTargetPosition(bl.getCurrentPosition()+(int) ((-Y*moveconstant) + (-X*strafeconstant) + (turnconstant * T)));
        br.setTargetPosition(br.getCurrentPosition()+(int) ((-Y*moveconstant) + (X*strafeconstant)  + (turnconstant * -T)));

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fl.setPower(P);
        fr.setPower(P);
        bl.setPower(P);
        br.setPower(P);

    }

    void scan() throws InterruptedException {

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        move(0,0,15,1);
        while(fl.isBusy()||fr.isBusy()||bl.isBusy()||br.isBusy()){sleep(10);}

        rawJiggleData.clear(); // make sure old data is gone
        frontleft.clear();
        frontright.clear();
        backright.clear();
        backleft.clear();


        move(0,0,-30,1);

        while(fl.isBusy()||fr.isBusy()||bl.isBusy()||br.isBusy()){

            if(distance_sensor.getDistance(DistanceUnit.CM)!=819.0){ //checks if the data returned is diff from DS null value of 819
                rawJiggleData.add(distance_sensor.getDistance(DistanceUnit.CM)); // adds the distance to a list
                telemetry.addLine("dist: "+distance_sensor.getDistance(DistanceUnit.CM)); // sends to phone
                frontleft.add(fl.getCurrentPosition()); // jeets idea of storing all 4 motor positions
                frontright.add(fr.getCurrentPosition());
                backleft.add(bl.getCurrentPosition());
                backright.add(br.getCurrentPosition());
            }
        }

        double lowestDist = 819; // makes sure any and all data in the list is less than the starting value and will be in the list

        if(!rawJiggleData.isEmpty()){ // accounts for edge case where all values are 819.0

            for(double d:rawJiggleData){ // finds lowest value in list
                if(d<lowestDist){lowestDist=d;}
            }
            telemetry.addLine(""+lowestDist);
            settargetpositioner(fl, -frontleft.get(rawJiggleData.indexOf(lowestDist))); // goes there
            settargetpositioner(bl, -backleft.get(rawJiggleData.indexOf(lowestDist)));
            settargetpositioner(br, backright.get(rawJiggleData.indexOf(lowestDist)));
            settargetpositioner(fr, frontright.get(rawJiggleData.indexOf(lowestDist)));
        }
        if(lowestDist!=819) // makes sure it doesnt crash into my wall again
            move(0, (lowestDist/100) - .08, 0, 1); // move to be 8 cm from the pole

        while (fl.isBusy()){} // lets the motors do what they have to, shouldnt do anything if prev line is false

        fl.setPower(0); // cleaning up after the scan is done
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // so the driver can actually drive again
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addLine("done");
        telemetry.update();
    }

    void settargetpositioner(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(.30);
    }
}