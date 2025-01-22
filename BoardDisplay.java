package numberPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardDisplay extends JPanel {
    private Puzzle puzzle;  // Use Puzzle class to manage the game state
    private int cellSize;
    private int boardSize;

    // Modify constructor to accept puzzle and board size
    public BoardDisplay(int gridSize, int boardSize) {
        this.boardSize = boardSize;
        this.cellSize = boardSize / gridSize;
        this.puzzle = new Puzzle(gridSize);  // Create a new puzzle with the given grid size

        // Add mouse listener to detect clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
                repaint();  // Repaint the board with updated state
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] grid = puzzle.getGrid();  // Get the current grid from the Puzzle class

        // Draw the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                // Draw cell border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);

                // Draw number if the cell is not empty
                if (grid[i][j] != 0) {
                    g.setColor(Color.BLUE);
                    g.setFont(new Font("Arial", Font.BOLD, cellSize / 3));  // Dynamic font size
                    g.drawString(String.valueOf(grid[i][j]), x + cellSize / 2 - 10, y + cellSize / 2 + 10);
                }
            }
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        int row = mouseY / cellSize;
        int col = mouseX / cellSize;

        // Attempt to move the clicked tile
        puzzle.moveTile(row, col);
    }

    public void shufflePuzzle() {
        puzzle.shuffle();
        repaint();  // Repaint the board after shuffling
    }
    
    public void simulatePuzzle() {
        puzzle.simulate(this);
        
    }

    // Method to check if the puzzle is solved
    public boolean isSolved() {
        return puzzle.isSolved();
    }
}
