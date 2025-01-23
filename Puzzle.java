package numberPuzzle;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Puzzle {
    private int[][] grid;
    private int gridSize;

    public Puzzle(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        initializeGrid();  // Se initializeaza panoul
    }

    //Se initializeaza panoul cu numere si cu spatiul liber
    private void initializeGrid() {

		 Random random = new Random();

        // Se generează random
        int[] shNum = IntStream.range(0, (gridSize * gridSize))
                                         .boxed()          
                                         .sorted((a, b) -> random.nextInt(3) - 1) 
                                         .mapToInt(Integer::intValue) 
                                         .toArray();    	  
    	  
    	  
        int num = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
            	
            	 grid[i][j] = shNum[(i*gridSize)+j];

            }
        }
    }

    //Se preia starea curenta a panoului
    public int[][] getGrid() {
        return grid;
    }

    //Se cauta pozitia spatiului gol (reprezentat prin zero)
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
    
    
	//Se verifica dacae o mutare valida, sa nu iasa din panou    
	public boolean isValidMove(int row, int col) {
	    return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
	}
    
   //Metoda pentru simularea mutarilor. Se apeleaza atunci cand se gaseste solutia
	public void simulateMove(int row, int col) {
	    // Find the empty space (0) position
	    int[] emptySpace = findEmptySpace(); 
	    int emptyRow = emptySpace[0];
	    int emptyCol = emptySpace[1];
	
		//Mutarile valide sunt doar acelea unde o piesa se muta in spatiul liber
	    if (isValidMove(row, col)) {
	        int temp = grid[row][col];
	        grid[row][col] = grid[emptyRow][emptyCol];
	        grid[emptyRow][emptyCol] = temp;
	    }
	}    
    

    //Metoda pentru mutarea unei piese. Se apeleaza atunci cand se da click pe o piesa
    public boolean moveTile(int row, int col) {
        int[] emptyPos = findEmptySpace();
        int emptyRow = emptyPos[0];
        int emptyCol = emptyPos[1];

        //Se verifica daca piesa pe care se da click e langa spatiul liber
        if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
            (Math.abs(col - emptyCol) == 1 && row == emptyRow)) {
            grid[emptyRow][emptyCol] = grid[row][col];
            grid[row][col] = 0;
            
            //Dupa fiecare mutare verifica daca e rezolvata, si afiseaza un mesaj in consola
            if(isSolved()) System.out.println(" SOLVED ");
            return true; 
        }
        return false;  
    }

    // Se verifica daca puzzle-ul e rezolvat. Se foloseste pentru varianta manuala
    public boolean isSolved() {
        int num = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] != num++ && !(i == gridSize - 1 && j == gridSize - 1 && grid[i][j] == 0)) {
                    return false; 
                }
            }
        } 
        
        return true; 
    }
    
     //Se verifica daca e rezolvata. Se foloseste pentru verificarea starilor intermediare, in timpul cautarii unei solutii
     public boolean isSolvedObj(int[][] gridB) {
        int num = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridB[i][j] != num++ && !(i == gridSize - 1 && j == gridSize - 1 && gridB[i][j] == 0)) {
                    return false;
                }
            }
        }
        return true;
    }
 
    
    //Metoda care cauta simuleaza mutarile pentru cautarea unei solutii. 
	 public List<Move> rezolvaPuzzle() {
	 	
	 	//Definim clasa State pentru a tine starea curenta: pozitia numerelor, pozitia spatiului liber, lista mutarilor deja facute si numarul de mutari
       class State {
	        int[][] grid;
	        int emptyRow, emptyCol;
	        List<Move> moves;
	        int cost;
	
	        State(int[][] grid, int emptyRow, int emptyCol, List<Move> moves, int cost) {
	            this.grid = grid;
	            this.emptyRow = emptyRow;
	            this.emptyCol = emptyCol;
	            this.moves = new ArrayList<>(moves);
	            this.cost = cost;
	        }
	    }


	 //Se stabileste costul fiecare stari, bazate pe numarul de mutari si pe distante
    PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
    Set<String> visited = new HashSet<>();
    int[] emptyPos = findEmptySpace();
    pq.add(new State(grid, emptyPos[0], emptyPos[1], new ArrayList<>(), 0));


	//Se exploreaza starile
    while (!pq.isEmpty()) {
        State current = pq.poll();
        if (isSolvedObj(current.grid)) {
            return current.moves;  
        }
	
		//Se omit starile care se repeta
        String stateKey = Arrays.deepToString(current.grid);
        if (visited.contains(stateKey)) continue;
        visited.add(stateKey);


			//De la spatiul liber, acestea sunt toate posibilitatile. Fizic se muta numerele in spatiul liber, dar ca logica de programare mutam spatiul liber intr-una din cele 4 directii
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = current.emptyRow + dir[0];
            int newCol = current.emptyCol + dir[1];


				//Se genereaza o stare noua
            if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                int[][] newGrid = cloneGrid(current.grid);
                newGrid[current.emptyRow][current.emptyCol] = newGrid[newRow][newCol];
                newGrid[newRow][newCol] = 0;

					//Se adauga noua stare a lista prioritatilor
                List<Move> newMoves = new ArrayList<>(current.moves);
                newMoves.add(new Move(newRow, newCol));

                int heuristic = calculateManhattanDistance(newGrid);
                pq.add(new State(newGrid, newRow, newCol, newMoves, newMoves.size() + heuristic));
            }
        }
    }

	 //Dacă nu se gaseste nici o solutie, se returneaza lista goala
    return new ArrayList<>();  //Returneaza o lista goala daca nu exista o solutie
}

	//Creeaza o copie a starii puzzle-ului, pentru simularea solutiei
    private int[][] cloneGrid(int[][] grid) {
        int[][] newGrid = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }


	 //Functie care calculeaza distanta Manhattan, pentru ordonarea prioritatilor
    private int calculateManhattanDistance(int[][] grid) {
        int distance = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int value = grid[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / gridSize;
                    int targetCol = (value - 1) % gridSize;
                    distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return distance;
    }    
    
    
    
    
}

