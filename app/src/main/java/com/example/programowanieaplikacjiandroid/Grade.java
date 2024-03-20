package com.example.programowanieaplikacjiandroid;

public class Grade {
    private String name;
    private int grade;

    public Grade(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
