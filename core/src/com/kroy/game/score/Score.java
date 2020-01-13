package com.kroy.game.score;

public class Score {

    private String name;
    private Integer value;

    public Score(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
    public Score()
    {
        this.value = 0;
        this.name = "Undefined_User";
    }

    public String getName() {
        return this.name;
    }

    public Integer getValue() {
        return this.value;
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
    public void changeName(String name)
    {
        this.name = name;
    }
}
