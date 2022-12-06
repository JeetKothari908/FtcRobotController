package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name="rightAuto", group="Autonomous")
public class rightAuto extends LinearOpMode {

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

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public DcMotor E;
    public ColorSensor color_sensor;
    double moveconstant = 1783.0;
    double motorrotation = 537.7;
    double turnconstant = 250.0;
    double strafeconstant = 250.0;
    String color = "";
    int red = color_sensor.red();
    int blue = color_sensor.blue();
    int green = color_sensor.green();
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

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();


        moveforward(0.53);

        //Scan cone code
        String color = null;

        //differentiation
        if (green > blue && red > blue){
            color = "yellow";
        }
        if (blue > green && red > green){
            color = "purple";
        }
        if (blue > red && green > red){
            color = "turqoise";
        }

        moveforward(0.53/*idk what 'du' means aiden,du*/);

        //for starting on the RIGHT:

        //initial movement

        //INSERT CODE TO FOR CLAW TO GRAB

//NEED MOVE LEFT TURN LEFT FUNCTIONS
        //NEED MOVE LEFT TURN LEFT FUNCTIONS
        //NEED MOVE LEFT TURN LEFT FUNCTIONS
        //NEED MOVE LEFT TURN LEFT FUNCTIONS

        moveforward(0.53);
        //extend(0/*i will assume you meant this aiden.5*/);
        //the extender needs to go up for the scanner to work
        //(unless we can fix this?)
        //Scan cone code

        //differentiation
        if (green > blue && red > blue){
            color = "yellow";
        }
        if (blue > green && red > green){
            color = "purple";
        }
        if (blue > red && green > red){
            color = "turqoise";
        }

        if(color.equals("turqoise"))
        {
            moveforward(0.15);
            turnLeft(90);
            movebackward(0.65);
            straferight(0.40);
            moveforward(0.10);
            //low extender
            //extend(1);
            //release claw (NEEDS TO BE ADDED)

        }
        else if(color.equals("yellow"))
        {
            turnLeft(90);
            strafeleft(0.40);
            moveforward(0.10);
            //mid extender
            //extend(2);
            //release claw (NEEDS TO BE ADDED)
        }
        else
        {
            moveforward(0.15);
            turnLeft(90);
            moveforward(0.65);
            straferight(0.40);
            moveforward(0.10);
            //extender to low height
            //extend(1);
            //release claw (NEEDS TO BE ADDED)
        }


        while (opModeIsActive()) {}

    }

    void moveforward(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (meters * moveconstant);
        fl.setTargetPosition(position);
        fr.setTargetPosition(position);
        bl.setTargetPosition(position);
        br.setTargetPosition(position);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(1.0);
        fr.setPower(1.0);
        bl.setPower(1.0);
        br.setPower(1.0);

    }
    void movebackward(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (meters * moveconstant);
        fl.setTargetPosition(position);
        fr.setTargetPosition(position);
        bl.setTargetPosition(position);
        br.setTargetPosition(position);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(1.0);
        fr.setPower(1.0);
        bl.setPower(1.0);
        br.setPower(1.0);
    }
    void strafeleft(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (meters * strafeconstant);
        fl.setTargetPosition(position);
        fr.setTargetPosition(position);
        bl.setTargetPosition(position);
        br.setTargetPosition(position);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(1.0);
        fr.setPower(1.0);
        bl.setPower(1.0);
        br.setPower(1.0);
    }
    void straferight(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (meters * strafeconstant);
        fl.setTargetPosition(position);
        fr.setTargetPosition(position);
        bl.setTargetPosition(position);
        br.setTargetPosition(position);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(1.0);
        fr.setPower(1.0);
        bl.setPower(1.0);
        br.setPower(1.0);
    }
    void turnRight(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (degrees * turnconstant);
        fl.setTargetPosition(position);
        //      fr.setTargetPosition(position);
        bl.setTargetPosition(position);
//        br.setTargetPosition(position);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //       fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //       br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setPower(1.0);
//        fr.setPower(1.0);
        bl.setPower(1.0);
        //       br.setPower(1.0);
    }
    void turnLeft(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int position = (int) (degrees * turnconstant);
        //       fl.setTargetPosition(position);
        fr.setTargetPosition(position);
        //      bl.setTargetPosition(position);
        br.setTargetPosition(position);
        //      fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //     bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //      fl.setPower(1.0);
        fr.setPower(1.0);
        //      bl.setPower(1.0);
        br.setPower(1.0);
    }
    void colortestor(){
        if (green > blue && red > blue){
            color = "yellow";
        }
        if (blue > green && red > green){
            color = "purple";
        }
        if (blue > red && green > red){
            color = "turqoise";
        }
    }
}