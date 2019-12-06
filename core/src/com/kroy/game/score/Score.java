package com.kroy.game.score;

public class Score {

}
    private String name;
    private Integer value;

    public Score(String name, Integer value){
        this.name = name;
        this.value = value;
    }
    public Score(){
        this.value = 0;
        this.name = "Undefined_User";
    }
    public String getName(){
        return this.name;
    }
    public Integer getValue(){
        return this.value;
    }
    public addScore(int value){
        this.value += value;
    }
    public multiplyScore(int multiplier){
        this.value *= multiplier;
    }
    public subtractScore(int value) {
        this.value -= value;
    }
    public changeName(String name){
        this.name = name;
}
