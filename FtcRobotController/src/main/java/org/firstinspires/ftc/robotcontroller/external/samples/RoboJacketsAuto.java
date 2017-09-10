package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by zipper on 9/9/17.
 */
@Autonomous
public class RoboJacketsAuto extends RoboJacketsLinearVisionOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initialize(true);

        //Key detection from pictograph

        waitForStart();
        glyphCol = doVuforia();
        telemetry.addData("mark",glyphCol);
    }
}

