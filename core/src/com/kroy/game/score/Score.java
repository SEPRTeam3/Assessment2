package com.kroy.game.score;

public class Score {

    private String name;
    private Integer value;
    private Integer ID;

    public Score(String name, Integer value, Integer ID){
        this.name = name;
        this.value = value;
        this.ID = ID;
    }

    public Score() {
        this.value = 0;
        this.name = "Undefined_User";
        this.ID = -1;
    }

    public String getName() {
        return this.name;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getValuestr(){
        return this.value.toString();
    }
    public Integer getIDint(){
        return this.ID;
    }
    public String getIDstr(){
        return this.ID.toString();
    }
    public void addScore(int value){
        this.value += value;
    }
    public void multiplyScore(int multiplier){
        this.value *= multiplier;
    }
    public void subtractScore(int value) {
        this.value -= value;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setID(Integer ID){
        this.ID = ID;
    }
}
