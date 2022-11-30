/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name="color_testing", group="Driver OP")
public class color_testor extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public ColorSensor color_sensor;

    @Override
    public void runOpMode() {

        color_sensor = hardwareMap.colorSensor.get("color_sensor");
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            String color = "";
            int red = color_sensor.red();
            int blue = color_sensor.blue();
            int green = color_sensor.green();
            double redblue= color_sensor.red()/color_sensor.blue();
            double redgreen= color_sensor.red()/color_sensor.green();
            double bluegreen= color_sensor.blue()/color_sensor.green();
            if (green > blue && red > blue){
                color = "yellow";
            }
            if (blue > green && red > green){
                color = "purple";
            }
            if (blue > red && green > red){
                color = "turqoise";
            }
            telemetry.addData("color is", color);
            telemetry.addData(String.valueOf(color_sensor.red()), "red");
            telemetry.addData(String.valueOf(color_sensor.blue()), "blue");
            telemetry.addData(String.valueOf(color_sensor.green()), "green");
            telemetry.addData(String.valueOf(redblue), "redblue");
            telemetry.addData(String.valueOf(redgreen), "redgreen");
            telemetry.addData(String.valueOf(bluegreen), "bluegreen");
            telemetry.update();
// this works
            
        }
    }
}