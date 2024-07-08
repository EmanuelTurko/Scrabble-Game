package test;


import java.util.Arrays;
import java.util.ArrayList;


public class Board {


    private static final int BoardSize = 15;
    private static final char Star = '*';
    private static final char Teal = 'T';
    private static final char Blue = 'B';
    private static final char Yellow = 'Y';
    private static final char Red = 'R';
    private static final char Green = 'G';
    private static Board instance = null;
    private char[][] board;
    private final boolean[][] visited = new boolean[BoardSize][BoardSize];
    private int wordMultiplier = 1;
    private int Multiplier = 1;
    private boolean StarVisited = false;

    private Board() {
        initializeBoard();
    }
    private void initializeVisited()
    {
        for(int i = 0; i < BoardSize ; i++)
        {
            Arrays.fill(visited[i],false);
        }
    }

    public static Board getBoard() {
        if (instance == null) {
            instance = new Board();
            instance.initializeVisited();
        }
        return instance;
    }

    private void initializeBoard() {
        board = new char[BoardSize][BoardSize];
        char[][] squareTypes = new char[BoardSize][BoardSize];

        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                board[i][j] = ' ';
                squareTypes[i][j] = SquareType(i,j);
            }
        }
        initializeVisited();
    }

    private char SquareType(int row, int col) {
        if(StarVisited && row == 7 && col == 7)
            return Green;

       else if(row == 7 && col == 7) {
            return Star; // word x2
        } else if (isTeal(row, col)) {
            return Teal; // letter x2
        } else if (isBlue(row, col)) {
            return Blue; // letter x3
        } else if (isYellow(row, col)) {
            return Yellow;  // word x2
        } else if (isRed(row, col)) {
            return Red;     // word x3
        } else {
            return Green;
        }
    }

    private boolean isTeal(int row, int col) {
        int[][] tealTilePositions = {{0, 3}, {0, 11}, {3, 0}, {2, 6}, {2, 8}, {3, 7}, {3, 14}, {6, 2}, {6, 6}, {6, 8}, {6, 12}, {7, 3}, {7, 11}, {8, 2}, {8, 6}, {8, 8}, {8, 10}, {11, 0}, {11, 7}, {11, 14}, {12, 6}, {12, 8}, {14, 2}, {14, 11}};
        for (int[] position : tealTilePositions) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }

    private boolean isBlue(int row, int col) {
        int[][] blueTilePositions = {{1, 5}, {1, 9}, {5, 1}, {5, 5}, {5, 9}, {5, 13}, {9, 1}, {9, 5}, {9, 9}, {9, 13}, {13, 5}, {13, 9}};
        for (int[] position : blueTilePositions) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }

    private boolean isYellow(int row, int col) {
        int[][] yellowTilePositions = {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {1, 13}, {2, 12}, {3, 11}, {4, 10}, {10, 4}, {11, 3}, {12, 2}, {13, 1}, {10, 10}, {11, 11}, {12, 12}, {13, 13}};
        for (int[] position : yellowTilePositions) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }

    private boolean isRed(int row, int col) {
        int[][] redTilePositions = {{0, 0}, {0, 7}, {0, 14}, {7, 0}, {7, 14}, {14, 0}, {14, 7}, {14, 14}};
        for (int[] position : redTilePositions) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }


    public Tile[][] getTiles() {
        Tile[][] tiles = new Tile[BoardSize][BoardSize];
        for (int i = 0; i < BoardSize; i++) {
            System.arraycopy(board[i], 0, tiles[i], 0, BoardSize);
        }
        return tiles;
    }


    public boolean isInBound(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int len = word.getTile().length;
        boolean isVertical = word.isVertical();
        if (row >= 0 && row < BoardSize && col >= 0 && col < BoardSize) {
            if (isVertical) {
                return row + len <= BoardSize;
            } else {
                return col + len <= BoardSize;
            }
        }
        return false;
    }

    public boolean boardLegal(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int len = word.getTile().length;
        boolean isVertical = word.isVertical();
        if (isInBound(word)) {
            if (isVertical) {
                if (col == 7 && row <= 7 && row+len >= 7) {
                    return true;
                }
                for (int i = row; i < len; i++) {
                    if (board[row+i][col] != ' ') {
                        return true;
                    }
                }
                for (int i = col; i < len+row; i++){
                    if ((board[i][col+1] != ' ')||(board[i][col-1] != ' ')) {
                        return true;
                    }
                }
            }
            else{
                if (row == 7 && col <= 7 && col+len >= 7) {
                    return true;
                }
                for (int i = col; i < len; i++) {
                    if (board[row][col+i] != ' ') {
                        return true;
                    }
                }
                for (int i = col; i < len+col; i++){
                    if ((board[row-1][i] != ' ')||(board[row+1][i] != ' ')) {
                        return true;
                    }
                }
            }
        }
        return false;
}

    private boolean dictionaryLegal(Word word) {
        return true;
    }

    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> newWords = new ArrayList<>();
        newWords.add(word);
        int row = word.getRow();
        int col = word.getCol();
        int len = word.getTile().length;
        if (word.isVertical()) {

            for (int i = row; i < len; i++) {
                if (board[row+i][col] != ' '&& word.getTile()[i-row]!=null) {
                    // Check if a new word is created horizontally
                    int j = col;
                    while (j >= 0 && board[i][j] != ' ') {
                        j--;
                    }
                    j++;
                    int start = j;
                    while (j < BoardSize && board[i][j] != ' ') {
                        j++;
                    }
                    if (j - start > 1) {
                        Tile[] newTiles = new Tile[j - start];
                        for (int k = start; k < j; k++) {
                            char letter = board[k][i];
                            newTiles[k - start] = new Tile(0,letter);
                        }
                        newWords.add(new Word(newTiles, i, start, false));
                    }
                }
            }
        } else {
            for (int i = col; i < len+col; i++) {
                if (board[row][i] != ' ' && word.getTile()[i-col]!= null) {
                    // Check if a new word is created vertically
                    int j = row;
                    while (j >= 0 && board[j][i] != ' ') {
                        j--;
                    }
                    j++;
                    int start = j;
                    while (j < BoardSize && board[j][i] != ' ') {
                        j++;
                    }
                    if (j - start > 1) {
                        Tile[] newTiles = new Tile[j - start];
                        for (int k = start; k < j; k++) {
                            char letter = board[k][i];
                            newTiles[k - start] = new Tile(0,letter);
                        }
                        newWords.add(new Word(newTiles, start, i, true));
                    }
                }
            }
        }
        // Implement logic to find new words created by placing the given word
        return newWords;
}




    private int getScore(Word word) {
        int wordScore = 0;
        wordMultiplier = 1;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        Tile[] tiles = word.getTile();

        for (Tile tile : tiles) {
            if(tile == null)
            {
                char letter;
                letter = board[row][col];
                int letterIndex = (letter - 'A');
                int[] scores = Tile.Bag.getBag().getScoresArray();
                int letterScore = scores[letterIndex];
                wordScore += letterScore;
                if(vertical)
                {
                    row++;
                }
                else
                {
                    col++;
                }
            }
            if (tile != null) {
                int letterIndex = (tile.letter - 'A');
                int[] scores = Tile.Bag.getBag().getScoresArray();

                // Calculate the score for the current tile
                int tileScore = scores[letterIndex];

                // Check if the tile is on a bonus square
                switch (SquareType(row, col)) {
                    case Star:
                        wordMultiplier = 2;
                        StarVisited = true;
                        break;
                    case Teal:
                        tileScore *= 2;
                        break;
                    case Blue:
                        tileScore *= 3;
                        break;
                    case Yellow:
                        wordMultiplier *= 2;
                        break;
                    case Red:
                        wordMultiplier *= 3;
                        break;
                    default:
                        // Regular square, no bonus
                        break;
                }

                // Add the score of the current tile to the total word score
                wordScore += tileScore;

                // Move to the next square (depending on a word direction)
                if (vertical)
                    row++;
                else
                    col++;
            }
        }
        return wordScore;
    }


    public int tryPlaceWord(Word word) {
        int score = 0;

        // Check if the word placement is legal on the board
        if (!boardLegal(word))
            return 0;

        // Convert tiles to a string
        StringBuilder wordString = new StringBuilder();
        Tile[] tiles = word.getTile(); // Get the tile array once for efficiency
        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];
            if (tile != null) {
                wordString.append(tile.letter);
            } else {
                if(word.isVertical())
                {
                    int j = word.getRow() + i;
                    int k = word.getCol();
                    wordString.append(board[j][k]);
                }
                else
                {
                    int j = word.getRow();
                    int k = word.getCol() + i;
                    wordString.append(board[j][k]);
                }
            }
        }


        // Place the word on the board
        if (word.isVertical()) {
            int j = word.getCol();
            for (int i = word.getRow(); i < word.getRow() + word.getTile().length; i++) {
                if (board[i][j] == ' ') {
                    board[i][j] = wordString.charAt(i - word.getRow());
                }
            }
        } else {
            int i = word.getRow();
            for (int j = word.getCol(); j < word.getCol() + word.getTile().length; j++) {
                if (board[i][j] == ' ' && wordString.charAt(j - word.getCol()) != ' ') {
                    board[i][j] = wordString.charAt(j - word.getCol());
                }
            }
        }

        // Check if the word is legal, according to the dictionary
        if (!dictionaryLegal(word)) {
            return -1; // Word is not legal, according to the dictionary
        }

        // Get unique words from the board
        ArrayList<Word> newWords = getWords(word);

        // Calculate the score for the word placement
        for (Word w : newWords) {
            score += getScore(w);
            if (wordMultiplier > 1) {
                Multiplier = wordMultiplier;
            }
            score *= Multiplier;
            Multiplier = 1;
        }

        return score;
    }

}

