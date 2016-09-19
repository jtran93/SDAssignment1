package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import minesweeper.Minesweeper.CellState;
import org.junit.After;

public class MinesweeperTest {

    Minesweeper minesweeper;
    boolean exposeNeighborsOfCalled;
    ArrayList<Integer> rowsAndColumns;

    @Before
    public void setUp()
    {
        minesweeper = new Minesweeper();
        exposeNeighborsOfCalled = false;
        rowsAndColumns = new ArrayList<Integer>();
    }

    @Test
    public void Canary()
    {
        assertTrue(true);
    }

    @Test
    public void exposeCellOnNonExposedCell()
    {
        minesweeper.exposeCell(3, 4);
        
        assertEquals(CellState.EXPOSED, minesweeper.getCellState(3, 4));
    }

    @Test
    public void exposeAlreadyExposedCell()
    {
        minesweeper.exposeCell(2, 2);
        minesweeper.exposeCell(2, 2);
        
        assertEquals(CellState.EXPOSED, minesweeper.getCellState(2, 2));
    }

    @Test
    public void exposeCellTwoDifferentCells()
    {
        minesweeper.exposeCell(8, 3);
        minesweeper.exposeCell(2, 0);

        assertEquals(CellState.EXPOSED, minesweeper.getCellState(8, 3));
        assertEquals(CellState.EXPOSED, minesweeper.getCellState(2, 0));
    }

    @Test
    public void exposeCellExposesItsNeighbors()
    {
        Minesweeper minesweeper = new Minesweeper()
        {

            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }
        };

        minesweeper.exposeCell(3, 4);
        
        assertTrue(exposeNeighborsOfCalled);
    }

    @Test
    public void exposeCellOnAlreadyExposedDoesNotExposeNeighbors()
    {
        Minesweeper minesweeper = new Minesweeper()
        {

            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }

        };
        
        minesweeper.exposeCell(3, 4);

        assertTrue(exposeNeighborsOfCalled);
        exposeNeighborsOfCalled = false;

        minesweeper.exposeCell(3, 4);
        assertFalse(exposeNeighborsOfCalled);
    }

    @Test
    public void exposeCellOnAdjacentCellDoesNotExposeNeighbors() {
        Minesweeper minesweeper = new Minesweeper()
        {

            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }
            protected boolean isAnAdjacentCell(int row, int column) {
                return true;
            }
        };

        minesweeper.exposeCell(3,4);
        assertFalse(exposeNeighborsOfCalled);
    }

    @Test
    public void exposeCellOutOfBoundsError()
    {
        try
        {
            minesweeper.exposeCell(8, 10);
            fail("Expected null pointer exception.");
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            assertTrue(true);
        }
    }

    class MinesweeperWithExposeCell extends Minesweeper
    {
        @Override
        public void exposeCell(int row, int column)
        {
            rowsAndColumns.add(row);
            rowsAndColumns.add(column);
        }
    }

    @Test
    public void exposeNeighborsNotOnAnyEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        minesweeper.exposeNeighborsOf(3,4);
        
        assertEquals(Arrays.asList(2, 4, 4, 4, 3, 3, 3, 5, 2, 3, 4, 5, 2, 5, 4, 3), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnTopEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(0, 4);
        
        assertEquals(Arrays.asList(1, 4, 0, 3, 0, 5, 1, 5, 1, 3), rowsAndColumns);
    }
     
    @Test
    public void exposeNeighborsOnRightEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(4, 9);
        
        assertEquals(Arrays.asList(3, 9, 5, 9, 4, 8, 3, 8, 5, 8), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnBottomEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(9, 4);
        
        assertEquals(Arrays.asList(8, 4, 9, 3, 9, 5, 8, 3, 8, 5), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnLeftEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(4, 0);
        
        assertEquals(Arrays.asList(3, 0, 5, 0, 4, 1, 5, 1, 3, 1), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnTopLeftCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(0, 0);
        
        assertEquals(Arrays.asList(1, 0, 0, 1, 1, 1), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnTopRightCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(0, 9);
        
        assertEquals(Arrays.asList(1, 9, 0, 8, 1, 8), rowsAndColumns);
    }
  
    @Test
    public void exposeNeighborsOnBottomRightCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(9, 9);
        
        assertEquals(Arrays.asList(8, 9, 9, 8, 8, 8), rowsAndColumns);
    }
    
    @Test
    public void exposeNeighborsOnBottomLeftCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();
        
        minesweeper.exposeNeighborsOf(9, 0);
        
        assertEquals(Arrays.asList(8, 0, 9, 1, 8, 1), rowsAndColumns);
    }
    
    @Test
    public void sealCellOnUnexposedCell()
    {
        minesweeper.toggleSeal(3, 4);
        assertEquals(CellState.SEALED, minesweeper.getCellState(3, 4));
    }
    
    @Test
    public void unsealCellOnASealedCell()
    {
        minesweeper.toggleSeal(3, 4);
        minesweeper.toggleSeal(3, 4);
        
        assertEquals(CellState.UNEXPOSED, minesweeper.getCellState(3, 4));
    }
    
    @Test
    public void exposeCellOnASealedCell()
    {
        Minesweeper minesweeper = new Minesweeper()
        {
            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }
        };
        
        minesweeper.toggleSeal(3, 4);
        minesweeper.exposeCell(3, 4);
        assertEquals(CellState.SEALED, minesweeper.getCellState(3, 4));
        assertFalse(exposeNeighborsOfCalled);
    }
}
