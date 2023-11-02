package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
public class ScannerCoordinates {

    //WebcamR
    public static Point RED_CAROUSAL_LEFT_RECTANGLE_POINT1 = new Point(15, 50);
    public static Point RED_CAROUSAL_RIGHT_RECTANGLE_POINT1 = new Point(180, 0);

    //WebcamL
    public static Point RED_WAREHOUSE_LEFT_RECTANGLE_POINT1 = new Point(70, 100);
    public static Point RED_WAREHOUSE_RIGHT_RECTANGLE_POINT1 = new Point(225, 0);

    //WebcamL
    public static Point BLUE_CAROUSAL_LEFT_RECTANGLE_POINT1 = new Point(65, 0);
    public static Point BLUE_CAROUSAL_RIGHT_RECTANGLE_POINT1 = new Point(225, 0);

    //WebcamR
    public static Point BLUE_WAREHOUSE_LEFT_RECTANGLE_POINT1 = new Point(0, 60);
    public static Point BLUE_WAREHOUSE_RIGHT_RECTANGLE_POINT1 = new Point(160, 60);

    public static String LEFT_CAMERA_NAME = "WebcamL";
    public static String RIGHT_CAMERA_NAME = "Webcam1" +
            "";

    private String webcamName;

    private Point leftRectanglePoint1;
    private Point rightRectanglePoint1;

    private String allianceColor;

    private Point leftRectanglePoint2;
    private Point rightRectanglePoint2;

    private double boxHeightMiddle = 60;
    private double boxWidthMiddle = 30;
    private double boxWidthLeft= 60;
    private double boxHeightLeft = 30;

    public ScannerCoordinates(RobotPosition robotPosition) {

        if(RobotPosition.RED_CAROUSAL.equals(robotPosition)) {
            this.webcamName = RIGHT_CAMERA_NAME;
            this.leftRectanglePoint1 = RED_CAROUSAL_LEFT_RECTANGLE_POINT1;
            this.rightRectanglePoint1 = RED_CAROUSAL_RIGHT_RECTANGLE_POINT1;
            this.allianceColor = "Red";
        } else if(RobotPosition.RED_WAREHOUSE.equals(robotPosition)) {
            this.webcamName = LEFT_CAMERA_NAME;
            this.leftRectanglePoint1 = RED_WAREHOUSE_LEFT_RECTANGLE_POINT1;
            this.rightRectanglePoint1 = RED_WAREHOUSE_RIGHT_RECTANGLE_POINT1;
            this.allianceColor = "Red";
        } else if(RobotPosition.BLUE_CAROUSAL.equals(robotPosition)) {
            this.webcamName = LEFT_CAMERA_NAME;
            this.leftRectanglePoint1 = BLUE_CAROUSAL_LEFT_RECTANGLE_POINT1;
            this.rightRectanglePoint1 = BLUE_CAROUSAL_RIGHT_RECTANGLE_POINT1;
            this.allianceColor = "Blue";
        } else {
            this.webcamName = RIGHT_CAMERA_NAME;
            this.leftRectanglePoint1 = BLUE_WAREHOUSE_LEFT_RECTANGLE_POINT1;
            this.rightRectanglePoint1 = BLUE_WAREHOUSE_RIGHT_RECTANGLE_POINT1;
            this.allianceColor = "Blue";
        }

        leftRectanglePoint2 = new Point(leftRectanglePoint1.x + boxWidthLeft, leftRectanglePoint1.y + boxHeightLeft);
        rightRectanglePoint2 = new Point(rightRectanglePoint1.x + boxWidthMiddle, rightRectanglePoint1.y + boxHeightMiddle);
    }


    public ScannerCoordinates(String webcamName, double leftX, double leftY, double rightX, double rightY) {
        this.webcamName = webcamName;

        leftRectanglePoint1 = new Point(leftX, leftY);
        rightRectanglePoint1 = new Point(rightX, rightY);

        leftRectanglePoint2 = new Point(leftX + boxWidthLeft, leftY + boxHeightLeft);
        rightRectanglePoint2 = new Point(rightX + boxWidthMiddle, rightY + boxHeightMiddle);
    }

    public String getWebcamName() {
        return webcamName;
    }


    public Point getLeftRectanglePoint1() {
        return leftRectanglePoint1;
    }

    public Point getRightRectanglePoint1() {
        return rightRectanglePoint1;
    }

    public Point getLeftRectanglePoint2() {
        return leftRectanglePoint2;
    }

    public Point getRightRectanglePoint2() {
        return rightRectanglePoint2;
    }

    public String getAllianceColor() {return allianceColor;}
}