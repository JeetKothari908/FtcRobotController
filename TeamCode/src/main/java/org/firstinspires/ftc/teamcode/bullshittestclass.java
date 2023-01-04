//package org.firstinspires.ftc.teamcode;
//
//import static java.lang.Thread.sleep;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//import java.util.ArrayList;
//
//@Autonomous(name="for all tests", group="Autonomous")
//public class bullshittestclass extends LinearOpMode {
//
//    public Servo grabber;
//
//    int position = 180; // did this have a purpose?
//    public DcMotor fl;
//    public DcMotor fr;
//    public DcMotor bl;
//    public DcMotor br;
//    public DcMotor E;
//    public ColorSensor color_sensor;
//    double moveconstant = 1783 * (2/2.05); //WORKS
//    double motorrotation = 538; //WORKS // da f is this for?
//    double turnconstant = 12.05; // per degree, so its rly small
//    double strafeconstant = 1783* (1/0.84) * (1/1.08) * (1/0.95) * (2/2.05); //untested, need to test
//    public ArrayList<Double> rawJiggleData= new ArrayList<>();
//    public ArrayList<Integer> frontleft= new ArrayList<>();
//    public ArrayList<Integer> frontright= new ArrayList<>();
//    public ArrayList<Integer> backleft= new ArrayList<>();
//    public ArrayList<Integer> backright= new ArrayList<>();
//    public DistanceSensor distance_sensor;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        fl = hardwareMap.get(DcMotor.class, "FL");
//        fr = hardwareMap.get(DcMotor.class, "FR");
//        bl = hardwareMap.get(DcMotor.class, "BL");
//        br = hardwareMap.get(DcMotor.class, "BR");
//        E = hardwareMap.get(DcMotor.class, "E");
//        distance_sensor = hardwareMap.get(DistanceSensor.class, "DS");
//        color_sensor = hardwareMap.colorSensor.get("color_sensor");
//
//        grabber = hardwareMap.get(Servo.class,"grab"); // servos are implemented badly and I hate it with a burning passion
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        E.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        fl.setDirection(DcMotorSimple.Direction.REVERSE);
//        fr.setDirection(DcMotorSimple.Direction.FORWARD);
//        bl.setDirection(DcMotorSimple.Direction.REVERSE);
//        br.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        // runs the moment robot is initialized
//        waitForStart();
//        while(opModeIsActive()) {
////            telemetry.addData("fr", fr.getCurrentPosition());
////            telemetry.addData("br", br.getCurrentPosition());
////            telemetry.addData("fl", fl.getCurrentPosition());
////            telemetry.addData("bl", bl.getCurrentPosition());
//
//            scan();
//
//            sleep(100000);
//            break;
//        }
//    }
//    void scan() throws InterruptedException {
//
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        move(0,0,15,1);
//        while(fl.isBusy()||fr.isBusy()||bl.isBusy()||br.isBusy()){sleep(10);}
//
//        rawJiggleData.clear(); // make sure old data is gone
//        frontleft.clear();
//        frontright.clear();
//        backright.clear();
//        backleft.clear();
//
//
//        move(0,0,-30,1);
//
//        while(fl.isBusy()||fr.isBusy()||bl.isBusy()||br.isBusy()){
//
//            if(distance_sensor.getDistance(DistanceUnit.CM)<819.0){ //checks if the data returned is diff from DS null value of 819
//                rawJiggleData.add(distance_sensor.getDistance(DistanceUnit.CM)); // adds the distance to a list
//                telemetry.addLine("dist: "+rawJiggleData.get(rawJiggleData.size()-1)); // sends to phone
//                frontleft.add(fl.getCurrentPosition()); // jeets idea of storing all 4 motor positions
//                frontright.add(fr.getCurrentPosition());
//                backleft.add(bl.getCurrentPosition());
//                backright.add(br.getCurrentPosition());
//            }
//        }
//
//        double lowestDist = 819; // makes sure any and all data in the list is less than the starting value and will be in the list
//
//
//        double cfl= fl.getCurrentPosition();
//        double cfr= fr.getCurrentPosition();
//        double cbl= bl.getCurrentPosition();
//        double cbr= br.getCurrentPosition();
//        telemetry.addData("fr", fr.getCurrentPosition());
//        telemetry.addData("br", br.getCurrentPosition());
//        telemetry.addData("fl", fl.getCurrentPosition());
//        telemetry.addData("bl", bl.getCurrentPosition());
//
//        if(!rawJiggleData.isEmpty()){ // accounts for edge case where all values are 819.0
//            telemetry.addLine("captured "+rawJiggleData.size()+"points");
//            for(double d:rawJiggleData){ // finds lowest value in list
//                if(d<lowestDist){lowestDist=d;}
//            }
//            telemetry.addLine("lowest distance"+lowestDist);
//            settargetpositioner(fl, (int) (-frontleft.get(rawJiggleData.indexOf(lowestDist))-cfl)); // goes there
//            settargetpositioner(bl, (int) (-backleft.get(rawJiggleData.indexOf(lowestDist))-cbl));
//            settargetpositioner(br, (int) (backright.get(rawJiggleData.indexOf(lowestDist))-cbr));
//            settargetpositioner(fr, (int) (frontright.get(rawJiggleData.indexOf(lowestDist))-cfr));
//        }else{
//            telemetry.addLine("empty list"); // idk why broken, so i added this
//        }
//        if(lowestDist<819) // makes sure it doesnt crash into my wall again
//            move(0, (lowestDist/100) - .08, 0, 1); // move to be 8 cm from the pole
//
//        while(fl.isBusy()||fr.isBusy()||bl.isBusy()||br.isBusy()){}
//        // lets the motors do what they have to, shouldnt do anything if prev line is false
//        telemetry.addData("err fr", fr.getCurrentPosition()-cfr);
//        telemetry.addData("err br", br.getCurrentPosition()-cbr);
//        telemetry.addData("err fl", fl.getCurrentPosition()-cfl);
//        telemetry.addData("err bl", bl.getCurrentPosition()-cbl);
//
//        fl.setPower(0); // cleaning up after the scan is done
//        fr.setPower(0);
//        bl.setPower(0);
//        br.setPower(0);
//        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // so the driver can actually drive again
//        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        telemetry.addLine("done");
//        telemetry.update();
//    }
//
//    void move(double X, double Y, double T, double P) {
//
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.FORWARD);
//
//        fl.setTargetPosition((int) ((-Y*moveconstant) + (X*strafeconstant)  + (turnconstant * T)));
//        fr.setTargetPosition((int) ((-Y*moveconstant) + (-X*strafeconstant) + (turnconstant * -T)));
//        bl.setTargetPosition((int) ((-Y*moveconstant) + (-X*strafeconstant) + (turnconstant * T)));
//        br.setTargetPosition((int) ((-Y*moveconstant) + (X*strafeconstant)  + (turnconstant * -T)));
//
//        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        fl.setPower(P);
//        fr.setPower(P);
//        bl.setPower(P);
//        br.setPower(P);
//    }
//
//    void settargetpositioner(DcMotor motor, int position){
//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motor.setTargetPosition(position);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setPower(.30);
//    }
//}