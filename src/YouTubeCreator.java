import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class YouTubeCreator {
    private char[][] grid;
    private int rows;
    private int cols;
    private List<String> words;

    public YouTubeCreator(int rows, int cols, List<String> words) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        this.words = words;
        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '-';
            }
        }
        placeWords();
        fillEmptySpaces();
    }

    private void placeWords() {
        Random random = new Random();
        for (String word : words) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(rows);
                int col = random.nextInt(cols);
                int direction = random.nextInt(4); // 0 for horizontal, 1 for vertical, 2 for diagonal (top-left to bottom-right), 3 for diagonal (top-right to bottom-left)

                if (direction == 0 && col + word.length() <= cols) {
                    // Check horizontal
                    if (HorizontalPlaceWord(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row][col + i] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 1 && row + word.length() <= rows) {
                    // Check vertical
                    if (VerticalPlaceWord(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row + i][col] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 2 && row + word.length() <= rows && col + word.length() <= cols) {
                    // Check diagonal (top-left to bottom-right)
                    if (DiagonallyTopLeftBottomRight(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row + i][col + i] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 3 && row + word.length() <= rows && col - word.length() >= -1) {
                    // Check diagonal (top-right to bottom-left)
                    if (DiagonallyTopRightBottomLeft(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row + i][col - i] = word.charAt(i);
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    private boolean HorizontalPlaceWord(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row][col + i] != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean VerticalPlaceWord(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col] != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean DiagonallyTopLeftBottomRight(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col + i] != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean DiagonallyTopRightBottomLeft(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col - i] != '-') {
                return false;
            }
        }
        return true;
    }

    private void fillEmptySpaces() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '-') {
                    grid[i][j] = (char) (random.nextInt(26) + 'A');
                }
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<String> getWords() {
        return words;
    }

    public String findWord(String word) {
        String position = findWordHorizontally(word);
        if (position != null) return position;
        position = findWordVertically(word);
        if (position != null) return position;
        position = findWordDiagonallyTopLeftBottomRight(word);
        if (position != null) return position;
        position = findWordDiagonallyTopRightBottomLeft(word);
        return position;
    }

    private String findWordHorizontally(String word) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - word.length(); j++) {
                int k;
                for (k = 0; k < word.length(); k++) {
                    if (grid[i][j + k] != word.charAt(k)) {
                        break;
                    }
                }
                if (k == word.length()) {
                    return "Word found horizontally from (" + i + ", " + j + ") to (" + i + ", " + (j + word.length() - 1) + ")";
                }
            }
        }
        return null;
    }

    private String findWordVertically(String word) {
        for (int i = 0; i <= rows - word.length(); i++) {
            for (int j = 0; j < cols; j++) {
                int k;
                for (k = 0; k < word.length(); k++) {
                    if (grid[i + k][j] != word.charAt(k)) {
                        break;
                    }
                }
                if (k == word.length()) {
                    return "Word found vertically from (" + i + ", " + j + ") to (" + (i + word.length() - 1) + ", " + j + ")";
                }
            }
        }
        return null;
    }

    private String findWordDiagonallyTopLeftBottomRight(String word) {
        for (int i = 0; i <= rows - word.length(); i++) {
            for (int j = 0; j <= cols - word.length(); j++) {
                int k;
                for (k = 0; k < word.length(); k++) {
                    if (grid[i + k][j + k] != word.charAt(k)) {
                        break;
                    }
                }
                if (k == word.length()) {
                    return "Word found diagonally (TL-BR) from (" + i + ", " + j + ") to (" + (i + word.length() - 1) + ", " + (j + word.length() - 1) + ")";
                }
            }
        }
        return null;
    }

    private String findWordDiagonallyTopRightBottomLeft(String word) {
        for (int i = 0; i <= rows - word.length(); i++) {
            for (int j = word.length() - 1; j < cols; j++) {
                int k;
                for (k = 0; k < word.length(); k++) {
                    if (grid[i + k][j - k] != word.charAt(k)) {
                        break;
                    }
                }
                if (k == word.length()) {
                    return "Word found diagonally (TR-BL) from (" + i + ", " + j + ") to (" + (i + word.length() - 1) + ", " + (j - word.length() + 1) + ")";
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("STARTPRACTICING");
        words.add("ARFINPARWEEN");
        words.add("ANKITBANSAL");
        words.add("DEBUGWITHSHUBHAM");
        words.add("GEEKCODERS");
        words.add("CODEBIX");
        words.add("DEWITHASHUTOSH");
        words.add("GAURAVSHARMA");
        words.add("TECHTFQ");
        words.add("SUMITMITTAL");
        words.add("TAKEUFORWARD");
        words.add("DATAWITHZACH");
        words.add("MANOHARBATRA");
        words.add("THEBIGDATASHOW");
        words.add("THEENGINEERGUY");
        words.add("DARSHILPARMAR");
        words.add("RPAFEED");
        words.add("BEAPROGRAMMER");
        words.add("DATASAVVY");
        words.add("ELEARNINGBRIDGE");
        words.add("FRAZ");
        words.add("INTERVIEWDEDO");
        words.add("TECHNICALSUNEJA");

        YouTubeCreator wordSearch = new YouTubeCreator(20, 35, words);
        wordSearch.printGrid();

        System.out.println("Words to find:");
        for (String word : wordSearch.getWords()) {
            System.out.println(word);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a word to find (or type 'exit' to quit):");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("EXIT")) {
                break;
            }

            String result = wordSearch.findWord(input);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Word not found.");
            }
        }
        scanner.close();
    }
}
