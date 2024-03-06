import java.util.Scanner;

public class CaveExplorer {
    private char[][] cave;
    private int startRow;
    private int startCol;
    private int numRows;
    private int numCols;

    public CaveExplorer() {
        String layout = "RRRRRR\n" +
                "R..SRR\n" +
                "R.RRRR\n" +
                "R.MRRR\n" +
                "RRRRRR\n";
        initializeCave(layout);
    }

    public CaveExplorer(String fileName) {
        Scanner scanner = new Scanner(fileName);
        numRows = scanner.nextInt();
        numCols = scanner.nextInt();
        scanner.nextLine();

        StringBuilder layoutBuilder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            layoutBuilder.append(scanner.nextLine()).append("\n");
        }

        initializeCave(layoutBuilder.toString());
    }

    private void initializeCave(String layout) {
        cave = new char[numRows][numCols];
        Scanner scanner = new Scanner(layout);
        for (int i = 0; i < numRows; i++) {
            String row = scanner.nextLine();
            for (int j = 0; j < numCols; j++) {
                cave[i][j] = row.charAt(j);

                if (cave[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result.append(cave[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    public boolean solve() {
        return explore(startRow, startCol);
    }

    private boolean explore(int row, int col) {
        if (cave[row][col] == 'M') {
            return true;
        }

        cave[row][col] = 'V';
        if (row > 0 && cave[row - 1][col] != 'R' && cave[row - 1][col] != 'V' && explore(row - 1, col)) {
            return true; // Move north
        }
        if (row < numRows - 1 && cave[row + 1][col] != 'R' && cave[row + 1][col] != 'V' && explore(row + 1, col)) {
            return true; // Move south
        }
        if (col > 0 && cave[row][col - 1] != 'R' && cave[row][col - 1] != 'V' && explore(row, col - 1)) {
            return true; // Move west
        }
        if (col < numCols - 1 && cave[row][col + 1] != 'R' && cave[row][col + 1] != 'V' && explore(row, col + 1)) {
            return true; // Move east
        }

        return false;
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        findPath(startRow, startCol, path);
        return path.toString();
    }

    private void findPath(int row, int col, StringBuilder path) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || cave[row][col] == 'R' || cave[row][col] == 'V') {
            return;
        }

        if (cave[row][col] == 'M') {
            return;
        }

        cave[row][col] = 'V';

        if (row > 0 && cave[row - 1][col] == 'V') {
            path.append('N');
            findPath(row - 1, col, path);
        } else if (row < numRows - 1 && cave[row + 1][col] == 'V') {
            path.append('S');
            findPath(row + 1, col, path);
        } else if (col > 0 && cave[row][col - 1] == 'V') {
            path.append('W');
            findPath(row, col - 1, path);
        } else if (col < numCols - 1 && cave[row][col + 1] == 'V') {
            path.append('E');
            findPath(row, col + 1, path);
        }
    }

    public static void main(String[] args) {

        CaveExplorer explorer1 = new CaveExplorer();
        System.out.println("Initial Layout:\n" + explorer1.toString());
        System.out.println("Is there a path to the mirror pool? " + explorer1.solve());
        System.out.println("Final Layout:\n" + explorer1.toString());
        System.out.println("Path taken: " + explorer1.getPath());
        System.out.println();

        CaveExplorer explorer2 = new CaveExplorer("cave_layout.txt");
        System.out.println("Initial Layout:\n" + explorer2.toString());
        System.out.println("Is there a path to the mirror pool? " + explorer2.solve());
        System.out.println("Final Layout:\n" + explorer2.toString());
        System.out.println("Path taken: " + explorer2.getPath());
    }
}
