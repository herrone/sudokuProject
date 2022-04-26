import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    int id;
    int timerMinutes;
    String player;
    ArrayList<Integer> fullBoard;
    ArrayList<Integer> partialBoard;
    int turns;
    int size;
    int difficulty;
    HashMap< Integer, ArrayList<Integer> > turnList;
    //static Database db = new Database();

    public Game(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimerMinutes() {
        return timerMinutes;
    }

    public void setTimerMinutes(int timerMinutes) {
        this.timerMinutes = timerMinutes;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public ArrayList<Integer> getFullBoard() {
        return fullBoard;
    }

    public void setFullBoard(ArrayList<Integer> fullBoard) {
        this.fullBoard = fullBoard;
    }

    public ArrayList<Integer> getPartialBoard() {
        return partialBoard;
    }

    public void setPartialBoard(ArrayList<Integer> partialBoard) {
        this.partialBoard = partialBoard;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public HashMap<Integer, ArrayList<Integer>> getTurnList() {
        return turnList;
    }

    public void setTurnList(HashMap<Integer, ArrayList<Integer>> turnList) {
        this.turnList = turnList;
    }

    public Game(int timerMinutes, String player, ArrayList<Integer> fullBoard, ArrayList<Integer> partialBoard, int turns, int size, int difficulty, HashMap<Integer, ArrayList<Integer>> turnList){
        this.id = id;
        this.turnList = turnList;
        this.player = player;
        this.fullBoard = fullBoard;
        this.partialBoard = partialBoard;
        this.turns = turns;
        this.size = size;
        this.difficulty = difficulty;
    }

}
