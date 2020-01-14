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
        ReadFile(csvFileName);
    }
    private void ReadFile(String csvFileName){
        try {
            FileReader fileCsv = new FileReader(csvFileName);
            //int lineNumber = FileLength(fileCsv);
            this.ScoresList = new ArrayList<>();
            BufferedReader bufferedCsv = new BufferedReader(fileCsv);
            String row;
            while ((row = bufferedCsv.readLine()) != null) {
                String[] lineData = row.split(",");
                String score = lineData[1].strip();
                Integer scoreInt = Integer.parseInt(score);
                InsertScore(lineData[0], scoreInt);
            }
            fileCsv.close();
            bufferedCsv.close();
        } catch(FileNotFoundException e) {
            WriteFile(csvFileName);
        } catch(IOException e){
            ;
        }
    }

    private int FileLength(FileReader fileCsv){
        int lineNumber = 0;
        try {
            LineNumberReader lineCsv = new LineNumberReader(fileCsv);
            while (lineCsv.readLine() != null) {
                lineNumber++;
            }
            lineCsv.close();
            return lineNumber;
        }
        catch(IOException e){
            return lineNumber;
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
                csvWriter.append("/n");
            }
            csvWriter.flush();
            csvWriter.close();
        }
        catch(IOException e){
            ;
        }
    }

    private void InsertScore(String newName, Integer newScore){
        if (this.ScoresList.size() == 1){
            this.newestScore = new Score(newName, newScore, 1);
            this.ScoresList.add(0, this.newestScore);
        } else {
            int numScores = this.ScoresList.size();
            for (int i = 0; i < numScores; i++){
                if (this.ScoresList.get(i).getValue() < newScore) {
                    this.newestScore = new Score(newName, newScore, numScores++);
                    this.ScoresList.add(i, this.newestScore);
                }
            }
        }
    }

    private List SelectScores(String scoreTypes){
		/* score types: "TopTen", "TopAndNew", "AllInst"
		TopTen - Top ten overall scores,
		TopAndNew - Top 8 eight scores + new newest added score + next score above newest score
		AllInst** - Top 10 scores for a given name, not implemented
		 */
        List<Score> outputScore = new ArrayList<>(10);
        if (scoreTypes == "TopTen"){
            for (int i = 0; i < 10; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } return outputScore;
        } else if(scoreTypes == "TopAndNew"){
            if (GetRank(this.newestScore.getIDint()) < 11){
                return SelectScores("TopTen");
            }
            for (int i = 0; i < 8; i++){
                outputScore.add(i, this.ScoresList.get(i));
            } int rank = GetRank(this.newestScore.getIDint());
            outputScore.add(this.ScoresList.get(rank-1));
            outputScore.add(this.ScoresList.get(rank));
            return outputScore;
        }
        return outputScore;
    }
    private Integer GetRank(Integer ID){
        return this.ScoresList.indexOf(ID);
    }
}