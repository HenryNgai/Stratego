package com.CSE308.Stratego.model;

import com.CSE308.Stratego.model.dao.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class Game {

    //boundaries for rivers and player zone
    // [minx, miny, maxx, maxy]
    private final int RIVER1[] = {2,4,4,6};
    private final int RIVER2[] = {6,4,8,6};
    private final int PLAYER_ZONE_LIMIT = 3;

    private Player user;
    private Player ai;

    private ArrayList<BoardPiece> userPieces;
    private ArrayList<BoardPiece> aiPieces;

    private ArrayList<BoardPiece> userGraveyard;
    private ArrayList<BoardPiece> uiGraveyard;

    private GameBoard board;
    private Date startTime;
    private Date endTime;
    private int gameId;

    private String userName;

    public Game(String userName, int gameId){

        user = new Player("Basim", "Red");
        ai = new Player("Opponent", "Blue");

        userPieces = new ArrayList<BoardPiece>();
        aiPieces = new ArrayList<BoardPiece>();
        userGraveyard = new ArrayList<BoardPiece>();
        uiGraveyard = new ArrayList<BoardPiece>();

        board = new GameBoard();
        startTime = new Date();
        this.gameId = gameId;
        this.userName = userName;

        initPieces();
    }

    private void initPieces(){
        userPieces.add(new BoardPiece("Spy", user));
        aiPieces.add(new BoardPiece("Spy", ai));
        for(int i=0;i<8;i++){
            userPieces.add(new BoardPiece("Scout",user));
            aiPieces.add(new BoardPiece("Scout", ai));
        }
        for(int i=0; i<5; i++){
            userPieces.add(new BoardPiece("Miner",user));
            aiPieces.add(new BoardPiece("Miner", ai));
        }
        for(int i=0; i<4; i++){
            userPieces.add(new BoardPiece("Sergeant",user));
            aiPieces.add(new BoardPiece("Sergeant", ai));
        }
        for(int i=0; i<4; i++){
            userPieces.add(new BoardPiece("Lieutenant",user));
            aiPieces.add(new BoardPiece("Lieutenant", ai));
        }
        for(int i=0; i<4; i++){
            userPieces.add(new BoardPiece("Captain",user));
            aiPieces.add(new BoardPiece("Captain", ai));
        }
        for(int i=0; i<3; i++){
            userPieces.add(new BoardPiece("Major",user));
            aiPieces.add(new BoardPiece("Major", ai));
        }
        for(int i=0; i<2; i++){
            userPieces.add(new BoardPiece("Colonel",user));
            aiPieces.add(new BoardPiece("Colonel", ai));
        }
        for(int i=0; i<1; i++){
            userPieces.add(new BoardPiece("General",user));
            aiPieces.add(new BoardPiece("General", ai));
        }
        for(int i=0; i<1; i++){
            userPieces.add(new BoardPiece("Marshall",user));
            aiPieces.add(new BoardPiece("Marshall", ai));
        }
        for(int i=0;i<6;i++){
            userPieces.add(new BoardPiece("Bomb", user));
            aiPieces.add(new BoardPiece("Bomb", ai));
        }
        userPieces.add(new BoardPiece("Flag", user));
        aiPieces.add(new BoardPiece("Flag", ai));


    }

    public BoardPiece getPieceFromBoard(int i, int j){
        return board.getBoard()[i][j];
    }

    public boolean addPieceFromBank(String name, int posX, int posY){
        for(BoardPiece p: userPieces){
            if(p.getName().equals(name)){
                if(!checkValidMove(p, posX, posY)) return false;
                if(board.addPiece(p, posX, posY)) {
                    userPieces.remove(p);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean movePieceOnBoard(int x, int y, int newX, int newY){
        BoardPiece piece = board.getBoard()[x][y];
        // Checks if there is a piece at source
        if(piece != null){
            // checks if there is a piece at destination
            if(!checkValidMove(piece, newX,newY)) return false;
            BoardPiece destination = board.getBoard()[newX][newY];
            if(destination != null){
                interact(piece, destination);
                return true;
            }else {
                if (movePiece(piece, newX, newY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean movePiece(BoardPiece piece, int newX, int newY){
        int oldX = piece.getxPos();
        int oldY = piece.getyPos();
        if(board.addPiece(piece, newX, newY)){
            board.removePiece(oldX, oldY);
            return true;
        }
        return false;
    }


    //check if the move is valid
    private boolean checkValidMove(BoardPiece piece, int newX, int newY){

        //if we are adding from bank
        if(userPieces.contains(piece)){
            //check player side boundary
            if(newY > PLAYER_ZONE_LIMIT) return false;
            if(board.getBoard()[newX][newY] != null) return false;
            return true;
        }
        //if we are moving a piece on the board
        //if a flag or bomb
        if(!piece.isMoveable()) return false;

        int currX = piece.getxPos();
        int currY = piece.getyPos();
        if(piece.isMoveMultiple()){
            //scout
            if(newX != currX && newY != currY ) return false;
            if(newX == currX && newY == currY ) return false;
            if(newX != currX){
                for(int i=currX+1; i<=newX; i++){
                    if(!isSpaceAvailable(i, currY)) return false;
                }
                return true;
            }else{
                for(int i=currY+1; i<=newY; i++){
                    if(!isSpaceAvailable(currX, i)) return false;
                }
                return true;
            }
        }
        //checks if moving one step forward/backward/side-to-side
        double dist = Math.sqrt((((double)newX-(double)currX)*((double)newX-(double)currX))+(((double)newY-(double)currY)*((double)newY-(double)currY)));
        if(dist != 1.0)return false;
        //checks if space is available
        return isSpaceAvailable(newX, newY);

    }

    private boolean isSpaceAvailable(int newX, int newY){
        //check for rivers
        if(newX > RIVER1[0] && newX < RIVER1[2] && newY > RIVER1[1] && newY < RIVER1[3]){
            return false;
        }
        if(newX > RIVER2[0] && newX < RIVER2[2] && newY > RIVER2[1] && newY < RIVER1[3]){
            return false;
        }
        //check for piece
        BoardPiece piece = board.getBoard()[newX][newY];
        if(piece != null){
            if(!piece.getPlayer().getName().equals("Opponent")) return false;
        }
        return true;
    }

    private void interact(BoardPiece attacker, BoardPiece defender){
        //do battle/bomb/flag/etc

    }


    //Getters and setters
    public ArrayList<BoardPiece> getUserPieces() {
        return userPieces;
    }

    public void setUserPieces(ArrayList<BoardPiece> userPieces) {
        this.userPieces = userPieces;
    }

    public ArrayList<BoardPiece> getAiPieces() {
        return aiPieces;
    }

    public void setAiPieces(ArrayList<BoardPiece> aiPieces) {
        this.aiPieces = aiPieces;
    }

    public ArrayList<BoardPiece> getUserGraveyard() {
        return userGraveyard;
    }

    public void setUserGraveyard(ArrayList<BoardPiece> userGraveyard) {
        this.userGraveyard = userGraveyard;
    }

    public ArrayList<BoardPiece> getUiGraveyard() {
        return uiGraveyard;
    }

    public void setUiGraveyard(ArrayList<BoardPiece> uiGraveyard) {
        this.uiGraveyard = uiGraveyard;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Player getUser() {
        return user;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public Player getAi() {
        return ai;
    }

    public void setAi(Player ai) {
        this.ai = ai;
    }
}
