import java.io.File;
import java.util.Scanner;

public class CaveExplorer {
    private char[][] cave;
    private int explorerRow;
    private int explorerCol;

    public CaveExplorerV3() {
        cave = new char[][] {
                { 'R', 'R', 'R', 'R', 'R', 'R' },
                { 'R', '.', '.', 'S', 'R', 'R' },
                { 'R', '.', 'R', 'R', 'R', 'R' },
                { 'R', '.', 'M', 'R', 'R', 'R' },
                { 'R', 'R', 'R', 'R', 'R', 'R' }
        };
        explorerRow = 1;
        explorerCol = 3;
    }

    public CaveExplorerV3(String fileName) throws Exception {
        Scanner scanner = new Scanner(new File(fileName));
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        cave = new char[rows][cols];
        scanner.nextLine();

        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < cols; j++) {
                cave[i][j] = line.charAt(j);
                if (cave[i][j] == 'S') {
                    explorerRow = i;
                    explorerCol = j;
                }
            }
        }
        scanner.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cave.length; i++) {
            for (int j = 0; j < cave[i].length; j++) {
                sb.append(cave[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean solve() {
        Stack<Integer[]> movesStack = new Stack<>();
        return explore(startRow, startCol, movesStack);
    }

    private boolean explore(int row, int col, Stack<Integer[]> movesStack) {
        // Check if we've reached the mirror pool
        if (cave[row][col] == 'M') {
            return true;
        }

        // Mark the current position as visited
        cave[row][col] = 'V';

        // Try moving in all four directions
        if (moveAndExplore(row - 1, col, movesStack)) {
            return true; // Move north
        }
        if (moveAndExplore(row + 1, col, movesStack)) {
            return true; // Move south
        }
        if (moveAndExplore(row, col - 1, movesStack)) {
            return true; // Move west
        }
        if (moveAndExplore(row, col + 1, movesStack)) {
            return true; // Move east
        }

        // No path found in any direction, backtrack
        if (!movesStack.isEmpty()) {
            Integer[] lastMove = movesStack.pop();
            return explore(lastMove[0], lastMove[1], movesStack);
        }

        return false;
    }

    private boolean moveAndExplore(int newRow, int newCol, Stack<Integer[]> movesStack) {
        if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols
                && cave[newRow][newCol] != 'R' && cave[newRow][newCol] != 'V') {
            movesStack.push(new Integer[] { newRow, newCol });
            return explore(newRow, newCol, movesStack);
        }
        return false;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < cave.length && col >= 0 && col < cave[0].length && cave[row][col] != 'R'
                && cave[row][col] != 'V';
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        int row = explorerRow;
        int col = explorerCol;

        while (cave[row][col] != 'S') {
            if (cave[row - 1][col] == 'V') {
                path.append('N');
                row--;
            } else if (cave[row][col + 1] == 'V') {
                path.append('E');
                col++;
            } else if (cave[row + 1][col] == 'V') {
                path.append('S');
                row++;
            } else if (cave[row][col - 1] == 'V') {
                path.append('W');
                col--;
            }
        }

        return path.reverse().toString();
    }

    public static void main(String[] args) throws Exception {
    CaveExplorer explorer1 = new CaveExplorerV3();
    System.out.println("Starting Cave 1:");
    System.out.println(explorer1);

    if (explorer1.solve()) {
        System.out.println("Final Cave 1:");
        System.out.println(explorer1);
        System.out.println("Path Taken in Cave 1: " + explorer1.getPath());
    } else {
        System.out.println("No path found in Cave 1");
    }
    CaveExplorer explorer2 = new CaveExplorerV3("cave.txt");
    System.out.println("\nStarting Cave 2 (from file):");
    System.out.println(explorer2);

    if (explorer2.solve()) {
        System.out.println("Final Cave 2:");
        System.out.println(explorer2);
        System.out.println("Path Taken in Cave 2: " + explorer2.getPath());
    } else {
        System.out.println("No path found in Cave 2");
    }
}