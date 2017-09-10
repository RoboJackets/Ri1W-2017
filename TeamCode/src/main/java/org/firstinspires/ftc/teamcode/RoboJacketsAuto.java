package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Mat;
import org.opencv.core.Size;

/**
 * Created by zipper on 9/9/17.
 */
@Autonomous
public class RoboJacketsAuto extends RoboJacketsLinearVisionOpMode {

    int frameCount = 0;

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        //initialize();

        //Key detection from pictograph
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASjtsmX/////AAAAGRIKfx5rxUpHh/PGnhhtxFhARxDTGhbXGgYJW2M7DJjZagpGX95lZcr+fiuH/OGcw0aoprFZE0yjWDGZROVrgCnWeURYO9lw6IKCGWZVRJA0AmiVfyFWOUVXLGz5LyXFvhs8iNbAF38DFn/gbuD81RGl126CNWRK+fGiDk/dJTOZspFhZqIsV6heVpjhgb+ZUI771znQlFKR1f9t08viSaiLKXiDsD+zwpiPBh4fHPyM7w8H4wwdPBq0MHDjnfmmxUDEbTMaVeLMoLZWmav70qg9eUzdJ71yH4MnBOsmZ12F0KPQ1txlip0iOVH/0U3mVGqLRrXlhhQeVtefTF2i2kB8YiJqqnGEwsADWm4vSmQ3";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        waitForStart();

        relicTrackables.activate();

        RelicRecoveryVuMark mark = RelicRecoveryVuMark.UNKNOWN;
        int leftCount = 0;
        int centerCount = 0;
        int rightCount = 0;

        for (int i = 0; i < 20; i++) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    leftCount++;
                } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    centerCount++;
                } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    rightCount++;
                }
            }
        }

        if (leftCount > centerCount && leftCount > rightCount) {
            mark = RelicRecoveryVuMark.LEFT;
        } else if (centerCount > leftCount && centerCount > rightCount) {
            mark = RelicRecoveryVuMark.CENTER;
        } else if (rightCount > centerCount && rightCount > leftCount) {
            mark = RelicRecoveryVuMark.RIGHT;
        }

        telemetry.addData("Key: ", mark.toString());
        relicTrackables.deactivate();

        //Jewel vision processing
        waitForVisionStart();

        this.setCamera(Cameras.PRIMARY);

        this.setFrameSize(new Size(200, 200));

        enableExtension(Extensions.ROTATION);       //Automatic screen rotation correction
        enableExtension(Extensions.CAMERA_CONTROL); //Manual camera control

        rotation.setIsUsingSecondaryCamera(false);
        rotation.disableAutoRotate();
        rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);

        cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        cameraControl.setAutoExposureCompensation();

        boolean blueLeft = false;
        int blueLeftCount = 0;
        int blueRightCount = 0;

        for (int i = 0; i < 20; i++) {

            if (hasNewFrame()) {
                //Get the frame
                Mat rgba = getFrameRgba();
                if (isBlueLeft(rgba)) {
                    blueLeftCount++;
                } else {
                    blueRightCount++;
                }

                //Discard the current frame to allow for the next one to render
                discardFrame();

                //Do all of your custom frame processing here
                //For this demo, let's just add to a frame counter
                frameCount++;
            }

            //Wait for a hardware cycle to allow other processes to run
            waitOneFullHardwareCycle();
        }

        if (blueLeftCount > blueRightCount) {
            blueLeft = true;
        }

        telemetry.addData("Blue is on the left?", blueLeft);
    }
}
