import java.util.Random;

// DO NOT MODIFY THIS FILE.
// WEBCAT'S REFERENCE TESTS WILL EXPECT THIS EXACT EXECUTION

/**
 * @author Patrick Sullivan
 * @version 0.1.0
 */
public class ChessFaker {

    static final char[] PIECES = "KQRRBBNNPPPP".toCharArray();
    static final char[] RANKS = "12345678".toCharArray();
    static final char[] FILES = "abcdefgh".toCharArray();

    static final char[] BOARD_CODES = "ijklmnopqrstuvwxyz".toCharArray();
    static final int BOARD_SIZE = 12;

    /**
     * For a given board state, get every possible valid move
     * 
     * @param board
     *            The current board state
     * 
     * @return
     *         An array of strings, each being a valid move.
     *         There are always between 1 and 12 moves.
     */
    public static String[] getNextMoves(String board) {
        Random rand = getRandomFor(board);
        int numNewMoves = rand.nextInt(11) + 1;
        String[] nextMoves = new String[numNewMoves];

        for (int i = 0; i < numNewMoves; i++) {
            char piece = PIECES[rand.nextInt(PIECES.length)];
            char rank = RANKS[rand.nextInt(RANKS.length)];
            char file = FILES[rand.nextInt(FILES.length)];
            char[] moveChars = { piece, file, rank };
            nextMoves[i] = new String(moveChars);
        }

        return nextMoves;
    }


    /**
     * From a starting board state and planned move, get the next board state
     * 
     * @param board
     *            The current current board state
     * @param move
     *            The planned move to make
     * 
     * @return
     *         The next board state
     */
    public static String getNextBoard(String board, String move) {
        Random rand = getRandomFor(board, move);

        char[] newBoard = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            int codeIndex = rand.nextInt(BOARD_CODES.length);
            newBoard[i] = BOARD_CODES[codeIndex];
        }
        return new String(newBoard);
    }


    /**
     * From a starting board state and planned move, calculate change in fitness
     * 
     * @param oldBoard
     *            The PREVIOUS board state
     * @param move
     *            The move made to reach the current state
     * 
     * @return
     *         The fitness of the CURRENT board
     */
    public static int getFitnessChange(String oldBoard, String move) {
        Random rand = getRandomFor(oldBoard, move);
        int fitChange = (int)(rand.nextGaussian() * 20);
        return Integer.min(100, Integer.max(fitChange, -100));
    }


    // Here are the very weak (but fast) pseudo-random generators.
    private static Random getRandomFor(String seedStr) {
        long hash = seedStr.hashCode();
        return new Random(hash);
    }


    private static Random getRandomFor(String seed1, String seed2) {
        long hash = seed1.hashCode() * seed2.hashCode();
        return new Random(hash);
    }

}
