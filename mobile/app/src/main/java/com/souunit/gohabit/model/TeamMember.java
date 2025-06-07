package com.souunit.gohabit.model;

public class TeamMember {
    private String name;
    private String position;
    private int points;

    public TeamMember(String name, String position, int points) {
        this.name = name;
        this.position = position;
        this.points = points;
    }

    // Getters
    public String getName() { return name; }
    public String getPosition() { return position; }
    public int getPoints() { return points; }
}