package minesweeper;

import java.util.Random;

public class Minesweeper
{
    private final int SIZE = 10;
    private final int numberOfMines = 10;
    boolean mineExposed;
    private CellState[][] cells = new CellState[SIZE][SIZE];
    protected boolean[][] mines = new boolean[SIZE][SIZE];


    public enum CellState {UNEXPOSED, EXPOSED, SEALED};


    public Minesweeper()
    {
        mineExposed = false;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                cells[i][j] = CellState.UNEXPOSED;
            }
        }
        //placeRandomMines();

    }

    public void exposeCell(int row, int column)
    {
        if(mineExposed == false)
        {
            if(cells[row][column] == CellState.UNEXPOSED && isAnAdjacentCell(row,column) == false)
            {
                cells[row][column] = CellState.EXPOSED;
                if(mines[row][column] == false)
                    exposeNeighborsOf(row, column);
                else mineExposed = true;

            }
        }
    }

    protected void exposeNeighborsOf(int row, int column)
    {
        for(int i = row - 1; i <= row+1; i++)
        {
            for(int j = column - 1; j <= column + 1; j++)
            {
                if((i >= 0 && i <=9) && (j >= 0 && j <= 9))
                    if(i != row || j != column)
                        exposeCell(i, j);
            }
        }
    }

    public CellState getCellState(int row, int column)
    {
        return cells[row][column];
    }

    protected boolean isAnAdjacentCell(int row, int column)
    {
        for(int i = row - 1; i <= row+1; i++)
        {
            for(int j = column - 1; j <= column + 1; j++)
            {
                if((i >= 0 && i <=9) && (j >= 0 && j <= 9))
                    if(i != row || j != column)
                        if(mines[i][j] == true)
                            return true;
            }
        }
        return false;
    }

    public void toggleSeal(int row, int column)
    {
        if(mineExposed == false)
        {
            if(cells[row][column] == CellState.UNEXPOSED)
                cells[row][column] = CellState.SEALED;
            else if(cells[row][column] == CellState.SEALED)
                cells[row][column] = CellState.UNEXPOSED;
        }
    }


    public boolean isAMine(int row, int column)
    {
        return mines[row][column];
    }



    //Venkat: Make this private and call it from within the constructor.
    private int placeRandomMines()
    {
        Random rand = new Random();
        int numMinesPlaced = 0;
        while(numMinesPlaced < numberOfMines)
        {
            int randRow = rand.nextInt(SIZE);
            int randColumn = rand.nextInt(SIZE);
            if(mines[randRow][randColumn] == false)
            {
                mines[randRow][randColumn] = true;
                numMinesPlaced++;
            }

        }
        return numMinesPlaced;
    }

    //Venkat: Merge the three functions below into one getGameStatus function that returns a GameStatus which is an enum of WON, LOST, INPROGRESS
    public boolean checkGameLost()
    {
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(mines[i][j] == true && cells[i][j] == CellState.EXPOSED)
                    return true;
            }
        }
        return false;
    }

    public boolean checkGameInProgress()
    {
        int numMinesSealed = 0;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(mines[i][j] == true && cells[i][j] == CellState.SEALED)
                    numMinesSealed++;
                if(mines[i][j] == true && cells[i][j] == CellState.EXPOSED)
                    mineExposed = true;
            }
        }
        if(numMinesSealed != numberOfMines && mineExposed == false)
            return true;
        else
            return false;

    }

    public boolean checkGameWon()
    {
        int numMinesSealed = 0;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(mines[i][j] == true && cells[i][j] == CellState.SEALED)
                    numMinesSealed++;
            }
        }
        if(numMinesSealed == numberOfMines)
            return true;
        else
            return false;
    }
}