import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
//this has the adding function :) time to try undo
public class Sudoku {

    public static void main(String[] args) throws IOException {

        boolean playGame = true;
        while (playGame = true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Game selected = welcomeChooseGame();
            selected.playGame();
            System.out.println("Would you like to play another game (Y/N)?");
            String yOrN = reader.readLine();
            if (yOrN == "Y" || yOrN == "y") {
                playGame = true;
            } else {
                playGame = false;
                System.out.println("Okay, thanks for playing !");
                break;
            }

        }
    }

    public static Game welcomeChooseGame() throws IOException {
        // ** prep all of the games/grids **

        ArrayList<Game> oldGames = Game.getOldGames();
        ArrayList<Game> newGames = Game.getNewGames();
        Game inPlay = new Game();
        boolean foundAGame = false;
        while (foundAGame == false) {

            System.out.print("Hello, welcome to sudoku!\n");
            System.out.println("What is your name?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String player = reader.readLine();
            System.out.println("Would you like to choose an old game to play, or play a new game? New = 1\n Old = 0\n");
            int oldOrNew = Integer.parseInt(reader.readLine());
            if (oldOrNew == 0) {
                if (oldGames.size() == 0) {
                    System.out.println("I do apologise, you have no old games, you will need to play one first!");
                    //foundAGame = false;
                } else {
                    System.out.println("Here are the games you can choose from ");
                    for (Game g: oldGames) {
                        System.out.println("Id: " + g.id + ", player; " + g.player + ", turns; " + g.turns);
                    }
                    System.out.println("Choose an Id");
                    int idInQuestion = Integer.parseInt(reader.readLine());
                    for (Game g: oldGames) {
                        if (g.id == idInQuestion) {
                            inPlay = g;
                            inPlay.player = player;
                            //    return inPlay;
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
                for (Game g: newGames) {
                    if (g.size == size && g.difficulty == difficulty && foundAGame == false) {
                        foundAGame = true;
                        inPlay = g;
                        inPlay.player = player;

                    }
                }
            }

        }
        return inPlay;
    }

}