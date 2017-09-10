package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by zipper on 2/4/17.
 *
 * Abstract class that contains the bulk of our code for the velocity vortex challenge.
 * All of the helper methods for our auto opmodes and teleop opmodes are neatly kept here.
 */

public abstract class RoboJacketsLinearVisionOpMode extends LinearVisionOpMode {
    private double NOT_DEPLOYED_POWER = .1;
    private double DEPLOYED_POWER = .9;
    private double CLAMP_LEFT_OPEN = .1;
    private double CLAMP_LEFT_CLOSED = .9;
    private double CLAMP_RIGHT_OPEN = .1;
    private double CLAMP_RIGHT_CLOSED = .9;
    private double GLYPH_IN = .1;
    private double GLYPH_PUSH = .9;
    private double RELIC_CLAW_UP = .1;
    private double RELIC_CLAW_DOWN = .9;
    private double RELIC_CLAW_OPEN = .9;
    private double RELIC_CLAW_CLOSED = .7;

    private DcMotor leftBack;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor rightFront;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private DcMotor glyphLift;
    private DcMotor relicExtend;
    private Servo deploy;
    private Servo clampLeft;
    private Servo clampRight;
    private Servo pushGlyph;
    private Servo relicClawPulley;
    private Servo relicClaw;
    public ElapsedTime runtime = new ElapsedTime();

    /**
     * Initializes all necessary components including
     * Motors
     * Sensors
     * Servos
     */
    public void initialize() {
//        leftFront = hardwareMap.dcMotor.get("leftFront");
//        leftBack = hardwareMap.dcMotor.get("leftBack");
//        rightFront = hardwareMap.dcMotor.get("rightFront");
//        rightBack = hardwareMap.dcMotor.get("rightBack");
//        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
//        intakeRight = hardwareMap.dcMotor.get("intakeRight");
//        glyphLift = hardwareMap.dcMotor.get("glyphLift");
//        relicExtend = hardwareMap.dcMotor.get("relicExtend");
//
//        pushGlyph = hardwareMap.servo.get("pushGlyph");
//        clampLeft = hardwareMap.servo.get("clampLeft");
//        clampRight = hardwareMap.servo.get("clampRight");
//        deploy = hardwareMap.servo.get("deploy");
        relicClawPulley = hardwareMap.servo.get("relicClawPulley");
        relicClaw = hardwareMap.servo.get("relicClaw");

//        pushGlyph.setPosition(GLYPH_IN);
//        clampLeft.setPosition(CLAMP_LEFT_OPEN);
//        clampRight.setPosition(CLAMP_RIGHT_OPEN);
//        deploy.setPosition(NOT_DEPLOYED_POWER);
        relicClawPulley.setPosition(RELIC_CLAW_UP);
        relicClaw.setPosition(RELIC_CLAW_CLOSED);


//        rightBack.setDirection(DcMotor.Direction.REVERSE);
//        rightFront.setDirection(DcMotor.Direction.REVERSE);
//        intakeRight.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Initialization ", "complete");
        telemetry.update();
    }

    /**
     * Deploys mechanisms that go outside of height
     */
    public void deploy() {
        deploy.setPosition(DEPLOYED_POWER);
    }
    public void glyphPush() {
        pushGlyph.setPosition(GLYPH_PUSH);
    }
    public void glyphIn() {
        pushGlyph.setPosition(GLYPH_IN);
    }
    public void pulleyDown() {
        relicClawPulley.setPosition(RELIC_CLAW_DOWN);
    }
    public void pulleyUp() {
        relicClawPulley.setPosition(RELIC_CLAW_UP);
    }
    public void relicClawClose() {
        relicClaw.setPosition(RELIC_CLAW_CLOSED);
    }
    public void relicClawOpen() {
        relicClaw.setPosition(RELIC_CLAW_OPEN);
    }
    public void relicExtend(double power) {
        relicExtend.setPower(power);
    }
    public void intake(double power) {
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
    }
    public void glyphLift(double power) {
        glyphLift.setPower(power);
    }

    public void glyphClampClose() {
        clampLeft.setPosition(CLAMP_LEFT_CLOSED);
        clampRight.setPosition(CLAMP_RIGHT_CLOSED);
    }
    public void glyphClampOpen() {
        clampLeft.setPosition(CLAMP_LEFT_OPEN);
        clampRight.setPosition(CLAMP_RIGHT_OPEN);
    }
    public void setPower(double powerLeft, double powerRight) {
        telemetry();
        leftFront.setPower(powerLeft);
        leftBack.setPower(powerLeft);
        rightFront.setPower(powerRight);
        rightBack.setPower(powerRight);
    }
    /**
     * Go forward indefinitely
     *
     * @param power Drive at this power
     * @throws InterruptedException
     */
    public void forward(double power) throws InterruptedException {
        telemetry();
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(power);
    }

    /**
     * Turn left indefinitely
     *
     * @param power Turn at this power
     * @throws InterruptedException
     */
    public void left(double power) throws InterruptedException {
        telemetry();
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(power);
    }

    /**
     * Turn right indefinitely
     *
     * @param power Turn at this power
     * @throws InterruptedException
     */
    public void right(double power) throws InterruptedException {
        telemetry();
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
    }

