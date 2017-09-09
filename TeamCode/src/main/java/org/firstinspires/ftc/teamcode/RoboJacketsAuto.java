package org.firstinspires.ftc.teamcode;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Mat;
import org.opencv.core.Size;

/**
 * Created by zipper on 9/9/17.
 */

public class RoboJacketsAuto extends RoboJacketsLinearVisionOpMode {

    int frameCount = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForVisionStart();
        this.setCamera(Cameras.PRIMARY);
        this.setFrameSize(new Size(900, 900));

        enableExtension(Extensions.ROTATION);       //Automatic screen rotation correction
        enableExtension(Extensions.CAMERA_CONTROL); //Manual camera control

        rotation.setIsUsingSecondaryCamera(false);
        rotation.disableAutoRotate();
        rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);

        cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        cameraControl.setAutoExposureCompensation();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Screen Rotation", rotation.getScreenOrientationActual());
            telemetry.addData("Frame Rate", fps.getFPSString() + " FPS");
            telemetry.addData("Frame Size", "Width: " + width + " Height: " + height);
            telemetry.addData("Frame Counter", frameCount);

            if (hasNewFrame()) {
                //Get the frame
                Mat rgba = getFrameRgba();
                findJewel(rgba);

                //Discard the current frame to allow for the next one to render
                discardFrame();

                //Do all of your custom frame processing here
                //For this demo, let's just add to a frame counter
                frameCount++;
            }

            //Wait for a hardware cycle to allow other processes to run
            waitOneFullHardwareCycle();
        }
    }
}
