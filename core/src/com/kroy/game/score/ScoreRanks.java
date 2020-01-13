//import com.kroy.game.MyGdxGame;
//import com.kroy.game.score.Score;
//
//import java.util.List;
//import java.util.Map;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.LineNumberReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class ScoreRanks {
//    final MyGdxGame game;
//    private Map<Integer, Score> scoreMap;
//    private ArrayList<Integer> IDsList;
//    private Integer newestID;
//
//    public ScoreRanks(final MyGdxGame game){
//        this.game = game;
//        this.newestID = null;
//        String csvFileName = "ScoreData.csv";
//        ReadFile(csvFileName);
//    }
//    private Map ReadFile(String csvFileName){
//        FileReader fileCsv = new FileReader(csvFileName);
//        int lineNumber = FileLength(fileCsv);
//        this.IDsList = new ArrayList<>();
//        this.scoreMap = new HashMap<>();
//        BufferedReader bufferedCsv = new BufferedReader(fileCsv);
//        String row;
//        while ((row = bufferedCsv.readLine()) != null){
//            String[] lineData = row.split(",");
//            String score = lineData[1].strip();
//            Integer scoreInt = Integer.parseInt(score);
//            InsertScore(lineData[0], scoreInt);
//        }
//        fileCsv.close();
//        bufferedCsv.close();
//    }
//
//    private int FileLength(FileReader fileCsv){
//        int lineNumber = 0;
//        LineNumberReader lineCsv = new LineNumberReader(fileCsv);
//        while (lineCsv.readLine() != null){
//            lineNumber++;
//        }
//        lineCsv.close();
//        return lineNumber;
//    }
//    private void WriteFile(String csvFileName){
//        FileWriter csvWriter = new FileWriter(csvFileName);
//        for (int i = 0; i < this.IDsList.size(); i++){
//            String name = this.IDsList.get(i).toString();
//            csvWriter.append(name);
//            csvWriter.append(",");
//            csvWriter.append(this.scoreMap.get(name).getValueAsString());
//            csvWriter.append("/n");
//        }
//        csvWriter.flush();
//        csvWriter.close();
//    }
//
//    private void InsertScore(String newName, Integer newScore){
//        if (this.IDsList.size() == 1){
//            this.IDsList.add(0, 1);
//            this.scoreMap.put(0, new Score(newName, newScore));
//            this.newestID = 1;
//        } else {
//            int numScores = this.IDsList.size();
//            for (int i = 0; i < numScores; i++){
//                if (this.scoreMap.get(this.IDsList.get(i)).getValue() < newScore) {
//                    this.IDsList.add(i, (numScores++));
//                    this.scoreMap.put(numScores++, new Score(newName, newScore));
//                    this.newestID = numScores++;
//                }
//            }
//        }
//    }
//
//    private List SelectScores(String scoreTypes){
//		 score types: "TopTen", "TopAndNew", "AllInst"
//		TopTen - Top ten overall scores,
//		TopAndNew - Top 8 eight scores + new newest added score + next score above newest score
//		AllInst** - Top 10 scores for a given name, not implemented
//
//        List<String> outputIDs = new ArrayList<String>(10);
//        if (scoreTypes == "TopTen"){
//            for (int i = 0; i < 10; i++){
//                outputIDs.add(i, this.IDsList.get(i).toString());
//            } return outputIDs;
//        } else if(scoreTypes == "TopAndNew"){
//            if (GetRank(this.newestID < 11)){
//                return SelectScores("TopTen");
//            }
//            for (int i = 0; i < 8; i++){
//                outputIDs.add(i, this.IDsList.get(i).toString());
//            } int rank = GetRank(this.newestID);
//            outputIDs.add(9, this.IDsList(rank-1).toString());
//            outputIDs.add(10, this.IDsList(rank).toString());
//            return outputIDs;
//        }
//    }
//    private Integer GetRank(Integer ID){
//        return this.IDsList.indexOf(ID);
//    }
//    private Score GetScore(Integer ID){
//        return this.scoreMap.get(ID);
//    }
//}
//*/