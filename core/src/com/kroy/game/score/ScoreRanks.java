package com.kroy.game.score;

import java.io.*;
import java.util.ArrayList;

public class ScoreRanks {
    /**
     * Reads and Writes scores, and sorts new scores
     */
    private ArrayList<Score> ScoresList;
    private Score newestScore;
    private String csvFileName = "ScoreData.csv";

    public ScoreRanks(){
        /**
         * Initialises class and immediately reads from the save file.
         */
        this.newestScore = null;
        this.ScoresList = new ArrayList<>();
        ReadFile();

    }
    private void ReadFile(){
        /**
         * Reads from the save file to get data from previous games
         */
        try {
            BufferedReader bufferedCsv = new BufferedReader(new FileReader(csvFileName));
            String row;
            while ((row = bufferedCsv.readLine()) != null) {
                String[] lineData = row.split(",");
                Integer scoreInt = Integer.parseInt(lineData[1]);
                InsertScore(lineData[0], scoreInt, true);
            }
            bufferedCsv.close();
        } catch(FileNotFoundException e) {
            TestScores();
        } catch(IOException e){
            ;
        }
    }
    private void WriteFile(){
        /**
         * Saves all current scores to the save file
         */
        try {
            FileWriter csvWriter = new FileWriter(csvFileName);
            for (int i = 0; i < this.ScoresList.size(); i++) {
                Score tempScore = this.ScoresList.get(i);
                String name = tempScore.getName();
                csvWriter.append(name);
                csvWriter.append(",");
                csvWriter.append(tempScore.getValuestr());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        }
        catch(IOException e){
        }
    }

    private void TestScores(){
        /**
         * Creates 20 base scores if the original csv file cannot be found.
         */
        for (Integer i = 0; i < 20; i++){
            String tempString = "test" + i.toString();
            Score tempScore = new Score(tempString, i + 1, i + 1);
            this.ScoresList.add(tempScore);
        }
        WriteFile();
    }

    public void setPlayerScore(Score playerScore){
        /**
         * public method for giving a new score from a finished game.
         */
        InsertScore(playerScore.getName(), playerScore.getValue(), false);
    }

    private void InsertScore(String newName, Integer newScore, Boolean fromFile){
        /**
         * Takes scores, either from the file or new scores, and sorts them into a list.
         */
        if (fromFile == false) {
            if (this.ScoresList.size() == 0) {
                this.newestScore = new Score(newName, newScore, 1);
                this.ScoresList.add(0, this.newestScore);
            } else {
                int numScores = this.ScoresList.size();
                for (int i = 0; i < numScores; i++) {
                    if (this.ScoresList.get(i).getValue() < newScore) {
                        this.newestScore = new Score(newName, newScore, numScores + 1);
                        this.ScoresList.add(i, this.newestScore);
                    }
                }
            } WriteFile();
        } else { // Scores from the file are already sorted
            int numScores = this.ScoresList.size();
            this.newestScore = new Score(newName, newScore, numScores + 1);
            this.ScoresList.add(numScores, this.newestScore);
        }
    }

    public ArrayList SelectScores(String scoreTypes){
		/**
         * score types: "TopTen", "TopAndNew"
         *TopTen - Top ten overall scores,
         *TopAndNew - Top 8 eight scores + new newest added score + next score above newest score
         */
        ArrayList<Score> outputScore = new ArrayList<>(10);
        if (scoreTypes == "TopTen"){
            for (int i = 0; i < 10; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } return outputScore;
        } else if(scoreTypes == "TopAndNew"){
            if (getRank(this.newestScore) < 11){
                return SelectScores("TopTen");
            }
            for (int i = 0; i < 8; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } int rank = getRank(this.newestScore);
            outputScore.add(this.ScoresList.get(rank-1));
            outputScore.add(this.ScoresList.get(rank));
            return outputScore;
        }
        return outputScore;
    }
    private Integer getRank(Score ScoreToFind){
        /**
         * Takes a score, finds the rank of that score out of all scores.
         */
        return this.ScoresList.indexOf(ScoreToFind);
    }
}