package com.CSE308.Stratego.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Game {

    //boundaries for rivers and player zone
    // [minx, miny, maxx, maxy]
    private final int RIVER1[] = {4,2,6,4};
    private final int RIVER2[] = {4,6,6,8};
    private final int PLAYER_ZONE_LIMIT = 6;

    private Player user;
    private Player ai;

    private ArrayList<BoardPiece> userPieces;
    private ArrayList<BoardPiece> aiPieces;

    private ArrayList<BoardPiece> userGraveyard;
    private ArrayList<BoardPiece> aiGraveyard;

    private GameBoard board;
    private Date startTime;
    private Date endTime;
    private int gameId;
    private boolean gamewon;
    private boolean gamelost;
    private int ai_charsLost;
    private int user_charsLost;

    private String userName;

    private boolean setUpPhase;
    private boolean battlePhase;

    public Game(String userName, int gameId){

        user = new Player(userName, "Blue");
        ai = new Player("Opponent", "Red");

        userPieces = new ArrayList<BoardPiece>();
        aiPieces = new ArrayList<BoardPiece>();
        userGraveyard = new ArrayList<BoardPiece>();
        aiGraveyard = new ArrayList<BoardPiece>();

        board = new GameBoard();
        startTime = new Date();
        this.gameId = gameId + 1;
        this.userName = userName;

        setUpPhase = true;
        battlePhase = false;
        gamewon = false;
        gamelost = false;

        initPieces();

        ai_charsLost = 0;
        user_charsLost = 0;
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


    public String makeMove(String name, int x, int y, int newX, int newY, boolean isAi){
        if(setUpPhase){
            if(x == -1) {
                if (addPieceFromBank(name, newX, newY, isAi)) {
                    if (userPieces.isEmpty() && aiPieces.isEmpty()) {
                        setUpPhase = false;
                        battlePhase = true;
                    }
                    return "True -1 -1 -1 -1";
                }
            }else{
                return movePieceOnBoard(x, y, newX, newY) + " -1 -1 -1 -1";
            }
            return "False -1 -1 -1 -1";
        }if(battlePhase){
            String result = movePieceOnBoard(x,y,newX,newY);
            if(!result.equals("False")){
                //TODO make ai return x and y and newx and newy
                String aiCoordinates = aiMovePiece(ai);
                //method to check if game over
                return result + " " + aiCoordinates;
            }
            return "False -1 -1 -1 -1";
        }
        return "False -1 -1 -1 -1";
    }

    public String aiMovePiece(Player player){
        //ai moves here
        if(gamewon) return "";
        String response = "";

        ArrayList<BoardPiece> piecesOnBoard = new ArrayList<BoardPiece>();
        for(int i=0;i<100;i++){
            int x = i/10;
            int y = i%10;
            BoardPiece piece = getPieceFromBoard(x, y);
            if(piece != null && !piece.isMoveable()) continue;
            if(piece != null && piece.getPlayer().getName().equals(player.getName())){
                piecesOnBoard.add(piece);
            }
        }
        Collections.shuffle(piecesOnBoard);
        //aggressive AI implementation
        //find spot that has enemy piece in it and attack it
        for(BoardPiece p: piecesOnBoard) {
            int x = p.getxPos();
            int y = p.getyPos();
            int newX = x;
            int newY = y;

            //check down
            newX = x+1;
            newY = y;
            if (newX < 10) {
                //see only for spaces that have other players in them
                if(isEnemyPresent(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    return response;
                }
            }

            //check left
            newX = x;
            newY = y-1;
            if (newY >= 0) {
                if(isEnemyPresent(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    return response;
                }
            }
            //check right
            newX = x;
            newY = y+1;
            if (newY < 10) {
                if(isEnemyPresent(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    return response;
                }
            }
            //check up
            newX = x-1;
            newY = y;
            if(newX >= 0){
                if(isEnemyPresent(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    return response;
                }
            }


        }



        //regular non aggro AI

        for(BoardPiece p: piecesOnBoard){
            int x = p.getxPos();
            int y = p.getyPos();
            int newX = x;
            int newY = y;

            if(p.getPlayer().getColor().equals("Blue")){
                //check up for automated player first
                newX = x-1;
                newY = y;
                if(newX >= 0){
                    if(isSpaceAvailable(newX, newY, p.getPlayer())){
                        String aiResult = movePieceOnBoard(x, y, newX, newY);
                        response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                        break;
                    }
                }
            }
            //check down
            newX = x+1;
            newY = y;
            if (newX < 10) {
                if(isSpaceAvailable(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    break;
                }
            }

            //check left
            newX = x;
            newY = y-1;
            if (newY >= 0) {
                if(isSpaceAvailable(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    break;
                }
            }
            //check right
            newX = x;
            newY = y+1;
            if (newY < 10) {
                if(isSpaceAvailable(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    break;
                }
            }
            //check up
            newX = x-1;
            newY = y;
            if(newX >= 0){
                if(isSpaceAvailable(newX, newY, p.getPlayer())){
                    String aiResult = movePieceOnBoard(x, y, newX, newY);
                    response += x + " " + y + " " + newX + " " + newY + " " + aiResult;
                    break;
                }
            }

        }
        return response;
    }


    public boolean addPieceFromBank(String name, int posX, int posY, boolean isAi){
        if(isAi){
            for(BoardPiece p: aiPieces){
                if(p.getName().equals(name)){
                    if(board.addPiece(p, posX, posY)) {
                        aiPieces.remove(p);
                        return true;
                    }
                }
            }
            return false;
        }
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


    public String movePieceOnBoard(int x, int y, int newX, int newY){
        BoardPiece piece = getPieceFromBoard(x,y);
        // Checks if there is a piece at source
        if(piece != null){
            // checks if there is a piece at destination
            if(!checkValidMove(piece, newX,newY)) return "False";
            BoardPiece destination = getPieceFromBoard(newX, newY);
            if(destination != null){
                String result = interact(piece, destination);
                //to be added, check here if gamewon is true assuming we reached flag
                if(user_charsLost == 36){
                    gamelost = true;
                }else if(ai_charsLost == 36){
                    gamewon = true;
                }
                return result;
            }else {
                if (movePiece(piece, newX, newY)) {
                    return "True";
                }
            }
        }
        return "False";
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
        if(setUpPhase){
            //check player side boundary
            if(newX < PLAYER_ZONE_LIMIT) return false;
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
                    if(!isSpaceAvailable(i, currY, piece.getPlayer())) return false;
                }
                return true;
            }else{
                for(int i=currY+1; i<=newY; i++){
                    if(!isSpaceAvailable(currX, i, piece.getPlayer())) return false;
                }
                return true;
            }
        }
        //checks if moving one step forward/backward/side-to-side
        double dist = Math.sqrt((((double)newX-(double)currX)*((double)newX-(double)currX))+(((double)newY-(double)currY)*((double)newY-(double)currY)));
        if(dist != 1.0)return false;
        //checks if space is available
        return isSpaceAvailable(newX, newY, piece.getPlayer());

    }

    private boolean isSpaceAvailable(int newX, int newY, Player player){
        //check for rivers
        if(newX >= RIVER1[0] && newX < RIVER1[2] && newY >= RIVER1[1] && newY < RIVER1[3]){
            return false;
        }
        if(newX >= RIVER2[0] && newX < RIVER2[2] && newY >= RIVER2[1] && newY < RIVER2[3]){
            return false;
        }
        //check for piece
        BoardPiece piece = getPieceFromBoard(newX, newY);
        if(piece != null){
            if(player.getColor().equals("Blue")) {
                if(piece.getPlayer().getColor().equals("Blue")) return false;
            }else{
                if(piece.getPlayer().getColor().equals("Red")) return false;
            }
        }
        return true;
    }

    private boolean isEnemyPresent(int newX, int newY, Player player){
        //check for rivers
        if(newX >= RIVER1[0] && newX < RIVER1[2] && newY >= RIVER1[1] && newY < RIVER1[3]){
            return false;
        }
        if(newX >= RIVER2[0] && newX < RIVER2[2] && newY >= RIVER2[1] && newY < RIVER2[3]){
            return false;
        }
        //check for piece otherwise don't count as true
        BoardPiece piece = getPieceFromBoard(newX, newY);
        if(piece != null){
            if(player.getColor().equals("Blue")) {
                if(piece.getPlayer().getColor().equals("Blue")) return false;
                else{ return true; }
            }else{
                if(piece.getPlayer().getColor().equals("Red")) return false;
                else{ return true; }
            }
        }
        return false;
    }

    public String aiSetup(){
        ArrayList<BoardPiece> aiPieces = getAiPieces();
        Collections.shuffle(aiPieces);
        String toReturn = "";
        int loop = aiPieces.size();
        for(int i=0;i<loop;i++){
            int x = i/10;
            int y= i%10;
            String name = aiPieces.get(0).getName();
            makeMove(name,-1,-1,x,y,true);
            toReturn += name + " ";
        }

        toReturn = toReturn.trim();
        return toReturn;

    }

    public String autoSetup(){
        Collections.shuffle(userPieces);
        String toReturn = "";
        int loop = userPieces.size();
        for(int i=60;i<100;i++){
            int x = i/10;
            int y= i%10;
            if(getPieceFromBoard(x, y) != null){
                continue;
            }
            String name = userPieces.get(0).getName();
            makeMove(name,-1,-1,x,y,false);
            toReturn += name + " ";
        }

        toReturn = toReturn.trim();
        System.out.println(toReturn);
        return toReturn;

    }



    private String interact(BoardPiece attacker, BoardPiece defender){
        //do battle/bomb/flag/etc
        String selectedpiece = attacker.getName();
        String destinationpiece = defender.getName();
        int selectedstr = attacker.getStrength();
        int destinationstr = defender.getStrength();
        String attack_col = attacker.getPlayer().getColor();
        String defend_col = defender.getPlayer().getColor();


        attacker.setVisible(true);
        defender.setVisible(true);

        //check if piece is a flag or a bomb and dont let it be interacted with
        if(selectedpiece.equals("Flag") || selectedpiece.equals("Bomb")){
            System.out.println("Invalid, these pieces cannot be moved");
            return "False";
        }

        //check special cases for bomb
        //if a miner attacks a bomb, destroy the bomb
        if(selectedpiece.equals("Miner") && destinationpiece.equals("Bomb")){
            defender.setKilledBy(attacker);
            board.removePiece(defender.getxPos(), defender.getyPos());
            movePieceOnBoard(attacker.getxPos(), attacker.getyPos(), defender.getxPos(), defender.getyPos());
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyard
            if(defend_col.equals("Blue")){
                userGraveyard.add(defender);
            }
            else if(defend_col.equals("Red")){
                aiGraveyard.add(defender);
            }
            return "W";
        }
        //if a non miner piece attacks bomb, destroy the piece
        if(!selectedpiece.equals("Miner") && destinationpiece.equals("Bomb")){
            attacker.setKilledBy(defender);
            defender.setKilledBy(attacker);
            board.removePiece(attacker.getxPos(), attacker.getyPos());
            board.removePiece(defender.getxPos(), defender.getyPos());
            attacker.setxPos(-1);
            attacker.setyPos(-1);
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyard
            if(attack_col.equals("Blue")){
                userGraveyard.add(attacker);
                aiGraveyard.add(defender);
                user_charsLost++;
            }
            else if(attack_col.equals("Red")){
                aiGraveyard.add(attacker);
                userGraveyard.add(defender);
                ai_charsLost++;
            }
            return "D";
        }

        //if we touch the flag, game is won
        if(destinationpiece.equals("Flag")){
            defender.setKilledBy(attacker);
            if(defender.getPlayer().getName().equals("Opponent")){
                gamewon = true;
            }else {
                gamelost = true;
            }
        }

        //if spy attacks marshall
        if(selectedpiece.equals("Spy") && destinationpiece.equals("Marshall")){
            defender.setKilledBy(attacker);
            board.removePiece(defender.getxPos(), defender.getyPos());
            movePieceOnBoard(attacker.getxPos(), attacker.getyPos(), defender.getxPos(), defender.getyPos());
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyard
            if(defend_col.equals("Blue")){
                userGraveyard.add(defender);
                user_charsLost++;
            }
            else if(defend_col.equals("Red")){
                aiGraveyard.add(defender);
                ai_charsLost++;
            }
            return "W";
        }

        //if marshall attacks spy
        if(selectedpiece.equals("Marshall") && destinationpiece.equals("Spy")){
            defender.setKilledBy(attacker);
            board.removePiece(defender.getxPos(), defender.getyPos());
            movePieceOnBoard(attacker.getxPos(), attacker.getyPos(), defender.getxPos(), defender.getyPos());
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyard
            if(defend_col.equals("Blue")){
                userGraveyard.add(defender);
                user_charsLost++;
            }
            else if(defend_col.equals("Red")){
                aiGraveyard.add(defender);
                ai_charsLost++;
            }
            return "W";
        }


        //otherwise see if our attacker is stronger than defender
        else if(selectedstr > destinationstr){
            defender.setKilledBy(attacker);
            board.removePiece(defender.getxPos(), defender.getyPos());
            movePieceOnBoard(attacker.getxPos(), attacker.getyPos(), defender.getxPos(), defender.getyPos());
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyard
            if(defend_col.equals("Blue")){
                userGraveyard.add(defender);
                user_charsLost++;
            }
            else if(defend_col.equals("Red")){
                aiGraveyard.add(defender);
                ai_charsLost++;
            }
            return "W";

        }
        //if defender is stronger than attacker
        else if(selectedstr < destinationstr){
            attacker.setKilledBy(defender);
            board.removePiece(attacker.getxPos(), attacker.getyPos());
            attacker.setxPos(-1);
            attacker.setyPos(-1);
            //send to appropriate graveyard
            if(attack_col.equals("Blue")){
                userGraveyard.add(attacker);
                user_charsLost++;
            }
            else if(attack_col.equals("Red")){
                aiGraveyard.add(attacker);
                ai_charsLost++;
            }
            return "L";
        }

        //if they are of equal strength destroy both
        else if(selectedstr == destinationstr){
            defender.setKilledBy(attacker);
            attacker.setKilledBy(defender);
            board.removePiece(attacker.getxPos(), attacker.getyPos());
            board.removePiece(defender.getxPos(), defender.getyPos());
            attacker.setxPos(-1);
            attacker.setyPos(-1);
            defender.setxPos(-1);
            defender.setyPos(-1);
            //send to appropriate graveyards
            if(attack_col.equals("Blue")){
                userGraveyard.add(attacker);
                user_charsLost++;
            }
            else if(attack_col.equals("Red")){
                aiGraveyard.add(attacker);
                ai_charsLost++;
            }

            if(defend_col.equals("Blue")){
                userGraveyard.add(defender);
                user_charsLost++;
            }
            else if(defend_col.equals("Red")){
                aiGraveyard.add(defender);
                ai_charsLost++;
            }
            return "D";

        }

        return "False";
    }


    //Getters and setters


    public boolean isSetUpPhase() {
        return setUpPhase;
    }

    public void setSetUpPhase(boolean setUpPhase) {
        this.setUpPhase = setUpPhase;
    }

    public boolean isBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(boolean battlePhase) {
        this.battlePhase = battlePhase;
    }

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

    public ArrayList<BoardPiece> getAiGraveyard() {
        return aiGraveyard;
    }

    public void setAiGraveyard(ArrayList<BoardPiece> aiGraveyard) {
        this.aiGraveyard = aiGraveyard;
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

    public boolean isGamewon() {
        return gamewon;
    }

    public boolean isGamelost() {
        return gamelost;
    }
}
