package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import minesweeper.Minesweeper;
import minesweeper.Minesweeper.CellState;
import minesweeper.Minesweeper.GameStatus;

public class MinesweeperFrame extends JFrame
{
    private static final int SIZE = 10;
    private static JFrame frame;
    private static JPanel panel;
    private static MinesweeperCell[][] buttons = new MinesweeperCell[SIZE][SIZE];
    Minesweeper minesweeper;
    
    @Override
    protected void frameInit()
    {
        super.frameInit();
        minesweeper = new Minesweeper();
        panel = new JPanel();
        panel.setLayout(new GridLayout(SIZE, SIZE));
        
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                buttons[i][j] = new MinesweeperCell(i, j);
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].setText("Unexposed");
                panel.add(buttons[i][j]);
                
                buttons[i][j].addMouseListener(new CellClickedHandler());
            }
        }
        
    }
    
    public static void main(String[] args)
    {
        frame = new MinesweeperFrame();
        frame.setTitle("Minesweeper");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    private void updateCellStates()
    {
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(minesweeper.getCellState(i, j) == CellState.EXPOSED)
                {
                    if(!minesweeper.isAMine(i, j))
                    {
                        if(minesweeper.isAnAdjacentCell(i, j))
                        {
                            buttons[i][j].setBackground(Color.orange);
                            buttons[i][j].setText(Integer.toString(getAdjacentMineCount(i, j)));
                        }
                        else
                        {
                            buttons[i][j].setBackground(Color.yellow);
                            buttons[i][j].setText("Exposed");
                        }
                    }
                    else
                    {
                        buttons[i][j].setBackground(Color.red);
                        buttons[i][j].setText("DEAD");
                    }
                }
                else if(minesweeper.getCellState(i, j) == CellState.UNEXPOSED)
                {
                    buttons[i][j].setBackground(Color.white);
                    buttons[i][j].setText("Unexposed");

                }
                else if(minesweeper.getCellState(i, j) == CellState.SEALED)
                {
                    buttons[i][j].setBackground(Color.green);
                    buttons[i][j].setText("Sealed");
                }
            }
        }
    }
    
    private void checkGameStatus()
    {
        if(minesweeper.getGameStatus() == GameStatus.LOST)
        {
            exposeAllMines();
            JOptionPane.showMessageDialog(rootPane, "YOU HAVE LOST!!!", "Game Over", JOptionPane.ERROR_MESSAGE);
            
            reset();
        }
        else if(minesweeper.getGameStatus() == GameStatus.WON)
        {
            JOptionPane.showMessageDialog(rootPane, "YOU HAVE WON!!!", "Winner", JOptionPane.WARNING_MESSAGE);  
            reset();
        }
    }
    
    private void exposeAllMines()
    {
        System.out.println("Hello");
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j ++)
            {
                if(minesweeper.isAMine(i, j) == true)
                {
                    buttons[i][j].setBackground(Color.red);
                }
            }
        }
    }
    
    private void reset()
    {
        minesweeper = new Minesweeper();
        updateCellStates();
    }

    private int getAdjacentMineCount(int row, int column) 
    {
        int adjacentMineCount = 0;
        for (int i = row - 1; i <= row + 1; i++) 
        {
            for (int j = column - 1; j <= column + 1; j++) 
            {
                if ((i >= 0 && i <= 9) && (j >= 0 && j <= 9))
                    if (i != row || j != column)
                        if (minesweeper.isAMine(i, j) == true)
                            adjacentMineCount++;
            }
        }
        return adjacentMineCount;
    }
    
    private class CellClickedHandler implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) 
        {
            MinesweeperCell cell = (MinesweeperCell) mouseEvent.getSource();
            if(SwingUtilities.isRightMouseButton(mouseEvent))
                minesweeper.toggleSeal(cell.row, cell.column);
            else if(SwingUtilities.isLeftMouseButton(mouseEvent))
                minesweeper.exposeCell(cell.row, cell.column);
            updateCellStates();
            checkGameStatus();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }    
}
