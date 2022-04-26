import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Database {

    public static void writeToFile(ArrayList<Game> games, Game updatedGame) throws IOException {
       int index = 0;
        Boolean found = false;
        if (games.size() > 1) {
            System.out.println(games.size());
            for (Game game: games) {

                if (game.id == updatedGame.id) {

                    found = true;
                    index = games.indexOf(game);

                }
                if (found == true) {
                    games.set(index, updatedGame);
                }

            }
        }
        StringBuilder sb = new StringBuilder("");
        PrintWriter writer = new PrintWriter("games.csv");

        for (Game g: games) {

            String fullBoard = boardToString(g.fullBoard, g.size);
            String partialBoard = boardToString(g.partialBoard, g.size);
            String hashMap = mapToString(g.turnList, g.size);
            sb.append(g.id + "," + g.player + "," + fullBoard + "," + partialBoard + "," + g.turns + "," + g.size + "," + g.difficulty + "," + hashMap + "," + "\n");

        }
        writer.write(sb.toString());
        writer.close();

    }
    public static String boardToString(ArrayList<Integer> board, int size) {

        StringBuilder complete = new StringBuilder();
        for (int a = 0; a<= size * size - 1; a++) {
            complete.append(board.get(a));
            complete.append("/");
            if (a % size == 0) {
                complete.append("=");
            }

        }
        return complete.toString();
    }

    public static String mapToString(HashMap<Integer, ArrayList<Integer>> turnList, int size) {
        String complete = "";
        for (int turnCounter = 1; turnCounter<turnList.size(); turnCounter++) {
            complete += (turnCounter + "%");
            complete += (boardToString(turnList.get(turnCounter), size));

            complete += ("~");

        }
        return complete;
    }
    public static ArrayList<Game> readFromFile() throws FileNotFoundException {

        ArrayList<Game> games = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("games.csv"));) {

            while (scanner.hasNextLine()) {
                Game myGame = new Game();
                String line = scanner.nextLine();
                String[] atts = line.split(",");
                ArrayList<Integer> fullBoard;
                ArrayList<Integer> partialBoard;
                int ident = Integer.parseInt(atts[0]);
                String player = atts[1];
                int sizer = Integer.parseInt(atts[5]);
                fullBoard = stringToSudokuBoard(atts[2], sizer);
                partialBoard = stringToSudokuBoard(atts[3], sizer);
                int turns = Integer.parseInt(atts[4]);
                HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
                if (turns > 0) {
                    hm = stringToHashMapOfTurns(atts[7], sizer);
                } else {

                }
                int difficulty = Integer.parseInt(atts[6]);
                myGame.player = player;
                myGame.id = ident;
                myGame.size = sizer;
                myGame.turns = turns;
                myGame.fullBoard = fullBoard;
                myGame.partialBoard = partialBoard;
                myGame.difficulty = difficulty;
                myGame.turnList = hm;
                games.add(myGame);
            }
        }
        return games;
    }
    public static HashMap<Integer, ArrayList<Integer>> stringToHashMapOfTurns(String allTurns, int size) {

        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
        int turnNumber;
        ArrayList<Integer> board;
        String turnByTurn[] = allTurns.split("~");
        for (String turn: turnByTurn) {
            String[] bitsOfTurn = turn.split("%");
            for (String bitOfTurn: bitsOfTurn) {
                turnNumber = Integer.parseInt(bitsOfTurn[0]);
                board = stringToSudokuBoard(bitsOfTurn[1], size);
                hm.put(turnNumber, board);
            }

        }

        return hm;
    }
    public static ArrayList<Integer> stringToSudokuBoard(String lineFromFile, int size) {

        LinkedList<Integer> listOfNums = new LinkedList<>();
        int num;
        String[] rows = lineFromFile.split("=");
        ArrayList<Integer> board = new ArrayList<Integer> ();
        for (String row: rows) {
            String[] nums = row.split("/");
            for (String n: nums) {
                num = Integer.parseInt(n);
                board.add(num);

            }
        }
        return board;
    }
}