import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
//this has the adding function :) time to try undo
public class Sudoku {

    public static void main(String[] args) throws IOException {


        mainStartMenu();
    }
        public static Game mainStartMenu() throws IOException {
            // ** prep all of the games/grids **
            File file = new File("games.csv");
            file.createNewFile();
            ArrayList<Game> myGames = readFromFile();
            ArrayList<Game> newGames = new ArrayList<>();
            ArrayList<Game> oldGames = new ArrayList<>();

            Game inPlay = new Game();
            for (Game g : myGames
            ) {
                if (g.turns == 0) {
                    newGames.add(g);
                } else {
                    oldGames.add(g);
                }
            }
            boolean foundAGame = false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean playAgain = true;

            System.out.print("Hello, welcome to sudoku!\n");
            System.out.println("What is your name?");
            String player = reader.readLine();
            while (foundAGame == false) {
                System.out.println("Would you like to choose an old game to play, or play a new game? New = 1\n Old = 0\n");
                int oldOrNew = Integer.parseInt(reader.readLine());
                if (oldOrNew == 0) {
                    if (oldGames.size() == 0) {
                        System.out.println("I do apologise, you have no old games, you will need to play one first!");
                        foundAGame = false;
                    } else {
                        System.out.println("Here are the games you can choose from ");
                        for (Game g : oldGames) {
                            System.out.println("Id: " + g.id + ", player; " + g.player + ", turns; " + g.turns);
                        }
                        System.out.println("Choose an Id");
                        int idInQuestion = Integer.parseInt(reader.readLine());
                        for (Game g : oldGames
                        ) {
                            if (g.id == idInQuestion) {
                                inPlay = g;
                                foundAGame = true;
                            }

                        }
                    }
                } else if (oldOrNew == 1) {
                    System.out.println("Lovely, a new game.\n Please choose your difficulty from the options below!\n Easy (1) / Med (2)/ Hard (3)");
                    int difficulty = Integer.parseInt(reader.readLine());
                    System.out.println("Excellent, and now  which size should I make the board ?\n 5x5 = 5\n 6x6 = 6\n 7x7 = 7\n8x8 = 8\n");
                    int size = Integer.parseInt(reader.readLine());
                    System.out.println(" Fantastic, loading game!");
                    for (Game g : newGames
                    ) {
                        if (g.size == size && g.difficulty == difficulty && foundAGame == false) {
                            foundAGame = true;
                            inPlay = g;
                        }
                    }
                }

            }
l
            ArrayList<Integer> myBoard = new ArrayList<>();

            myBoard = inPlay.getPartialBoard();
            inPlay.player = player;
            int myTurnCounter = 0;
            //boolean playAgain = true;

            HashMap<Integer, ArrayList<Integer>> myturns = new HashMap<>();
            myturns.put(myTurnCounter, myBoard);
            inPlay.setTurnList(myturns);

            System.out.println("Welcome to the game " + player + "\nThe starting board:");
            System.out.println("The size is " + inPlay.size);
            String reply = "";
            while (!reply.equals("q")) {

                System.out.println("Current board:");
                System.out.println(displayBoard(inPlay.getPartialBoard(), inPlay.getSize()));
                System.out.println("quit, add, undo, replay, get a hint, give-up or check the board(q/a/u/r/h/g/c)?");
                reply = reader.readLine();
                int roww = 0;
                int coll = 0;
                int vall = 0;
                boolean askAgain = false;
                if (reply.equals("a")) {

                    System.out.println("row?");

                    roww = Integer.parseInt(reader.readLine());

                    System.out.println("col?");

                    coll = Integer.parseInt(reader.readLine());


                    ArrayList<Integer> myCopiedList = new ArrayList<>();
                    for (int a : inPlay.partialBoard
                    ) {
                        myCopiedList.add(a);
                    }
                    System.out.println("val?");

                    vall = Integer.parseInt(reader.readLine());
                    if (vall > inPlay.size) {
                        System.out.println("The number cannot be larger than the width of the board! ");
                        askAgain = true;
                    }
                    while (askAgain == true) {
                        System.out.println("val?");
                        vall = Integer.parseInt(reader.readLine());
                        if (vall <= inPlay.size) {

                            askAgain = false;
                        } else {
                            System.out.println("The number cannot be larger than the width of the board! ");

                        }

                    }
                    myCopiedList = addNumber(myCopiedList, roww, coll, vall, inPlay.size);
                    myTurnCounter = inPlay.getTurns();
                    myturns = inPlay.getTurnList();
                    inPlay.setPartialBoard(myCopiedList);
                    myTurnCounter += 1;
                    myturns.put(myTurnCounter, myCopiedList);
                    inPlay.setTurnList(myturns);
                    inPlay.setTurns(myTurnCounter);
                    System.out.println(myturns.values());
                    System.out.println("Done, your new board:");
                    // System.out.println(displayBoard(inPlay.getPartialBoard(), inPlay.getSize()));

                } else if (reply.equals("g")) {
                    System.out.println("That's a shame :( Would you like to play another game? (Y/N)");
                    String wouldYouPlayAgain = reader.readLine();
                    if (wouldYouPlayAgain.equals("N")) {
                        playAgain = false;
                        System.out.println("Okay, thanks for playing " + player + "!");
                        break;
                    } else {
                        System.out.println("Okay!");
                        foundAGame = false;
                    }
                } else if (reply.equals("h")) {
                    ArrayList<Integer> copied = new ArrayList<>();
                    for (int a : inPlay.getPartialBoard()) {
                        copied.add(a);
                    }
                    System.out.println("Get a hint? Okay! I will complete a number for you");
                    int partial = 0;
                    int full = 0;
                    int index = 0;
                    for (int i = 0; i < inPlay.getFullBoard().size(); i++) {
                        full = inPlay.getFullBoard().get(i);
                        partial = inPlay.getPartialBoard().get(i);
                        if (full != partial) {
                            index = i;
                            copied.set(index, full);
                            break;
                        }
                    }
                    System.out.println("I changed a " + partial + " to a " + full + ", you're welcome!");
                    inPlay.setPartialBoard(copied);
                } else if (reply.equals("u")) {
                    //  inPlay = undo(inPlay);
//                System.out.println("turns before: " + inPlay.getTurns());
                    //  inPlay.turns = inPlay.getTurns() - 1;
                    inPlay.setTurns(inPlay.getTurns() - 1);

//       System.out.println("turns after: " +inPlay.getTurns());
                    HashMap<Integer, ArrayList<Integer>> myTurnList = inPlay.getTurnList();
                    ArrayList<Integer> undoneBoard = myTurnList.get(inPlay.getTurns());
                    inPlay.setPartialBoard(undoneBoard);

                    //  System.out.println(displayBoard(inPlay.getPartialBoard() , inPlay.getSize()));


                } else if (reply.equals("q")) {
                    System.out.println("Okay, quitting!");
                    System.out.println("Would you like to play a different or new game? (Y/N)");
                    String wouldYouPlayAgain = reader.readLine();
                    if (wouldYouPlayAgain.equals("N")) {
                        playAgain = false;
                        System.out.println("Okay, thanks for playing " + player + "!");
                        break;
                    } else {
                        System.out.println("Okay!");
                        // reply = "not q";

                    }
                } else if (reply.equals("r")) {
                    System.out.println("Would you like to replay step-by-step from here (h)\n, or watch the game from the start (s)? ");
                    String whereFrom = reader.readLine();
                    if (whereFrom.equals("h")) {
                        int howMany = inPlay.turns;
                        System.out.println(howMany);
                        System.out.println(inPlay.turnList.size());
                        while (howMany >= 1) {
                            System.out.println("Board from turn " + howMany + ":");
                            ArrayList<Integer> boardToShow = inPlay.turnList.get(howMany - 1);
                            System.out.println(displayBoard(boardToShow, inPlay.size));
                            howMany -= 1;
                            System.out.println("Would you like to see the next one? (c to continue)");
                            String continueReply = reader.readLine();
                            if (!continueReply.equals("c")) {
                                break;
                            }
                        }


                        //   inPlay.setPartialBoard(undoneBoard);
                        // displayBoard()
                    } else if (whereFrom.equals("s")) {

                        int howMany = inPlay.turns;
                        int i = 1;
                        while (i <= howMany) {

                            System.out.println("Board from turn " + i + ":");
                            ArrayList<Integer> boardToShow = inPlay.turnList.get(i - 1);
                            System.out.print(displayBoard(boardToShow, inPlay.size));
                            i += 1;
                            System.out.println("Would you like to see the next one? (c to continue)");
                            String continueReply = reader.readLine();
                            if (!continueReply.equals("c")) {
                                break;
                            }

                        }
                    }
                } else if (reply.equals("s")) {
                    gamesToFile(myGames, inPlay);
                    System.out.println("Okay, saved!");
                } else if (reply.equals("c")) {
                    Boolean allRight = true;
                    int wrongNumbers = 0;
                    for (int i = 0; i < inPlay.fullBoard.size(); i++) {
                        if (inPlay.fullBoard.get(i) != inPlay.partialBoard.get(i)) {
                            allRight = false;
                            wrongNumbers += 1;

                        }

                    }
                    if (allRight) {
                        System.out.println("Well done! You've done it!");
                        finishAndWipeGame(inPlay);
                        break;
                    } else {
                        System.out.println("I'm afraid that's not quite right. You have " + wrongNumbers + " numbers to correct!");
                    }
                } else {
                    System.out.println("That isn't an option, please go again");
                }


            }
        }


