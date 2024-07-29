import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class YouTubeCreator {
    private char[][] grid;
    private int gridSize;
    private List<String> words;

    public YouTubeCreator(int gridSize, List<String> words) {
        this.gridSize = gridSize;
        this.grid = new char[gridSize][gridSize];
        this.words = words;
        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
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
                int row = random.nextInt(gridSize);
                int col = random.nextInt(gridSize);
                int direction = random.nextInt(4); // 0 for horizontal, 1 for vertical, 2 for diagonal (top-left to bottom-right), 3 for diagonal (top-right to bottom-left)

                if (direction == 0 && col + word.length() <= gridSize) {
                    // Check horizontal
                    if (HorizontalPlaceWord(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row][col + i] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 1 && row + word.length() <= gridSize) {
                    // Check vertical
                    if (VerticalPlaceWord(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row + i][col] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 2 && row + word.length() <= gridSize && col + word.length() <= gridSize) {
                    // Check diagonal (top-left to bottom-right)
                    if (DiagonallyTLBR(word, row, col)) {
                        for (int i = 0; i < word.length(); i++) {
                            grid[row + i][col + i] = word.charAt(i);
                        }
                        placed = true;
                    }
                } else if (direction == 3 && row + word.length() <= gridSize && col - word.length() >= -1) {
                    // Check diagonal (top-right to bottom-left)
                    if (DiagonallyTRBL(word, row, col)) {
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

    private boolean DiagonallyTLBR(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col + i] != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean DiagonallyTRBL(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col - i] != '-') {
                return false;
            }
        }
        return true;
    }

    private void fillEmptySpaces() {
        Random random = new Random();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == '-') {
                    grid[i][j] = (char) (random.nextInt(26) + 'A');
                }
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
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
        position = findWordDiagonallyTLBR(word);
        if (position != null) return position;
        position = findWordDiagonallyTRBL(word);
        return position;
    }

    private String findWordHorizontally(String word) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j <= gridSize - word.length(); j++) {
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
        for (int i = 0; i <= gridSize - word.length(); i++) {
            for (int j = 0; j < gridSize; j++) {
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

    private String findWordDiagonallyTLBR(String word) {
        for (int i = 0; i <= gridSize - word.length(); i++) {
            for (int j = 0; j <= gridSize - word.length(); j++) {
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

    private String findWordDiagonallyTRBL(String word) {
        for (int i = 0; i <= gridSize - word.length(); i++) {
            for (int j = word.length() - 1; j < gridSize; j++) {
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
        words.add("DEWITHASHUTOSH");
        words.add("GEEKCODERS");
        words.add("CODEBIX");
        words.add("THEBIGDATASHOW");
        words.add("THEENGINEERGUY");
        words.add("GAURAVSHARMA");
        words.add("TECHTFQ");
        words.add("SUMITMITTAL");
        words.add("DATASAVVY");
        words.add("TAKEUFORWARD");
        words.add("DATAWITHZACH");
        words.add("MANOHARBATRA");
        words.add("DARSHILPARMAR");
        words.add("RPAFEED");
        words.add("BEAPROGRAMMER");
        words.add("ELEARNINGBRIDGE");
        words.add("FRAZ");
        words.add("INTERVIEWDEDO");

        YouTubeCreator wordSearch = new YouTubeCreator(25, words);
        wordSearch.printGrid();

        System.out.println("Words to find:");
        for (String word : wordSearch.getWords()) {
            System.out.println(word);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a word to find in the grid:");
        String wordToFind = scanner.nextLine();
        String result = wordSearch.findWord(wordToFind);
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Word not found.");
        }
        scanner.close();
    }
}
