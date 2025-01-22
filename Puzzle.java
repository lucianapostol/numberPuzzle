package numberPuzzle;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puzzle {
    private int[][] grid;
    private int gridSize;

    public Puzzle(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        initializeGrid();  // Initialize the grid with numbers
    }

    // Initialize the grid with numbers from 1 to gridSize^2 - 1, and 0 as the empty space
    private void initializeGrid() {

		 Random random = new Random();

        // Generate a shuffled array of numbers from 0 to n-1
        int[] shNum = IntStream.range(0, (gridSize * gridSize))
                                         .boxed()          // Convert to Stream<Integer>
                                         .sorted((a, b) -> random.nextInt(3) - 1) // Shuffle
                                         .mapToInt(Integer::intValue) // Back to int stream
                                         .toArray();    	  
    	  
    	  
        int num = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
            	
            	 grid[i][j] = shNum[(i*gridSize)+j];
                //if (num < gridSize * gridSize) {
                    //grid[i][j] = num++;
                //} else {
                //    grid[i][j] = 0;  // Empty cell represented by 0
                //}
            }
        }
    }

    // Get the current state of the grid
    public int[][] getGrid() {
        return grid;
    }

    // Find the position of the empty space (0)
    public int[] findEmptySpace() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 0) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    // Move a tile into the empty space, if adjacent
    public boolean moveTile(int row, int col) {
        int[] emptyPos = findEmptySpace();
        int emptyRow = emptyPos[0];
        int emptyCol = emptyPos[1];

        // Check if the tile to move is adjacent to the empty space
        if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
            (Math.abs(col - emptyCol) == 1 && row == emptyRow)) {
            // Swap the clicked tile with the empty space
            grid[emptyRow][emptyCol] = grid[row][col];
            grid[row][col] = 0;
            return true;  // Successfully moved
        }
        return false;  // Invalid move
    }

    // Check if the puzzle is solved (i.e., grid is in correct order)
    public boolean isSolved() {
        int num = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] != num++ && !(i == gridSize - 1 && j == gridSize - 1 && grid[i][j] == 0)) {
                    return false;  // Not in the correct order
                }
            }
        }
        return true;  // Puzzle is solved
    }

    // Method to simulate a random shuffle of the puzzle grid
    public void shuffle() {
        // Simple random shuffle logic (could be more sophisticated)
        for (int i = 0; i < 100; i++) {
            int[] emptyPos = findEmptySpace();
            int emptyRow = emptyPos[0];
            int emptyCol = emptyPos[1];

            // Randomly select a neighboring tile to move into the empty space
            int newRow = emptyRow + (Math.random() < 0.5 ? 1 : -1);
            int newCol = emptyCol + (Math.random() < 0.5 ? 1 : -1);

            // Ensure the move is valid
            if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                moveTile(newRow, newCol);
            }
        }
    }
    
    
    public void simulate(BoardDisplay board) {
        for (int i = 0; i < 100; i++) {
            int[] emptyPos = findEmptySpace();
            int emptyRow = emptyPos[0];
            int emptyCol = emptyPos[1];

            // Randomly select a neighboring tile to move into the empty space
            int newRow = emptyRow + 1;
            int newCol = emptyCol;

            // Ensure the move is valid
            if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                moveTile(newRow, newCol);
                board.repaint();
                
                return;
            }
         /*  try {
                Thread.sleep(1000); // Pause for 1000 milliseconds (1 second)
           } catch (InterruptedException e) {
                System.out.println("Thread was interrupted.");
           } */
            //simulate();
        }
    }    
    
    
    
    
}