    public static ArrayList<Integer> addNumber(ArrayList<Integer> mine, int row, int col, int val, int size){
        int index = ((row - 1) * size) + col - 1;

        mine.set(index, val);

        return mine;
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
                // System.out.println(atts[2]);
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
                //scanner.nextLine();
            }
        }
        return games;
    }
        public static ArrayList<Integer> stringToSudokuBoard(String lineFromFile, int size){
            LinkedList<Integer> listOfNums = new LinkedList<>();
            int num;
            String [] rows = lineFromFile.split("=");
            ArrayList<Integer> board = new ArrayList<Integer>();
            for (String row: rows
            ) {
                //  System.out.println(row);
                String [] nums = row.split("/");
                for (String n: nums
                ) {
                    num = Integer.parseInt(n);
                    board.add(num);
                    // System.out.println(n);

                }
            }
            return board;
        }
    public static HashMap<Integer, ArrayList<Integer>> stringToHashMapOfTurns(String allTurns, int size){
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
        int turnNumber;
        ArrayList<Integer> board;
        String turnByTurn[] = allTurns.split("~");
        for (String turn :turnByTurn
        ) {
            String[] bitsOfTurn = turn.split("%");
            for (String bitOfTurn: bitsOfTurn
            ) {
                turnNumber = Integer.parseInt(bitsOfTurn[0]);
                board = stringToSudokuBoard(bitsOfTurn[1], size);
                hm.put(turnNumber, board);
            }

        }

        return hm;
    }
    public static void gamesToFile(ArrayList<Game> games, Game updatedGame) throws IOException {
        //   try (Scanner scanner = new Scanner(new File("/Users/emilyh/IdeaProjects/parser/out/storedGrids.csv"));) {
        int index = 0;
        Boolean found = false;
        if(games.size() > 1) {
            System.out.println(games.size());
            for(Game game : games){

                if (game.id == updatedGame.id) {
                    //  System.out.println("found it");
                    found = true;
                    index = games.indexOf(game);
//            games.remove(index);
                    //games.add(updatedGame);
                }
                if (found == true) {
                    games.set(index, updatedGame);
                }

            }
        }
        StringBuilder sb = new StringBuilder("");
        PrintWriter writer = new PrintWriter("games.csv");

        for (Game g: games
        ) {

            String fullBoard = boardToString(g.fullBoard, g.size);
            String partialBoard = boardToString(g.partialBoard, g.size);
            String hashMap = mapToString(g.turnList, g.size);
            sb.append(g.id + "," + g.player +"," + fullBoard +"," + partialBoard + ","+ g.turns + ","+ g.size +"," + g.difficulty +"," + hashMap + ","+ "\n");


        }
        writer.write(sb.toString());
        writer.close();

    }
    public static void finishAndWipeGame(Game finito) throws IOException {
        ArrayList<Game> myGames = readFromFile();
        finito.setPartialBoard(finito.turnList.get(0));
        finito.setTurns(0);
        finito.setPlayer("");

        gamesToFile(myGames, finito);
    }

    public static String mapToString(HashMap<Integer, ArrayList<Integer>> turnList, int size){
        String complete = "";
        for (int turnCounter = 1; turnCounter < turnList.size(); turnCounter++
        ) {
            complete+=(turnCounter + "%");
            complete+=(boardToString(turnList.get(turnCounter) , size));

            complete+=("~");

        }
        return complete;
    }

    public static String boardToString(ArrayList<Integer> board, int size){
        StringBuilder complete = new StringBuilder();
        for (int a = 0; a<= size*size-1; a++
        ) {
            complete.append(board.get(a));
            complete.append("/");
            if(a % size == 0){
                complete.append("=");
            }

        }


        return complete.toString();
    }
    public static String displayBoard(ArrayList<Integer> board, int size){

        String display = "";
        int i = 0;
        for (int a : board
        ) {
            if(i % size == 0){
                display+="\n";
            }
            if (a == 0) {
                display += "?  ";

            } else {
                display += a + "  " ;
            }
            i++;
        }
        return display;
    }
    }