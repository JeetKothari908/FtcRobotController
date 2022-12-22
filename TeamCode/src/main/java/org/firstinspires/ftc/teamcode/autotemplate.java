package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="nameinphone", group="Autonomous")
public class autotemplate extends LinearOpMode {

    // Declare OpMode members.
//    private final ElapsedTime runtime = new ElapsedTime();



    //               )\         O_._._._A_._._._O         /(
    //                \`--.___,'=================`.___,--'/
    //                 \`--._.__                 __._,--'/
    //                   \  ,. l`~~~~~~~~~~~~~~~'l ,.  /
    //       __            \||(_)!_!_!_.-._!_!_!(_)||/            __
    //       \\`-.__        ||_|____!!_|;|_!!____|_||        __,-'//
    //        \\    `==---='// Declare OpMode members.`=---=='    //
    /*    /**/private final ElapsedTime runtime = new ElapsedTime();/**/
    //         \  ,.`~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~',.  /
    //           \||  ____,-------._,-------._,-------.____  ||/
    //            ||\|___!`======="!`======="!`======="!___|/||
    //            || |---||--------||-| | |-!!--------||---| ||
    //  __O_____O_ll_lO_____O_____O|| |'|'| ||O_____O_____Ol_ll_O_____O__
    //  o H o o H o o H o o H o o |-----------| o o H o o H o o H o o H o
    // ___H_____H_____H_____H____O =========== O____H_____H_____H_____H___
    //                          /|=============|\
    //()______()______()______() '==== +-+ ====' ()______()______()______()
    //||{_}{_}||{_}{_}||{_}{_}/| ===== |_| ===== |\{_}{_}||{_}{_}||{_}{_}||
    //||      ||      ||     / |==== s(   )s ====| \     ||      ||      ||
    //======================()  =================  ()======================
    //----------------------/| ------------------- |\----------------------
    //                     / |---------------------| \
    //-'--'--'           ()  '---------------------'  ()
    //                   /| ------------------------- |\    --'--'--'
    //       --'--'     / |---------------------------| \    '--'
    //                ()  |___________________________|  ()           '--'-
    //  --'-          /| _______________________________  |\
    // --' gpyy      / |__________________________________| \

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
    double turnconstant = 12.05; // per degree, so its rly small
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
        // put code here!


        while (opModeIsActive()) {
                strafeleft(.50);
                straferight(.50);
                turnright(90);
                turnleft(90);
                moveforward(.25);
                movebackward(.25);
                telemetry.addData("targetposition", fl.getTargetPosition());
                telemetry.addData("currentpostition", position);
                telemetry.update();
                sleep(1000);
                break;
        }
    }
    // this is only for dc motors
    void settargetpositioner(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);
    }

    void moveforward(double meters){

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (meters * moveconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
    void movebackward(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * moveconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(br, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }
    void strafeleft(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * strafeconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);


    }
    void straferight(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (meters * strafeconstant)*-1;
        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);


    }
    void turnright(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        int position = (int) (degrees * turnconstant)*-1;
        settargetpositioner(fl, -position);
        settargetpositioner(bl, -position);
        settargetpositioner(br, position);
        settargetpositioner(fr, position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }
    void turnleft(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (degrees * turnconstant)*-1;
        settargetpositioner(fl, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, -position);
        settargetpositioner(fr, -position);
        while (fl.isBusy()){sleep(1);}
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

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
        }
        if (place == 1){
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(997);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
        }
        if (place == 2){
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(1944);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
        }
        if (place == 3) {
            E.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            E.setTargetPosition(2990);
            E.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            E.setPower(1.0);
        }
    }
    void openclaw(){
        grabber.setPosition(.395);
    }
    void closeclaw(){
        grabber.setPosition(0.0);
    }

}