    /**
     * Drive based on encoder ticks
     *
     * @param leftSpeed   Speed for left motors
     * @param rightSpeed  Speed for right motors
     * @param leftCounts  Ticks for left encoders
     * @param rightCounts Ticks for right encoders
     * @param timeoutS    Timeout seconds
     * @throws InterruptedException
     */
    public void encoderDrive(double leftSpeed, double rightSpeed, double leftCounts, double rightCounts, double timeoutS) throws InterruptedException {
        zeroEncoders();

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(leftSpeed);
            rightFront.setPower(rightSpeed);
            leftBack.setPower(leftSpeed);
            rightBack.setPower(rightSpeed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS)) {
                telemetry();
                if (Math.abs(leftFront.getCurrentPosition()) > Math.abs(leftCounts) * (7.0 / 8) || Math.abs(leftBack.getCurrentPosition()) > Math.abs(leftCounts) * (7.0 / 8) || Math.abs(rightFront.getCurrentPosition()) > Math.abs(rightCounts) * (7.0 / 8) || Math.abs(rightBack.getCurrentPosition()) > Math.abs(rightCounts) * (7.0 / 8)) {
                    leftFront.setPower(.5 * leftSpeed);
                    rightFront.setPower(.5 * rightSpeed);
                    leftBack.setPower(.5 * leftSpeed);
                    rightBack.setPower(.5 * rightSpeed);
                    telemetry.addData("speed", .5);
                } else if (Math.abs(leftFront.getCurrentPosition()) > Math.abs(leftCounts) * (.5) || Math.abs(leftBack.getCurrentPosition()) > Math.abs(leftCounts) * (.5) || Math.abs(rightFront.getCurrentPosition()) > Math.abs(rightCounts) * (.5) || Math.abs(rightBack.getCurrentPosition()) > Math.abs(rightCounts) * (.5)) {
                    leftFront.setPower(.75 * leftSpeed);
                    rightFront.setPower(.75 * rightSpeed);
                    leftBack.setPower(.75 * leftSpeed);
                    rightBack.setPower(.75 * rightSpeed);
                    telemetry.addData("speed", .75);
                } else {
                    telemetry.addData("speed", 1);
                }
                if (Math.abs(leftFront.getCurrentPosition()) > Math.abs(leftCounts) || Math.abs(leftBack.getCurrentPosition()) > Math.abs(leftCounts) || Math.abs(rightFront.getCurrentPosition()) > Math.abs(rightCounts) || Math.abs(rightBack.getCurrentPosition()) > Math.abs(rightCounts)) {
                    leftFront.setPower(0);
                    leftBack.setPower(0);
                    rightFront.setPower(0);
                    rightBack.setPower(0);
                    break;
                }
                waitOneFullHardwareCycle();
            }
            stopAll();

            zeroEncoders();
            //sleep(250);   // optional pause after each move
        }
    }

    /**
     * Zeroes encoders and resets them
     */
    public void zeroEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Stops all motors
     *
     * @throws InterruptedException
     */
    public void stopAll() throws InterruptedException {
        telemetry();
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    /**
     * Telemetries all debugging information
     */
    public void telemetry() {
        telemetry.addData("leftFront to position", (leftFront.getTargetPosition() - leftFront.getCurrentPosition()));
        telemetry.addData("rightFront to position", (rightFront.getTargetPosition() - rightFront.getCurrentPosition()));
        telemetry.addData("leftBack to position", (leftBack.getTargetPosition() - leftBack.getCurrentPosition()));
        telemetry.addData("rightBack to position", (rightBack.getTargetPosition() - rightBack.getCurrentPosition()));
        telemetry.update();
    }

    /**
     * Processes jewel orientations
     */
    public void findJewels(Mat frame) {
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2BGR);
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

        Mat leftFrame = new Mat(hsvFrame, new Range(hsvFrame.rows()/2, hsvFrame.rows()), new Range(0, hsvFrame.cols()));
        Mat leftBlue = new Mat();
        Core.inRange(leftFrame, new Scalar(95, 0, 0), new Scalar(105, 255, 255), leftBlue);

        int leftCount = 0;
        for (int i = 0; i < leftBlue.rows(); i++) {
            for (int j = 0; j < leftBlue.cols(); j++) {
                if (leftBlue.get(i, j)[0] != 0) {
                    leftCount++;
                }
            }
        }

        Mat rightFrame = new Mat(hsvFrame, new Range(0, hsvFrame.rows()/2), new Range(0, hsvFrame.cols()));
        Mat rightBlue = new Mat();
        Core.inRange(rightFrame, new Scalar(95, 0, 0), new Scalar(105, 255, 255), rightBlue);

        int rightCount = 0;
        for (int i = 0; i < rightBlue.rows(); i++) {
            for (int j = 0; j < rightBlue.cols(); j++) {
                if (rightBlue.get(i, j)[0] != 0) {
                    rightCount++;
                }
            }
        }

        String orientation = "";
        if (leftCount > rightCount) {
            orientation = "left";
        } else {
            orientation = "right";
        }
        telemetry.addData("Blue jewel is on the ", orientation);
        telemetry.addData("leftCount",leftCount);
        telemetry.addData("rightCount",rightCount);
    }

}