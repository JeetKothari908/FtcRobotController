package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.FileWriter;
import java.io.IOException;

@Autonomous(name="test", group="Autonomous")
public class justTest extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        abstraction robot = new abstraction(hardwareMap, gamepad1);

        robot.defineAndStart();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            robot.jiggle(15);
            try {

                FileWriter writer = new FileWriter("DCIM/JigglerOutput.txt");
                telemetry.addLine("1");
                writer.write("total of "+robot.rawJiggleData.size()+"datapoints recorded,");
                writer.write("each represents appx. "+((30)/robot.rawJiggleData.size())+" deg of area");
                telemetry.addLine("2");
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
                    writer.write(d + System.lineSeparator());
                }
                telemetry.addLine("3");
                writer.flush();
                writer.close();
                telemetry.addLine("4");


            } catch (IOException e) {
telemetry.addLine("dies");
            }
            telemetry.addData("jiggles: ",robot.rawJiggleData);
telemetry.update();


sleep(10000);
        }
    }
}
