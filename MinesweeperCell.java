package ui;

import javax.swing.JButton;

class MinesweeperCell extends JButton
{
    public final int row;
    public final int column;
    public MinesweeperCell(int theRow, int theColumn) 
    {
        row = theRow;
        column = theColumn;
        setSize(10, 10);
    }
    
}
