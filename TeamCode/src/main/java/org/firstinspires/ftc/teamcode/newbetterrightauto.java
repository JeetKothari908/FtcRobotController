package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="therightauto", group="Autonomous")
public class newbetterrightauto extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    public Servo grabber;

    int position = 180;
    double constanter = 0.0;
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public ColorSensor color_sensor;
    double moveconstant = 1783 * (2/2.05); //WORKS
    double motorrotation = 538; //WORKS
    double turnconstant = 11.3846625767; // per degree, so its rly small
    double strafeconstant = 1783* (1/0.84) * (1/1.08) * (1/0.95) * (2/2.05); //untested, need to test
    String color = "";

    @Override
    public void runOpMode() {



        fl = hardwareMap.get(DcMotor.class, "FL");
        fr = hardwareMap.get(DcMotor.class, "FR");
        bl = hardwareMap.get(DcMotor.class, "BL");
        br = hardwareMap.get(DcMotor.class, "BR");
        E = hardwareMap.get(DcMotor.class, "E");
        color_sensor = hardwareMap.colorSensor.get("color_sensor");

        grabber = hardwareMap.get(Servo.class,"grab"); //THE SERVO IS IN PEROCENT, BW/ 1 OR 0. BASELINE IS .5

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

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            closeclaw();
            sleep(25);
            straferight(17/12);
            sleep(100);
            // now robot is at cone measuring location
            color = colortestor();
            telemetry.addData("color is ", color);
            telemetry.update();
            straferight(1);
            straferight(0.4);
            strafeleft(0.4);
            // apt the robot is in the middle of the cone's square
            moveforward(2);
            straferight(1.145);
            //drop cone off
            sleep(300);
            moveExtender(3);
            sleep(300);
            moveforward(.385);
            sleep(300);
            openclaw();
            movebackward(0.3);
            moveExtender(0);
            straferight(1);
            turnleft(189);

            moveforward(4);
         //   straferight(1);
            //pick up new cone
            //move extender to new placement not measured yet will do later
            moveExtender(4);
            openclaw();
            moveforward(0.3);
            closeclaw();
            moveExtender(3);
            movebackward(0.3);
            moveExtender(0);
            turnleft(180);
            //should be back idk
            straferight(0.25);
            moveforward(4);
            strafeleft(1);
            //drop cone off
            moveExtender(3);
            moveforward(0.3);
            openclaw();
            movebackward(0.3);
            moveExtender(0);
            //strafeleft(1.18);
            // should be back at the place with the things
/*            sleep(400);
            if (color.equals("turqoise")) {
                movebackward(.6);
            } else if (color.equals("yellow")) {

            } else {

                moveforward(.6);
            }
            moveExtender(0);

            sleep(10000);*/
            break;
        }

    }
    // this is only for dc motors
    void settargetpositioner(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(.3);
    }

    void moveforward(double feet){

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (feet * 0.3048 * moveconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);

    }
    void movebackward(double feet){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (feet * 0.3048 * moveconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()||fr.isBusy() || bl.isBusy() || br.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);

    }
    void strafeleft(double feet){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (feet * 0.3048 * strafeconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()||fr.isBusy() || bl.isBusy() || br.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);


    }
    void straferight(double feet){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (feet * 0.3048 * strafeconstant)*-1;
        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()||fr.isBusy() || bl.isBusy() || br.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);


    }
    void turnleft(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        int position = (int) (degrees * turnconstant)*-1;
        settargetpositioner(fl, -position);
        settargetpositioner(bl, -position);
        settargetpositioner(br, position);
        settargetpositioner(fr, position);
        while (fl.isBusy()||fr.isBusy() || bl.isBusy() || br.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);

    }
    void turnright(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (degrees * turnconstant)*-1;
        settargetpositioner(fl, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, -position);
        settargetpositioner(fr, -position);
        while (fl.isBusy()||fr.isBusy() || bl.isBusy() || br.isBusy()){sleep(10);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(100);

    }
    String colortestor() {
        if (color_sensor.green() > color_sensor.blue() && color_sensor.red() > color_sensor.blue()) {
            return "yellow";
        }
        if (color_sensor.blue() > color_sensor.green() && color_sensor.red() > color_sensor.green()) {
            return "purple";
        }
        else {
            return "turqoise";
        }}

    void moveExtender(int place){
        if (place == 0){
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(0);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);

            sleep(1000);

        }
        if (place == 1){
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(997);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
            sleep(1000);

/*            while(E.isBusy()){sleep(10);}
            E.setPower(0);
            sleep(100);*/


        }
        if (place == 2){
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(1944);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
            sleep(1000);

            /*while(E.isBusy()){sleep(10);}
            E.setPower(0);
            sleep(100);*/


        }
        if (place == 3) {
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(2990);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
            sleep(1000);
        }
           /* while(E.isBusy()){sleep(10);}
            E.setPower(0);
            sleep(100);*/
        if (place == 4){

            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(525);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
            sleep(1000);
        }


        }

    void openclaw(){

        grabber.setPosition(.35);
        sleep(500);


    }
    void closeclaw(){
        grabber.setPosition(0.550);
        sleep(500);

    }

}