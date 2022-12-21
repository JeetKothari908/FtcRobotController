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
                for(Double d: robot.rawJiggleData) {
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
