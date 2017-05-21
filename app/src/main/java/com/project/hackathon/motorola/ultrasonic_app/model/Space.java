package com.project.hackathon.motorola.ultrasonic_app.model;

/**
 * Created by matheuscatossi on 5/20/17.
 */

public class Space {

    int id;
    double position1;
    double position2;
    double position3;
    double position4;
    String name;

    public Space() {

    }

    public Space(int id, String name, double position1, double position2, double position3, double position4){
        this.id = id;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.position4 = position4;
        this.name = name;
    }

    public Space(String name, double position1, double position2, double position3, double position4){
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.position4 = position4;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPosition1() {
        return position1;
    }

    public void setPosition1(double position1) {
        this.position1 = position1;
    }

    public double getPosition2() {
        return position2;
    }

    public void setPosition2(double position2) {
        this.position2 = position2;
    }

    public double getPosition3() {
        return position3;
    }

    public void setPosition3(double position3) {
        this.position3 = position3;
    }

    public double getPosition4() {
        return position4;
    }

    public void setPosition4(double position4) {
        this.position4 = position4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
