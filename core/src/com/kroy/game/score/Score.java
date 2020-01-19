package com.kroy.game.score;

public class Score {

    private String name;
    private Integer value;
    private Integer ID;

    public Score(String name, Integer value, Integer ID){
        /**
         * Creates a score object with the 3 given inputs
         */
        this.name = name;
        this.value = value;
        this.ID = ID;
    }

    public Score() {
        /**
         * Creates a score object with default values
         */
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
