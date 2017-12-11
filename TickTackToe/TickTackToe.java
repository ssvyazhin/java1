/**
* @author Sergey Svyazhin
* @version 08.12.2017
* @link null
*/

import java.util.Scanner;
import java.util.Random;

class TickTackToe {

    public static void main(String[] args) {
        TickTackGame game = new TickTackGame();
        game.ProcessGame();
    }

}

class TickTackGame {
    
    private final int SIZE = 5;
    private final int WIN_SIZE = 4;
    private final char DOT_X = 'x';
    private final char DOT_O = 'o';
    private final char DOT_EMPTY = '.';
    
    private GameMap gm;
    private HumanPlayer p1;
    private AIPlayer p2;
    
    TickTackGame() {
        gm = new GameMap(DOT_X, DOT_O, DOT_EMPTY, SIZE, WIN_SIZE);
        p1 = new HumanPlayer(DOT_X, DOT_O, DOT_EMPTY);
        p2 = new AIPlayer(DOT_O, DOT_X, DOT_EMPTY);
    }
    
    public void ProcessGame() {
        while (true) {
            
            p1.makeTurn(gm);
            
            gm.printMap();
            
            if (gm.checkWin(DOT_X)) {
                System.out.println("You win!");
                break;
            }
            
            if (gm.isMapFull()) {
                System.out.println("Draw!");
                break;
            }
            
            p2.makeTurn(gm);
            
            gm.printMap();
            
            if (gm.checkWin(DOT_O)) {
                System.out.println("AI win!");
                break;
            }
            
            if (gm.isMapFull()) {
                System.out.println("Draw!");
                break;
            }

        }
        System.out.println("Game over!");
    }  
    
}

class GameMap {
    
    private char[][] map;
    private int[] lastTurn = {-1,-1};
    private int mapSize;
    private int winSize;
    private char dot_x;
    private char dot_o;
    private char dot_empty;
    
    GameMap(char DOT_X, char DOT_O, char DOT_EMPTY, int SIZE, int WIN_SIZE) {
        mapSize = SIZE;
        winSize = WIN_SIZE;
        dot_x = DOT_X;
        dot_o = DOT_O;
        dot_empty = DOT_EMPTY;
        
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                map[i][j] = DOT_EMPTY;   
    }
    
    public void setCell(int y, int x, char dot) {
        map[y][x] = dot;
    }

    public char getCell(int y, int x) {
        return map[y][x];
    }
    
    public void setLastTurn(int x, int y) {
        lastTurn[0] = x;
        lastTurn[1] = y;
    }
    
    public int getXLastTurn() {
        return lastTurn[0];
    }
    
    public int getYLastTurn() {
        return lastTurn[1];
    }

    public int getMapSize() {
        return mapSize;
    }    
    
     public int getWinSize() {
        return winSize;
    }    
       
