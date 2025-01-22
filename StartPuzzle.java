package numberPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPuzzle {
    // Move board as an instance variable
    private static BoardDisplay board;

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Number Puzzle");

        // Dynamically set the size of the frame
        int defaultBoardSize = 400; // Fixed board size for now
        frame.setSize(defaultBoardSize + 200, defaultBoardSize + 200); // Add padding
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // **Create a panel for input and buttons, set layout to FlowLayout**
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());  // Use FlowLayout for horizontal arrangement
        topPanel.setBounds(100, 20, defaultBoardSize, 60); // Set the bounds of the panel
        frame.add(topPanel);

        // **Input field for grid size**
        JTextField gridSizeField = new JTextField();
        gridSizeField.setColumns(5);  // Set the number of columns for input box
        topPanel.add(gridSizeField);  // Add the input box to the top panel

        // **Button to generate the puzzle**
        JButton generateButton = new JButton("Generate Puzzle");
        topPanel.add(generateButton);  // Add the button to the top panel

        // **Shuffle button**
        JButton shuffleButton = new JButton("Shuffle");
        topPanel.add(shuffleButton);  // Add the shuffle button to the top panel
        
        
        // Butonul care lansează algoritmul de găsire a soluțiilor
        JButton simulateButton = new JButton("Rezolva");
        topPanel.add(simulateButton);  // Add the shuffle button to the top panel

        // Initially set gridSize to 4 (or any valid default)
        int[] gridSize = {4}; // Use an array to make gridSize mutable
        board = new BoardDisplay(gridSize[0], defaultBoardSize);  // Initialize the board here
        board.setBounds(100, 120, defaultBoardSize, defaultBoardSize); // Set board position
        frame.add(board);

        // **ActionListener for generate button**
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newGridSize = Integer.parseInt(gridSizeField.getText()); // Get grid size from input

                    // **Validation for even number and max 20**
                    if (newGridSize % 2 != 0 || newGridSize > 20 || newGridSize < 4) {
                        JOptionPane.showMessageDialog(frame, "Please enter an even number between 4 and 20.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // **Update gridSize and re-initialize the board**
                        gridSize[0] = newGridSize; // Update the grid size inside the array

                        // Remove previous board and add the new one
                        frame.remove(board);  // Remove the previous board
                        board = new BoardDisplay(gridSize[0], defaultBoardSize); // Create a new BoardDisplay
                        board.setBounds(100, 120, defaultBoardSize, defaultBoardSize); // Set the new board position
                        frame.add(board);  // Add the new board to the frame

                        frame.revalidate(); // Revalidate the frame
                        frame.repaint(); // Repaint the frame to reflect changes
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // **ActionListener for shuffle button**
        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.shufflePuzzle(); // Shuffle the puzzle
            }
        });

        // Acțiunea pentru butonul Rezolva
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.simulatePuzzle(); // Apelează metoda pentru găsirea unei soluții
               // JOptionPane.showMessageDialog(frame, "Apasat");
            }
        });



        frame.setVisible(true);
    }
}
