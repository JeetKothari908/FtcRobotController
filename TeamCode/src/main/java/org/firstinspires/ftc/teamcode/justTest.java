package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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

            telemetry.addData("jiggles: ",robot.rawJiggleData);



            break;
        }
    }
}
