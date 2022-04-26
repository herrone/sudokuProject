import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    HashMap<Integer, ArrayList<Integer> > turnList;

    public Game() {

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
    public static Game finishAndWipeGame(Game finito) throws IOException {
        ArrayList<Game> myGames = getGames();
        finito.setPartialBoard(finito.turnList.get(0));
        finito.setTurns(0);
        finito.setPlayer("");
        printGames(myGames, finito);
        return finito;
    }

    public Game(int timerMinutes, String player, ArrayList<Integer> fullBoard, ArrayList<Integer> partialBoard, int turns, int size, int difficulty, HashMap<Integer, ArrayList<Integer>> turnList) {
        this.id = id;
        this.turnList = turnList;
        this.player = player;
        this.fullBoard = fullBoard;
        this.partialBoard = partialBoard;
        this.turns = turns;
        this.size = size;
        this.difficulty = difficulty;
    }

    public static void printGames(ArrayList<Game> games, Game updatedGame) throws IOException {

        Database.writeToFile(games, updatedGame);

    }
    public static ArrayList<Game> getOldGames() throws IOException {

        ArrayList<Game> myGames = Database.readFromFile();
        ArrayList<Game> oldGames = new ArrayList<>();

        for (Game g: myGames) {
            if (g.turns != 0) {
                oldGames.add(g);
            }
        }
        return oldGames;
    }
    public static ArrayList<Game> getGames() throws IOException {

        ArrayList<Game> myGames = Database.readFromFile();
        return myGames;
    }

    public static ArrayList<Game> getNewGames() throws IOException {
        ArrayList<Game> myGames = Database.readFromFile();
        ArrayList<Game> newGames = new ArrayList<>();
        for (Game g: myGames) {
            if (g.turns == 0) {
                newGames.add(g);
            }
        }
        return myGames;
    }
    public static Game playGame(Game game) {

        return game;
    }
    public Game getHint() {
        ArrayList<Integer> copied = new ArrayList<>();
        for (int a: this.getPartialBoard()) {
            copied.add(a);
        }
        System.out.println("Get a hint? Okay! I will complete a number for you");
        int partial = 0;
        int full = 0;
        int index = 0;
        for (int i = 0; i<this.getFullBoard().size(); i++) {
            full = this.getFullBoard().get(i);
            partial = this.getPartialBoard().get(i);
            if (full != partial) {
                index = i;
                copied.set(index, full);
                break;
            }
        }
        System.out.println("I changed a " + partial + " to a " + full + ", you're welcome!");
        this.setPartialBoard(copied);
        return this;
    }

    public void checkBoard() throws IOException {
        Boolean allRight = true;
        int wrongNumbers = 0;
        for (int i = 0; i<this.fullBoard.size(); i++) {
            if (this.fullBoard.get(i) != this.partialBoard.get(i)) {
                allRight = false;
                wrongNumbers += 1;
            }
        }
        if (allRight) {
            System.out.println("Well done! You've done it!");
         Game.finishAndWipeGame(this);
            printGames(getGames(), this);
        } else {
            System.out.println("I'm afraid that's not quite right. You have " + wrongNumbers + " numbers to correct!");
        }
    }
    public void quitGame() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Okay, quitting!");
        System.out.println("Thanks for playing " + this.player + "!");
        printGames(getGames(), this);

    }

    public Game addNumber() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean askAgainRow = true;
        boolean askAgainCol = true;
        boolean askAgainVal = true;
        int row = 0;
        int col = 0;
        int val = 0;
        while (askAgainRow == true) {
            System.out.println("Which row?");
            row = Integer.parseInt(reader.readLine());
            if (row<= this.size) {
                askAgainRow = false;
            } else {
                System.out.println("The number cannot be larger than the width of the board! ");

            }
        }
        while (askAgainCol == true) {
            System.out.println("Which column?");
            col = Integer.parseInt(reader.readLine());
            if (col<= this.size) {
                askAgainCol = false;
            } else {
                System.out.println("The number cannot be larger than the width of the board! ");

            }
        }

        while (askAgainVal == true) {
            System.out.println("Which value?");
            val = Integer.parseInt(reader.readLine());
            if (val<= this.size) {
                askAgainVal = false;
            } else {
                System.out.println("The number cannot be larger than the width of the board! ");

            }
        }
        ArrayList<Integer> myCopiedList = new ArrayList<>();
        for (int a: this.partialBoard) {
            myCopiedList.add(a);
        }
        myCopiedList = Game.addNumberToBoard(myCopiedList, row, col, val, this.size);
        int myTurnCounter = this.getTurns();
        HashMap<Integer, ArrayList<Integer>> myturns = this.getTurnList();
        this.setPartialBoard(myCopiedList);
        myturns.put(myTurnCounter, this.getPartialBoard());
        myTurnCounter += 1;
        this.setTurnList(myturns);
        this.setTurns(myTurnCounter);
        System.out.println(myturns.values());
        return this;
    }

    public Game undo() {

        this.setTurns(this.getTurns() - 1);
        HashMap<Integer, ArrayList<Integer>> myTurnList = this.getTurnList();
        ArrayList<Integer> undoneBoard = myTurnList.get(this.getTurns() - 1);
        if (undoneBoard == null) {
            System.out.println("that's as far back as I can go ");
        } else {
            this.setPartialBoard(undoneBoard);
        }
        return this;
    }

    public void replayGame() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Would you like to replay step-by-step from here (h)\n, or watch the game from the start (s)? ");
        String whereFrom = reader.readLine();
        if (whereFrom.equals("h")) {
            int howMany = this.turns;
            System.out.println(howMany);
            System.out.println(this.turnList.size());
            while (howMany >= 1) {
                System.out.println("Board from turn " + howMany + ":");
                ArrayList<Integer> boardToShow = this.turnList.get(howMany - 1);
                System.out.println(displayBoard(boardToShow, this.size));
                howMany -= 1;
                System.out.println("Would you like to see the next one? (c to continue)");
                String continueReply = reader.readLine();
                if (!continueReply.equals("c")) {
                    break;
                }
            }

        } else if (whereFrom.equals("s")) {

            int howMany = this.turns;
            int i = 1;
            while (i<= howMany) {

                System.out.println("Board from turn " + i + ":");
                ArrayList<Integer> boardToShow = this.turnList.get(i - 1);
                System.out.print(displayBoard(boardToShow, this.size));
                i += 1;
                System.out.println("Would you like to see the next one? (c to continue)");
                String continueReply = reader.readLine();
                if (!continueReply.equals("c")) {
                    break;
                }
            }
        }

    }
    public static ArrayList<Integer> addNumberToBoard(ArrayList<Integer> mine, int row, int col, int val, int size) {
        int index = ((row - 1) * size) + col - 1;

        mine.set(index, val);

        return mine;
    }
    public static String displayBoard(ArrayList<Integer> board, int size) {

        String display = "";
        int i = 0;
        for (int a: board) {
            if (i % size == 0) {
                display += "\n";
            }
            if (a == 0) {
                display += "?  ";

            } else {
                display += a + "  ";
            }
            i++;
        }
        return display;
    }
    public void playGame() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> myBoard = new ArrayList<>();
        myBoard = this.getPartialBoard();
        int myTurnCounter = 0;
        HashMap<Integer, ArrayList<Integer>> myturns = new HashMap<>();
        myturns.put(myTurnCounter, myBoard);
        this.setTurnList(myturns);
        System.out.println("Welcome to the game " + this.player + "\nThe starting board:");
        System.out.println("The size is " + this.size);
        String reply = "";

        while (!reply.equals("q")) {

            while (!reply.equals("q")) {
                System.out.println("Current board:");
                System.out.println(Game.displayBoard(this.getPartialBoard(), this.getSize()));

                System.out.println("Would you like to: Quit (q),\n Save (s),\n Add (q),\n Undo (u),\n Replay (r),\n Get a hint (h),\n Give-up (g),\n or Check your board (c)?");
                reply = reader.readLine();
                boolean askAgain = false;

                if (reply.equals("a")) {

                    this.addNumber();

                } else if (reply.equals("g")) {

                    System.out.println("That's a shame :( Are you sure? (Y/N)");
                    String yesOrNo = reader.readLine();
                    if (yesOrNo == "Y") {
                        quitGame();
                    }

                } else if (reply.equals("h")) {

                    getHint();

                } else if (reply.equals("u")) {

                    undo();

                } else if (reply.equals("q")) {
                    System.out.println("That's a shame :( Are you sure? (Y/N)");
                    String yesOrNo = reader.readLine();
                    if (yesOrNo == "Y") {
                        quitGame();
                        break;
                    }

                } else if (reply.equals("r")) {

                    replayGame();
                    break;

                } else if (reply.equals("s")) {
                    ArrayList<Game> myGames = getGames();
                    Game.printGames(myGames, this);
                    System.out.println("Okay, saved!");
                } else if (reply.equals("c")) {
                    checkBoard();
                } else {
                    System.out.println("That isn't an option, please go again");
                }

            }
        }
    }

}