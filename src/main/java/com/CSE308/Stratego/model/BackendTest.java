package com.CSE308.Stratego.model;

import com.CSE308.Stratego.model.dao.BoardPieceDao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BackendTest {

    private static Game game;

    public static void main(String[] args){
        game = new Game("chow", 3);

        printBoard();
        boolean done = false;
        Scanner scn = new Scanner(System.in);
        while(!done){
            String input = scn.nextLine();
            if(input.equals("Bomb")){
                int x = scn.nextInt();
                int y = scn.nextInt();
                if(!game.addPieceFromBank("Bomb", x, y)){
                    System.out.println("Invalid placement");
                }
                printBoard();
            }else if(input.equals("q")){
                done = true;
            }else if(input.equals("Move")){
                int x = scn.nextInt();
                int y = scn.nextInt();
                int newX = scn.nextInt();
                int newY = scn.nextInt();
                if(!game.movePieceOnBoard(x,y,newX,newY)){
                    System.out.println("Invalid move");
                }
                printBoard();
            }


        }
    }


    private static void printBoard(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(game.getPieceFromBoard(i, j) == null){
                    System.out.print(0);
                }else{
                    System.out.print(3);
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