    public boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= mapSize || y >= mapSize)
            return false;
        if (map[y][x] == dot_empty)
            return true;
        return false;
    }
    
    public boolean isMapFull() {
        for (int i = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++)
                if (map[i][j] == dot_empty)
                    return false;
        return true;
    }
    
    public void printMap() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++)
                System.out.print(map[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkWin(char dot) {
        int y0, x0;
        int counter;

        // check horizontals
        counter = 0;
        for (int i=0;i<mapSize;i++) {
            if (map[lastTurn[1]][i] == dot) counter = counter + 1; else counter = 0;
            if (counter >=winSize) return true;    
        }
        
        // check verticals
        counter = 0;
        for (int i=0;i<mapSize;i++) {
            if (map[i][lastTurn[0]] == dot) counter = counter + 1; else counter = 0;
            if (counter >=winSize) return true;
        }

        // check first diagonal
        counter = 0;
        if (lastTurn[0]>lastTurn[1]) {
            y0 = 0;
            x0 = lastTurn[0] - lastTurn[1];
        } else {
            x0 = 0;
            y0 = lastTurn[1] - lastTurn[0];            
        }
        
        for(int i = y0 , j = x0;i<mapSize &&j<mapSize;i++,j++) {
            if (map[i][j] == dot) counter = counter + 1; else counter = 0;
            if (counter >=winSize) return true;
        }
        
        // check second diagonal
        counter = 0;
        if (lastTurn[0]>mapSize - lastTurn[1] - 1) {
            x0 = lastTurn[0] - (mapSize - lastTurn[1] - 1);
            y0 = mapSize - 1;
        } else {
            x0 = 0;
            y0 = lastTurn[1] + lastTurn[0] ;            
        }
        
        for(int i = y0 , j = x0;i>=0 &&j<mapSize;i--,j++) {
            if (map[i][j] == dot) counter = counter + 1; else counter = 0;
            if (counter >=winSize) return true;
        }

        return false;
    }

}

interface IPlayer {
    void makeTurn(GameMap gm);
}

abstract class Player implements IPlayer{
    
    protected char playerType;
    protected char dotType;
    protected char opositeDotType;
    protected char emptyDot;

    Player(char dotType, char opositeDotType, char emptyDot) {
        this.dotType = dotType;
        this.opositeDotType = opositeDotType;
        this.emptyDot = emptyDot;
    }

}

class HumanPlayer extends Player{
    
    private Scanner sc = new Scanner(System.in);
    
    HumanPlayer (char dotType, char opositeDotType, char emptyDot) {
        super(dotType, opositeDotType, emptyDot);
    }
    
    public void makeTurn(GameMap gm) {
        int x, y;
        do {
            System.out.println("Enter X and Y (1..3):");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!gm.isCellValid(x, y));
        gm.setCell(y, x, dotType); // map[y][x] = dotType;
        gm.setLastTurn(x,y);
    }
}

class AIPlayer extends Player {
    
    private Random rand = new Random();

    AIPlayer (char dotType, char opositeDotType, char emptyDot) {
        super(dotType, opositeDotType, emptyDot);
    }
    
    public void makeTurn(GameMap gm) {
        int x, y;
        int y0, x0;
        int counter, fine;
        x = 0;
        y = 0;
        
        if (gm.getXLastTurn() == -1) {
            do {
                x = rand.nextInt(gm.getMapSize());
                y = rand.nextInt(gm.getMapSize());
            } while (!gm.isCellValid(x, y));
        
            gm.setCell(y, x, dotType);
            gm.setLastTurn(x , y);
            
            return;
        }
        
        // check horizontals
        counter = 0;
        fine = 1;
        for(int i=0;i<gm.getMapSize();i++) {
            if (gm.getCell(gm.getYLastTurn(), i) == opositeDotType) {
                 counter=counter+1;
            } else {
                if (gm.getCell(gm.getYLastTurn(), i) == emptyDot) {
                    if (fine == 1) {
                        fine = fine -1;
                        counter = counter + 1;
                        y = gm.getYLastTurn();
                        x = i;
                    } else {
                        fine = 1;
                        counter = 0;
                        i = x;
                    }
                } else counter = 0;
            }
            if (counter >=gm.getWinSize()) {
                gm.setCell(y, x, dotType);
                gm.setLastTurn(x , y);
                return;
            }
        }
        
        // check verticals
        counter = 0;
        fine = 1;
        for (int i=0;i<gm.getMapSize();i++) {
            if (gm.getCell(i, gm.getXLastTurn()) == opositeDotType) {
                 counter = counter + 1;
            } else {
                if (gm.getCell(i, gm.getXLastTurn()) == emptyDot) {
                    if (fine == 1) {
                        fine = fine -1;
                        counter = counter + 1;
                        y = i;
                        x = gm.getXLastTurn();
                    } else {
                        fine = 1;
                        counter = 0;
                        i = y;
                    }
                } else counter = 0;
            }
            if (counter >=gm.getWinSize()) {
                gm.setCell(y, x, dotType);
                gm.setLastTurn(x , y);
                return;
            }
        }

        // check first diagonals
        counter = 0;
        fine = 1;
        if (gm.getXLastTurn()>gm.getYLastTurn()) {
            y0 = 0;
            x0 = gm.getXLastTurn() - gm.getYLastTurn();
        } else {
            x0 = 0;
            y0 = gm.getYLastTurn() - gm.getXLastTurn();            
        }
        
        for(int i = y0 , j = x0;i<gm.getMapSize() &&j<gm.getMapSize();i++,j++) {
            if (gm.getCell(i,j) == opositeDotType) {
                 counter = counter + 1;
            } else {
                if (gm.getCell(i,j) == emptyDot) {
                    if (fine == 1) {
                        fine = fine -1;
                        counter = counter + 1;
                        y = i;
                        x = j;
                    } else {
                        fine = 1;
                        counter = 0;
                        i = y;
                        j = x;
                    }
                } else counter = 0;
            }
            if (counter >=gm.getWinSize()) {
                gm.setCell(y, x, dotType);
                gm.setLastTurn(x , y);
                return;
            }
        }
        
        // check second diagonal
        counter = 0;
        fine = 1;
        if (gm.getXLastTurn()>gm.getMapSize() - gm.getYLastTurn() - 1) {
            x0 = gm.getXLastTurn() - (gm.getMapSize() - gm.getYLastTurn() - 1);
            y0 = gm.getMapSize() - 1;
        } else {
            x0 = 0;
            y0 = gm.getYLastTurn() + gm.getXLastTurn();            
        }
        
        for(int i = y0 , j = x0;i>=0 &&j<gm.getMapSize();i--,j++) {
            if (gm.getCell(i,j) == opositeDotType) {
                 counter = counter + 1;
            } else {
                if (gm.getCell(i,j) == emptyDot) {
                    if (fine == 1) {
                        fine = fine -1;
                        counter = counter + 1;
                        y = i;
                        x = j;
                    } else {
                        fine = 1;
                        counter = 0;
                        i = y;
                        j = x;
                    }
                } else counter = 0;
            }
            if (counter >=gm.getWinSize()) {
                gm.setCell(y, x, dotType);
                gm.setLastTurn(x , y);
                return;
            }
        }

        do {
            x = rand.nextInt(gm.getMapSize());
            y = rand.nextInt(gm.getMapSize());
        } while (!gm.isCellValid(x, y));
        
        gm.setCell(y, x, dotType);
        gm.setLastTurn(x , y);
    }

}