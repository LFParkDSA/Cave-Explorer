import java.io.File;
import java.util.Scanner;

public class CaveExplorer {
    private char[][] cave;
    private int explorerRow;
    private int explorerCol;

    public CaveExplorerV4() {
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

    public CaveExplorerV4(String fileName) throws Exception {
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
        findPath(startRow, startCol, path, new HashSet<>());
        return path.toString();
    }

    private boolean findPath(int row, int col, StringBuilder path, Set<String> visited) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || cave[row][col] == 'R'
                || visited.contains(row + "-" + col)) {
            return false;
        }

        if (cave[row][col] == 'M') {
            path.append("M");
            return true;
        }

        visited.add(row + "-" + col);

        // Try moving in all four directions
        if (findPath(row - 1, col, path.append("N"), visited)) {
            return true;
        } else if (findPath(row + 1, col, path.deleteCharAt(path.length() - 1).append("S"), visited)) {
            return true;
        } else if (findPath(row, col - 1, path.deleteCharAt(path.length() - 1).append("W"), visited)) {
            return true;
        } else if (findPath(row, col + 1, path.deleteCharAt(path.length() - 1).append("E"), visited)) {
            return true;
        }

        visited.remove(row + "-" + col);
        return false;
    }

    public static void main(String[] args) {
        CaveExplorer explorer3 = new CaveExplorerV4("branching_layout.txt");
        System.out.println("Initial Layout:\n" + explorer3.toString());
        System.out.println("Is there a path to the mirror pool? " + explorer3.solve());
        System.out.println("Final Layout:\n" + explorer3.toString());
        System.out.println("Path taken: " + explorer3.getPath());
        System.out.println();
    }
}
