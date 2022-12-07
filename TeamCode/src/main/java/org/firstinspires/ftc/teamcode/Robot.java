
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Robot {

    // a class to import in order to have basic robot
    // comes with 4g of ram and 8g of storage, crazy!

    // motors
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public ColorSensor color_sensor;
    // autonomous strafe constants (trial & error so that units are meters in autonomous)
    double moveconstant = 1783; //this is how many targetposition units in a meter
    double motorrotation = 538; // this is how many targetposition units in a rotation
    double turnconstant = 2268; // untested, need to test
    double strafeconstant = 3; //untested, need to test
    int red = color_sensor.red();
    int blue = color_sensor.blue();
    int green = color_sensor.green();
    // driverop constants
    public double DXmult=.5;
    public double DYmult=.5;
    public double DTmult=.5;

    // for extender
    public DcMotor extender;
    public double extenderCmMultiple;

    // for arm
    public DcMotor shoulder;    // unused currently, may be implemented in future years
    public DcMotor elbow;
    public DcMotor wrist;

    // for claw or other extension
    public Servo claw;          // used lol
    public double clawOpenPos;
    public double clawClosePos;

    public boolean usesArm = false;
    public boolean usesExt = true;

    public Gamepad driverGamepad;

    public Robot(Gamepad g, HardwareMap h, String configuration){
        driverGamepad = g;

        // initializes motors
        initMotor(h);

        switch(configuration){
            case "basic mecanum":
                // figure this out next year
                break;
            case "boGilda":
                //TODO trial&error this
                fl.setDirection(DcMotor.Direction.REVERSE);
                fr.setDirection(DcMotor.Direction.FORWARD);
                bl.setDirection(DcMotor.Direction.REVERSE);
                br.setDirection(DcMotor.Direction.FORWARD);
                break;
        }







    }










    void initMotor(HardwareMap hardwareMap){
        fl = hardwareMap.get(DcMotor.class, "FL");
        fr = hardwareMap.get(DcMotor.class, "FR");
        bl = hardwareMap.get(DcMotor.class, "BL");
        br = hardwareMap.get(DcMotor.class, "BR");
        color_sensor = hardwareMap.colorSensor.get("color_sensor");

        if(usesArm){
            //figure out with arm
        }
        if(usesExt){
            extender=hardwareMap.get(DcMotor.class, "EXT");
        }
    }

    //driver controlled time
    public void driverMove(){
        //units defined for readability
        double horizontal = driverGamepad.left_stick_x*DXmult;   // this works so dont question it
        double vertical = driverGamepad.left_stick_y*DYmult;
        double turn = driverGamepad.right_stick_x*DTmult;

        //the god code. we do not change this. too much work to trial&error
        fl.setPower(Range.clip((vertical + horizontal - turn), -1, 1));
        fr.setPower(Range.clip((vertical - horizontal + turn), -1, 1));
        bl.setPower(Range.clip((vertical - horizontal - turn), -1, 1));
        br.setPower(Range.clip((vertical + horizontal + turn), -1, 1));
    }

    public void runAutonomous(){


        //                                         //
        //         INSERT AUTONOMOUS HERE          //
        //(in terms of the functions defined below)//
        //                                         //


    }

    void settargetpositioner(DcMotor motor, int position){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);
    }

    void moveforward(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        //fr.setDirection(DcMotorSimple.Direction.FORWARD);
        //bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (meters * moveconstant);

        settargetpositioner(fl, position);
        // settargetpositioner(fr, position);
        //settargetpositioner(bl, position);
        settargetpositioner(br, position);

    }
    void movebackward(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        //bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * moveconstant);

        settargetpositioner(fl, position);
        settargetpositioner(br, position);
        //settargetpositioner(bl, position);
        //settargetpositioner(br, position);

    }
    void strafeleft(double meters){
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        //fr.setDirection(DcMotorSimple.Direction.FORWARD);
        //bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        int position = (int) (meters * strafeconstant);

        settargetpositioner(fl, position);
        //settargetpositioner(fr, position);
        //settargetpositioner(bl, position);
        settargetpositioner(br, position);

    }
    void straferight(double meters){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        //fr.setDirection(DcMotorSimple.Direction.REVERSE);
        //bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (meters * strafeconstant);
        settargetpositioner(fl, position);
        //settargetpositioner(fr, position);
        //settargetpositioner(bl, position);
        settargetpositioner(br, position);

    }
    void turnright(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        //fr.setDirection(DcMotorSimple.Direction.FORWARD);
        //bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        int position = (int) (degrees * turnconstant);
        settargetpositioner(fl, position);
//        settargetpositioner(fr, position);
        //settargetpositioner(bl, position);
        // settargetpositioner(br, position);

    }
    void turnleft(int degrees){
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        int position = (int) (degrees * turnconstant);
        //      settargetpositioner(fl, position);
        //settargetpositioner(fr, position);
        //     settargetpositioner(bl, position);
        settargetpositioner(br, position);

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
            settargetpositioner(extender, 0);
        }
        if (place == 1){
            settargetpositioner(extender, 997);
        }
        if (place == 2){
            settargetpositioner(extender, 1994);
        }
        if (place == 3) {
            settargetpositioner(extender, 2990);
        }
    }
    void openclaw(){
        claw.setPosition(.295);
    }
    void closeclaw(){
        claw.setPosition(0.0);
    }
}

