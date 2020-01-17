package com.kroy.game.score;

import com.kroy.game.MyGdxGame;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ScoreRanks {
    final MyGdxGame game;
    private ArrayList<Score> ScoresList;
    private Score newestScore;

    public ScoreRanks(final MyGdxGame game){
        this.game = game;
        this.newestScore = null;
        String csvFileName = "ScoreData.csv";
        this.ScoresList = new ArrayList<>();
        ReadFile(csvFileName);

    }
    private void ReadFile(String csvFileName){
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
            TestScores(csvFileName);
        } catch(IOException e){
            ;
        }
    }
    private void WriteFile(String csvFileName){
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

    private void TestScores(String csvFileName){
        for (Integer i = 0; i < 20; i++){
            String tempString = "test" + i.toString();
            Score tempScore = new Score(tempString, i + 1, i + 1);
            this.ScoresList.add(tempScore);
        }
        WriteFile(csvFileName);
    }

    private void InsertScore(String newName, Integer newScore, Boolean fromFile){
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
            }
        } else {
            int numScores = this.ScoresList.size();
            this.newestScore = new Score(newName, newScore, numScores + 1);
            this.ScoresList.add(numScores, this.newestScore);
        }
    }

    public ArrayList SelectScores(String scoreTypes){
		/* score types: "TopTen", "TopAndNew", "AllInst"
		TopTen - Top ten overall scores,
		TopAndNew - Top 8 eight scores + new newest added score + next score above newest score
		AllInst** - Top 10 scores for a given name, not implemented
		 */
        ArrayList<Score> outputScore = new ArrayList<>(10);
        if (scoreTypes == "TopTen"){
            for (int i = 0; i < 10; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } return outputScore;
        } else if(scoreTypes == "TopAndNew"){
            if (GetRank(this.newestScore) < 11){
                return SelectScores("TopTen");
            }
            for (int i = 0; i < 8; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } int rank = GetRank(this.newestScore);
            outputScore.add(this.ScoresList.get(rank-1));
            outputScore.add(this.ScoresList.get(rank));
            return outputScore;
        }
        return outputScore;
    }
    private Integer GetRank(Score ScoreToFind){
        return this.ScoresList.indexOf(ScoreToFind);
    }
}