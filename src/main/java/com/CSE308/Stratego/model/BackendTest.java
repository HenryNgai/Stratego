package com.CSE308.Stratego.model;


import java.util.*;

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
                if(!game.makeMove("", x,y,newX,newY, false)){
                    System.out.println("Invalid move");
                }
                printBoard();
            }else if(names.contains(input)){
                System.out.print("X: ");
                int x = scn.nextInt();
                System.out.print("Y: ");
                int y = scn.nextInt();
                if(!game.makeMove(input,0,0, x, y, false)){
                    System.out.println("Invalid placement");
                }
                printBoard();
            }

            if(game.getUserPieces().isEmpty()){
                aiSetup();
            }


        }
    }

    private static void aiSetup(){
        ArrayList<BoardPiece> aiPieces = game.getAiPieces();
        Collections.shuffle(aiPieces);
        int loop = aiPieces.size();
        for(int i=0;i<loop;i++){
            if(i==20){
                System.out.println("");
            }
            int x = i/10;
            int y= i%10;
            game.makeMove(aiPieces.get(0).getName(),0,0,x,y,true);
        }
        printBoard();

    }






    private static void printBoard(){
        System.out.println("  0123456789");
        for(int i=0;i<10;i++){
            System.out.print(i +"|");
            for(int j=0;j<10;j++){
                BoardPiece piece = game.getPieceFromBoard(i, j);
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
