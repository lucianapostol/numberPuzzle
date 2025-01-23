package numberPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BoardDisplay extends JPanel {
    private Puzzle puzzle;  //Folosim o clasa separata pentru a face calculele
    private int cellSize;
    private int boardSize;

    // Constructorul acceptă mărimea panoului
    public BoardDisplay(int gridSize, int boardSize) {
        this.boardSize = boardSize;
        this.cellSize = boardSize / gridSize;
        this.puzzle = new Puzzle(gridSize);  // Creeaza un nou puzzle cu dimensiunile date

        //Mouse listener pentru a urmari click-urile
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
                repaint();  
            }
        });
    }


	 //Se deseneaza pe ecran	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] grid = puzzle.getGrid();  

        //Se deseneaza panoul
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                // Draw cell border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);

                // Afisarea numerelor
                if (grid[i][j] != 0) {
                    g.setColor(Color.BLUE);
                    g.setFont(new Font("Arial", Font.BOLD, cellSize / 3));  // Fontul se adapteaza in functie de cât de multe rânduri și coloane sunt
                    g.drawString(String.valueOf(grid[i][j]), x + cellSize / 2 - 10, y + cellSize / 2 + 10);
                }
            }
        }
    }

	//Se proceseaza apasarea pe mouse
    private void handleMouseClick(int mouseX, int mouseY) {
        int row = mouseY / cellSize;
        int col = mouseX / cellSize;

        //Apeleaza metoda care muta piesa
        puzzle.moveTile(row, col);
    }


    
public void simulatePuzzle() {
    //Se apealează metoda care cauta solutia
    List<Move> solutionMoves = puzzle.rezolvaPuzzle();
    
    
    // Daca se returneaza array gol, inseamna ca nu a gasit nici o solutie
    if (solutionMoves.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nici o soluție găsită", "Eroare", JOptionPane.ERROR_MESSAGE);
        return; 
    }

	 //Se afiseaza catre consola solutia gasita
    System.out.println("Solved in " + solutionMoves.size() + " moves:");
    for (Move move : solutionMoves) {
        System.out.print("(" + move.x + ", " + move.y + ") ");
    }
    System.out.println();    
    

    //Temporizarea rezolvării pentru a se putea urmari aplicarea solutiei gasite
    Timer timer = new Timer(500, new ActionListener() {
        int moveIndex = 0; 

        @Override
        public void actionPerformed(ActionEvent e) {
            if (moveIndex < solutionMoves.size()) {
                Move move = solutionMoves.get(moveIndex);

                //Mutarile sunt simulate cu simulateMove
                puzzle.simulateMove(move.x, move.y);  
                repaint();  // Repaint after each move
                moveIndex++;
            } else {
                ((Timer) e.getSource()).stop(); 
            }
        }
    });

    timer.start();  
}    
    
    
}
