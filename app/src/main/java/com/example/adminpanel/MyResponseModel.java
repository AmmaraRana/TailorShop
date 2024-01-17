package com.example.adminpanel;

public class MyResponseModel {
    private int arm_length_cm;
    private int leg_length_cm;
    private int neck_circumference_cm;
    private int shoulder_width_cm;

    public MyResponseModel(int arm_length_cm, int leg_length_cm, int neck_circumference_cm, int shoulder_width_cm) {
        this.arm_length_cm = arm_length_cm;
        this.leg_length_cm = leg_length_cm;
        this.neck_circumference_cm = neck_circumference_cm;
        this.shoulder_width_cm = shoulder_width_cm;
    }

    public int getArm_length_cm() {
        return arm_length_cm;
    }

    public void setArm_length_cm(int arm_length_cm) {
        this.arm_length_cm = arm_length_cm;
    }

    public int getLeg_length_cm() {
        return leg_length_cm;
    }

    public void setLeg_length_cm(int leg_length_cm) {
        this.leg_length_cm = leg_length_cm;
    }

    public int getNeck_circumference_cm() {
        return neck_circumference_cm;
    }

    public void setNeck_circumference_cm(int neck_circumference_cm) {
        this.neck_circumference_cm = neck_circumference_cm;
    }

    public int getShoulder_width_cm() {
        return shoulder_width_cm;
    }

    public void setShoulder_width_cm(int shoulder_width_cm) {
        this.shoulder_width_cm = shoulder_width_cm;
    }
}
