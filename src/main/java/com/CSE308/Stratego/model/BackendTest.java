package com.CSE308.Stratego.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BackendTest {

    private static Game game;
    private static List<String> names;


    public static void main(String[] args){
        game = new Game("chow", 3);

        String nameArray[] = {"Marshall", "General", "Colonel", "Major", "Captain", "Lieutenant", "Sergeant", "Miner", "Scout", "Spy", "Bomb", "Flag"};
        names = Arrays.asList(nameArray);

        printBoard();
        boolean done = false;
        Scanner scn = new Scanner(System.in);
        while(!done){
            String input = scn.next();
            if(input.equals("q")){
                done = true;
            }else if(input.equals("Move")){
                System.out.print("X: ");
                int x = scn.nextInt();
                System.out.print("Y: ");
                int y = scn.nextInt();
                System.out.print("newY: ");
                int newX = scn.nextInt();
                System.out.print("newY: ");
                int newY = scn.nextInt();
                if(!game.movePieceOnBoard(x,y,newX,newY)){
                    System.out.println("Invalid move");
                }
                printBoard();
            }else if(names.contains(input)){
                System.out.print("X: ");
                int x = scn.nextInt();
                System.out.print("Y: ");
                int y = scn.nextInt();
                if(!game.addPieceFromBank(input, x, y)){
                    System.out.println("Invalid placement");
                }
                printBoard();
            }


        }
    }


    private static void printBoard(){
        System.out.println("  0123456789");
        for(int i=0;i<10;i++){
            System.out.print(i +"|");
            for(int j=0;j<10;j++){
                BoardPiece piece = game.getPieceFromBoard(j, i);
                if(piece == null){
                    System.out.print(0);
                }else if(piece.getName().equals("Bomb")){
                    System.out.print("B");
                }else if(piece.getName().equals("Flag")){
                    System.out.print("F");
                }else if(piece.getName().equals("Marshall")){
                    System.out.print("M");
                }else{
                    System.out.print(piece.getStrength());
                }
            }
            System.out.print("\n");
        }
        ArrayList<BoardPiece> toPrint = game.getUserPieces();
        for(int i=0;i<toPrint.size();i++){
            if(i%18 == 0) System.out.print("\n");
            System.out.print(toPrint.get(i).getName() + " | ");

        }
    }



}
