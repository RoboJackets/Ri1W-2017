package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by zipper on 9/9/17.
 */
@TeleOp
public class RoboJacketsTeleop extends RoboJacketsLinearVisionOpMode {
    private boolean intakeToggle = false;
    private boolean relicClawToggle = false;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive()){
            teleop();
            if(gamepad1.b) {
                runtime.reset();
                while(runtime.seconds() < 1.5) {
                    glyphClampClose();
                    glyphLift(1);
                    teleop();
                }
                runtime.reset();
                while(runtime.seconds() < 1.5) {
                    glyphPush();
                    teleop();
                }
                glyphIn();
                glyphClampOpen();
                runtime.reset();
                while(runtime.seconds() < 1.5) {
                    glyphLift(-1);
                    teleop();
                }
            }
        }
    }
    public void teleop() {
        setPower(gamepad1.left_stick_y,gamepad1.right_stick_y);
        if(intakeToggle) intake(1);
        else intake(0);
        if(relicClawToggle) relicClawClose();
        else relicClawOpen();
        if(gamepad1.a) intakeToggle = !intakeToggle;
        if(gamepad1.x) relicClawToggle = !relicClawToggle;
        telemetry();
    }
}
