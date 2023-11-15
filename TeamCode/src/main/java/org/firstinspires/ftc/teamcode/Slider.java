package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Slider {

    /*public static int LOW_JUNCTION_TICKS = 750;
    public static int MID_JUNCTION_TICKS = 1550;
    public static int HIGH_JUNCTION_TICKS = 2600;
    public static int TICK_DROP_BEFORE_RELEASE = 100;*/

    private HardwareMap hardwareMap;
    DcMotor slider = null;

    //diameter of spool = 50mm
    //REV HEX 40:1..ticks per rev :''

    public Slider(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        slider = hardwareMap.dcMotor.get("slider");
       // slider.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*public void goToLevel(int junctionLevel) {

        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (junctionLevel == 0) {
            goToPosition(300);
        } else if (junctionLevel == 1) {
            goToPosition(LOW_JUNCTION_TICKS);
        } else if (junctionLevel == 2) {
            goToPosition(MID_JUNCTION_TICK);
        } else {
            goToPosition(HIGH_JUNCTION_TICKS);
        }
    }*/

    public void goUp() {

        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        goToPosition(1300);


    }

    private void goToPosition(int desiredPosition) {

        slider.setTargetPosition(desiredPosition);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slider.setPower(1.0);

        while (slider.isBusy()) {
            sleep(50);
        }


    }

   /* public void holdElevator(int holdIterations) {
        for (int i = 0; i < holdIterations; i++) {
            slider.setPower(0.05);
            sleep(50);
        }
    }*/

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void goToHome() {
        slider.setTargetPosition(0);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slider.setPower(0.5);

        while (slider.isBusy()) {
            sleep(50);
        }
    }

   /* public void dropBeforeRelease() {
        int currentTicks = elevator.getCurrentPosition();
        elevator.setTargetPosition(currentTicks - TICK_DROP_BEFORE_RELEASE);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(1.0);
        while (elevator.isBusy()) {
            sleep(50);
        }
    }*/
}
