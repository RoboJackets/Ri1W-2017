package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by zipper on 9/9/17.
 */
@Autonomous
public class RoboJacketsAuto extends RoboJacketsLinearVisionOpMode {

    OpenGLMatrix lastLocation = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        //initialize();

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
        parameters.vuforiaLicenseKey = "ASjtsmX/////AAAAGRIKfx5rxUpHh/PGnhhtxFhARxDTGhbXGgYJW2M7DJjZagpGX95lZcr+fiuH/OGcw0aoprFZE0yjWDGZROVrgCnWeURYO9lw6IKCGWZVRJA0AmiVfyFWOUVXLGz5LyXFvhs8iNbAF38DFn/gbuD81RGl126CNWRK+fGiDk/dJTOZspFhZqIsV6heVpjhgb+ZUI771znQlFKR1f9t08viSaiLKXiDsD+zwpiPBh4fHPyM7w8H4wwdPBq0MHDjnfmmxUDEbTMaVeLMoLZWmav70qg9eUzdJ71yH4MnBOsmZ12F0KPQ1txlip0iOVH/0U3mVGqLRrXlhhQeVtefTF2i2kB8YiJqqnGEwsADWm4vSmQ3";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables jewels = vuforia.loadTrackablesFromAsset("Ri1W-2017");
        VuforiaTrackable redJewel = jewels.get(0);
        VuforiaTrackable blueJewel = jewels.get(1);

        waitForStart();

        jewels.activate();

        while (opModeIsActive()) {
            OpenGLMatrix redPose = ((VuforiaTrackableDefaultListener)redJewel.getListener()).getPose();
            OpenGLMatrix bluePose = ((VuforiaTrackableDefaultListener)blueJewel.getListener()).getPose();
            VectorF redTrans = redPose.getTranslation();
            VectorF blueTrans = bluePose.getTranslation();
            if (redTrans.get(0) < blueTrans.get(0)) {
                telemetry.addData("Red Jewel is: ", "Left");
            } else {
                telemetry.addData("Red Jewel is: ", "Right");
            }
        }
    }
}
