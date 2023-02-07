package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import java.util.ArrayList;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
public class opencvpipelines extends OpenCvPipeline{
        Mat mat = new Mat();
        boolean thing = true;
        ArrayList<ArrayList<ArrayList<Double>>> image;
        @Override
        public Mat processFrame(Mat input) {
                Mat c = input.clone();
                telemetry.addData("referenced", c);
                int pixelsCounter = 0;
                ArrayList<ArrayList<ArrayList<Double>>> pixels = new ArrayList<>();
                for (int i = 0; i < c.height(); i++) {
                        ArrayList<ArrayList<Double> > t2 = new ArrayList<>();
                        for (int j = 0; j < c.width(); j++) {
                                pixelsCounter++;
                                ArrayList<Double> tmp = new ArrayList<>();
                                tmp.add(c.get(i, j)[0]);
                                tmp.add(c.get(i, j)[1]);
                                tmp.add(c.get(i, j)[2]);
                                t2.add(tmp);
                        }
                        pixels.add(t2);
                }

                return c;
        }

        public ArrayList<ArrayList<ArrayList<Double>>> get_pixels(){
                return image;
        }
}
