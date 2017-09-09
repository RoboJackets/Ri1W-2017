package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by zipper on 9/9/17.
 */
@TeleOp
public class RoboJacketsTeleop extends RoboJacketsLinearVisionOpMode {
    private boolean intakeToggle = false;
    private boolean clampToggle = false;
    private boolean relicClawToggle = false;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        while(opModeIsActive()){
            setPower(gamepad1.left_stick_y,gamepad1.right_stick_y);
            if(intakeToggle) intake(1);
            else intake(0);
            if(clampToggle) glyphClampOpen();
            else glyphClampClose();
            if(gamepad1.a) intakeToggle = !intakeToggle;
            if(gamepad1.b) clampToggle  = !clampToggle;
            telemetry();
        }
    }
}
