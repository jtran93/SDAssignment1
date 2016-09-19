package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Minesweeper
{
    private final int SIZE = 10;
    private CellState[][] cells = new CellState[SIZE][SIZE];
    public enum CellState{UNEXPOSED, EXPOSED, SEALED};

    public Minesweeper()
    {
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                cells[i][j] = CellState.UNEXPOSED;
            }
        }
    }

    public void exposeCell(int row, int column)
    {
        if(cells[row][column] == CellState.UNEXPOSED && isAnAdjacentCell(row,column) == false) 
        {
            cells[row][column] = CellState.EXPOSED;
            exposeNeighborsOf(row, column);
        }
    }

    protected void exposeNeighborsOf(int row, int column)
    {
      //Venkat: Please use a loop to reduce code
        if(row > 0)
            exposeCell(row-1, column);
        if(row < 9)
            exposeCell(row+1, column);
        if(column > 0)
            exposeCell(row, column-1);
        if(column < 9)
            exposeCell(row, column+1);
        if(row > 0 && column > 0)
            exposeCell(row-1, column-1);
        if(row < 9 && column < 9)
            exposeCell(row+1, column+1);
        if(row > 0 && column < 9)
            exposeCell(row-1, column+1);
        if(row < 9 && column > 0)
            exposeCell(row+1, column-1);
    }

    public CellState getCellState(int row, int column)
    {
        return cells[row][column];
    }

    protected boolean isAnAdjacentCell(int row, int column)
    {
        return false;
    }
    
    public void toggleSeal(int row, int column)
    {
        if(cells[row][column] == CellState.UNEXPOSED)
            cells[row][column] = CellState.SEALED;
        else if(cells[row][column] == CellState.SEALED)
            cells[row][column] = CellState.UNEXPOSED;
    }
}
