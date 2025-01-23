package numberPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPuzzle {
    //Clasa Board Display extinde JPanel
    private static BoardDisplay board;

    public static void main(String[] args) {
        //
        JFrame frame = new JFrame("Puzzle cu numere");

        //Se setează dimensiunile panoului. Pe viitor îl facem dinamic
        int defaultBoardSize = 400; 
        frame.setSize(defaultBoardSize + 200, defaultBoardSize + 200); // + 200 să nu vină fix pe margini.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        //Se creează panoul pentru butoane
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());  // Se folosește pentru aranjarea pe orizontală
        topPanel.setBounds(100, 20, defaultBoardSize, 60); // Setăm poziția panoului
        frame.add(topPanel);

        //Căsuța de intrare pentru dimensiunile jocului
        JTextField gridSizeField = new JTextField();
        gridSizeField.setColumns(2);  // Numărul de coloane pentru căsuta de intrare
        gridSizeField.setText("3"); 
        topPanel.add(gridSizeField);  

        // Butonul care generează puzzle-ul
        JButton generateButton = new JButton("Genereaza Puzzle");
        topPanel.add(generateButton);  
      
        // Butonul care lansează algoritmul de găsire a soluțiilor
        JButton simulateButton = new JButton("Rezolva");
        topPanel.add(simulateButton);  

        // Initial panoul are 3 randuri si 3 coloane. La 4 deja sta prea mult sa gaseasca solutia
        int[] gridSize = {3}; // Use an array to make gridSize mutable
        board = new BoardDisplay(gridSize[0], defaultBoardSize);  // Initialize the board here
        board.setBounds(100, 120, defaultBoardSize, defaultBoardSize); // Set board position
        frame.add(board);

        //Acțiunea pentru butonul ce genereaza panoul
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newGridSize = Integer.parseInt(gridSizeField.getText()); // Se preia valoarea de la căsuta te intrare

                    //Validam sa fie mai mic de 10, nu are sens, e oricum prea mare, dar pentru demonstratie
                    	if (newGridSize > 10 || newGridSize < 2) {
                        JOptionPane.showMessageDialog(frame, "Adauga un numar intre 2 si 10", "Numar invalid", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Actualizare dimensiune si reinitializare
                        gridSize[0] = newGridSize; 

                        //Stergem panoul dinainte si il adaugam pe cel nou
                        frame.remove(board);  
                        board = new BoardDisplay(gridSize[0], defaultBoardSize); 
                        board.setBounds(100, 120, defaultBoardSize, defaultBoardSize); 
                        frame.add(board);  

                        frame.revalidate(); 
                        frame.repaint(); 
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Adauga un numar.", "Numar invalid", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Acțiunea pentru butonul Rezolva
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.simulatePuzzle(); // Apelează metoda pentru găsirea si simularea unei solutii
            }
        });



        frame.setVisible(true);
    }
}
