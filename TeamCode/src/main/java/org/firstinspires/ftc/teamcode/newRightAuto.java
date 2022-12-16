package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="rightauto", group="Autonomous")
public class rightauto extends LinearOpMode {

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
    /* int red = color_sensor.red();
     int blue = color_sensor.blue();
     int green = color_sensor.green();*/
    @Override
    public void runOpMode() {



        fl = hardwareMap.get(DcMotor.class, "FL");
        fr = hardwareMap.get(DcMotor.class, "FR");
        bl = hardwareMap.get(DcMotor.class, "BL");
        br = hardwareMap.get(DcMotor.class, "BR");
        E = hardwareMap.get(DcMotor.class, "E");
        color_sensor = hardwareMap.colorSensor.get("color_sensor");

        grabber = hardwareMap.get(Servo.class,"grab"); //THE SERVO IS IN PERCENT, BW/ 1 OR 0. BASELINE IS .5

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
       
        //auto goes here (Aiden)
	      /*for starting on the RIGHT.
        set robot so the sensor is in line with the cone.
        this should score a max of 26 points.
        we push the signal cone around but thats allowed by the rules.
        ALL DISTANCES MUST BE TESTED ON THE GAME FIELD BEFORE  
        MATCHES. Fields are standardized to +-1 inch.
        */
        //initial movement
        closeclaw();
        moveright(0.53);
        //Scan cone code
        int red = getRed();
        int blue = getBlue();
        int green = getGreen();
        
        //differentiation
        if (green > blue && red > blue){
            color = "yellow";
        }
        if (blue > green && red > green){
            color = "purple";
        }
        if (blue > red && green > red){
            color = "turquoise";
        }
           
        if(color.equals("turquoise")){
          //Moves to zone
          moveright(0.32);
          moveback(0.55);
          moveright(0.24);
          //drops cone
          extend(1);
          moveforward(0.05);
          openclaw();
          movebackward(0.05);
          extend(0);
        }
        else if(color.equals("yellow"))
        {
          //moves to zone
         moveright(0.53);
         //drops cone
          extend(2);
          moveforward(0.05);
          closeclaw();
          movebackward(0.05);
          extend(0);
        }
        else
        {
          //moves to zone
          moveright(0.32);
          moveforward(0.60);
          moveright(0.24);
          //drops cone
          extend(3);
          moveforward(0.05);
          openclaw();
          movebackward(0.05);
          extend(0);
        }
      //end of auto. Robot is in correct zone.

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

        int position = (int) (meters * moveconstant)*-1;

        settargetpositioner(fl, position);
        settargetpositioner(fr, position);
        settargetpositioner(bl, position);
        settargetpositioner(br, position);

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
