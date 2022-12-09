package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="moverer", group="Autonomous")
public class threeencodertester extends LinearOpMode {

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
    double moveconstant = 1783; //this is how many targetposition units in a meter
    double motorrotation = 538; // this is how many targetposition units in a rotation
    double turnconstant = 2268; // untested, need to test
    double strafeconstant = 3; //untested, need to test
    String color = "";
    int red = color_sensor.red();
    int blue = color_sensor.blue();
    int green = color_sensor.green();
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
        moveforward(.25);

        moveExtender(0);
        moveExtender(1);
        moveExtender(2);
        moveExtender(3);
        openclaw();
        closeclaw();
        while (opModeIsActive()) {}
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

        int position = (int) (meters * moveconstant);

        settargetpositioner(fl, position);
 //       settargetpositioner(fr, position);
 //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    void movebackward(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * moveconstant);
        settargetpositioner(fl, position);
        //       settargetpositioner(fr, position);
        //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    void strafeleft(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * strafeconstant);
        settargetpositioner(fl, position);
        //       settargetpositioner(fr, position);
        //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    void straferight(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (meters * strafeconstant);
        settargetpositioner(fl, position);
        //       settargetpositioner(fr, position);
        //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    void turnright(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        int position = (int) (degrees * turnconstant);
        settargetpositioner(fl, position);
        //       settargetpositioner(fr, position);
        //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    void turnleft(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        settargetpositioner(fl, position);
        //       settargetpositioner(fr, position);
        //       settargetpositioner(bl, position);
        settargetpositioner(br, position);
        while (fl.getPower() > 0.0){
            fr.setPower(fl.getPower());
            bl.setPower(fl.getPower());
        }
        fr.setPower(0.0);
        bl.setPower(0.0);
    }
    String colortestor(){
        if (green > blue && red > blue){
            return "yellow";
        }
        if (blue > green && red > green){
            return "purple";
        }
        if (blue > red && green > red){
            return "turqoise";
        }
        else {
            return "no color, sense again";
        }
    }
    void moveExtender(int place){
        if (place == 0){
            settargetpositioner(E, 0);
        }
        if (place == 1){
            settargetpositioner(E, 997);
        }
        if (place == 2){
            settargetpositioner(E, 1994);
        }
        if (place == 3) {
            settargetpositioner(E, 2990);
        }
    }
    void openclaw(){
        grabber.setPosition(.295);
    }
    void closeclaw(){
        grabber.setPosition(0.0);
    }

}