package numberPuzzle;

public class Move {
    public int x; // Attribute x
    public int y; // Attribute y

    // Constructor to initialize x and y
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Override toString for easier display
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}