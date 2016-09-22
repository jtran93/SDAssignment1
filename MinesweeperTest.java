package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import minesweeper.Minesweeper.CellState;
import minesweeper.Minesweeper.GameStatus;
import org.junit.After;

public class MinesweeperTest {

    Minesweeper minesweeper;
    boolean noMines;
    boolean exposeNeighborsOfCalled;
    ArrayList<Integer> rowsAndColumns;

    @Before
    public void setUp()
    {
        minesweeper = new Minesweeper()
        {

        };
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
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.exposeCell(3, 4);

        assertEquals(CellState.EXPOSED, minesweeper.getCellState(3, 4));
    }

    @Test
    public void exposeAlreadyExposedCell()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.exposeCell(2, 2);
        minesweeper.exposeCell(2, 2);

        assertEquals(CellState.EXPOSED, minesweeper.getCellState(2, 2));
    }

    @Test
    public void exposeCellTwoDifferentCells()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.exposeCell(8, 3);
        minesweeper.exposeCell(2, 0);

        assertEquals(CellState.EXPOSED, minesweeper.getCellState(8, 3));
        assertEquals(CellState.EXPOSED, minesweeper.getCellState(2, 0));
    }

    @Test
    public void exposeCellExposesItsNeighbors()
    {
        Minesweeper minesweeper = new Minesweeper(noMines)
        {
            @Override
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
        Minesweeper minesweeper = new Minesweeper(noMines)
        {
            @Override
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
        Minesweeper minesweeper = new Minesweeper(noMines)
        {
            @Override
            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }

            @Override
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

        assertEquals(Arrays.asList(2, 3, 2, 4, 2, 5, 3, 3, 3, 5, 4, 3, 4, 4, 4, 5), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnTopEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(0, 4);

        assertEquals(Arrays.asList(0, 3, 0, 5, 1, 3, 1, 4, 1, 5), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnRightEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(4, 9);

        assertEquals(Arrays.asList(3, 8, 3, 9, 4, 8, 5, 8, 5, 9), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnBottomEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(9, 4);

        assertEquals(Arrays.asList(8, 3, 8, 4, 8, 5, 9, 3, 9, 5), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnLeftEdge()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(4, 0);

        assertEquals(Arrays.asList(3, 0, 3, 1, 4, 1, 5, 0, 5, 1), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnTopLeftCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(0, 0);

        assertEquals(Arrays.asList(0, 1, 1, 0, 1, 1), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnTopRightCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(0, 9);

        assertEquals(Arrays.asList(0, 8, 1, 8, 1, 9), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnBottomRightCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(9, 9);

        assertEquals(Arrays.asList(8, 8, 8, 9, 9, 8), rowsAndColumns);
    }

    @Test
    public void exposeNeighborsOnBottomLeftCorner()
    {
        Minesweeper minesweeper = new MinesweeperWithExposeCell();

        minesweeper.exposeNeighborsOf(9, 0);

        assertEquals(Arrays.asList(8, 0, 8, 1, 9, 1), rowsAndColumns);
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
        Minesweeper minesweeper = new Minesweeper(noMines)
        {
            @Override
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

    @Test
    public void mineIsExposedTest()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;
        minesweeper.exposeCell(3, 4);

        assertTrue(minesweeper.isAMine(3,4));
        assertEquals(CellState.EXPOSED, minesweeper.getCellState(3,4));
    }

    @Test
    public void exposingMineCellDoesNotCallExposeNeighborsOf()
    {
        Minesweeper minesweeper = new Minesweeper()
        {
            @Override
            protected void exposeNeighborsOf(int row, int column)
            {
                exposeNeighborsOfCalled = true;
            }
        };

        minesweeper.mines[3][4] = true;
        minesweeper.exposeCell(3, 4);

        assertFalse(exposeNeighborsOfCalled);
    }

    @Test
    public void afterMineIsExposedShouldNotAllowExposeCell()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;
        minesweeper.exposeCell(3, 4);
        minesweeper.exposeCell(8, 8);

        assertEquals(CellState.UNEXPOSED, minesweeper.getCellState(8, 8));

    }



    @Test
    public void afterMineIsExposedShouldNotAllowSealCell()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;
        minesweeper.exposeCell(3, 4);

        minesweeper.toggleSeal(1,6);
        assertEquals(CellState.UNEXPOSED, minesweeper.getCellState(1, 6));


    }


    @Test
    public void notAnAdjacentCell()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;

        assertFalse(minesweeper.isAnAdjacentCell(3, 4));
    }

    @Test
    public void isAnAdjacentCellNotOnAnyEdge()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;

        assertTrue(minesweeper.isAnAdjacentCell(2, 4));
    }

    @Test
    public void isAnAdjacentCellOnCorner()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[1][1] = true;

        assertTrue(minesweeper.isAnAdjacentCell(0, 0));
    }

    @Test
    public void isAnAdjacentCellOnEdge()
    {
        Minesweeper minesweeper = new Minesweeper();

        minesweeper.mines[9][5] = true;

        assertTrue(minesweeper.isAnAdjacentCell(8, 4));
    }

  @Test
    public void randomMinePlacementPlaces10Mines()
    {
        Minesweeper minesweeper = new Minesweeper();

        assertEquals(10,minesweeper.getNumberOfMines());
    }



    @Test
    public void minesAreRandomlyPlaced() {
        Minesweeper minesweeper = new Minesweeper();
        Minesweeper minesweep = new Minesweeper();

        assertFalse(Arrays.equals(minesweeper.mines, minesweep.mines));
    }

    @Test
    public void gameLossWhenAnyMinesAreExposed()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;
        minesweeper.exposeCell(3, 4);

        assertEquals(GameStatus.LOST, minesweeper.getGameStatus());
    }

    @Test
    public void gameInProgressWhenNoMinesAreExposedAndAllMinesAreNotSealed()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);

        minesweeper.mines[3][4] = true;
        minesweeper.toggleSeal(3, 4);

        assertEquals(GameStatus.INPROGRESS, minesweeper.getGameStatus());
    }

    @Test
    public void gameWonWhenAllMinesAreSealedAndOtherCellsAreExposed()
    {
        Minesweeper minesweeper = new Minesweeper(noMines);
        for(int x = 0; x < 10; x++)
        {
            minesweeper.mines[x][0] = true;
            minesweeper.toggleSeal(x, 0);

        }
        for(int i = 0; i< 10; i++) {
            for(int j = 1; j < 10; j++) {
                minesweeper.exposeCell(i,j);
            }
        }

        assertEquals(GameStatus.WON, minesweeper.getGameStatus());
    }
}