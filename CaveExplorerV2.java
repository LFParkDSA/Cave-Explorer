import java.io.File;
import java.util.Scanner;

public class CaveExplorer {
    private char[][] cave;
    private int explorerRow;
    private int explorerCol;

    public CaveExplorerV2() {
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

    public CaveExplorerV2(String fileName) throws Exception {
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
        boolean foundMirror = explore(explorerRow, explorerCol);
        return foundMirror;
    }

    private boolean explore(int row, int col) {
        if (cave[row][col] == 'M') {
            return true;
        }

        cave[row][col] = 'V';

        // Check all four directions
        if (isValid(row - 1, col) && explore(row - 1, col)) {
            return true; // North
        }
        if (isValid(row, col + 1) && explore(row, col + 1)) {
            return true; // East
        }
        if (isValid(row + 1, col) && explore(row + 1, col)) {
            return true; // South
        }
        if (isValid(row, col - 1) && explore(row, col - 1)) {
            return true; // West
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
    CaveExplorer explorer1 = new CaveExplorerV2();
    System.out.println("Starting Cave 1:");
    System.out.println(explorer1);

    if (explorer1.solve()) {
        System.out.println("Final Cave 1:");
        System.out.println(explorer1);
        System.out.println("Path Taken in Cave 1: " + explorer1.getPath());
    } else {
        System.out.println("No path found in Cave 1");
    }
    CaveExplorer explorer2 = new CaveExplorerV2("cave.txt");
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