import java.util.*;

class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

enum PieceType {
    X,
    O;
}

class PlayingPiece {
    public PieceType pieceType;

    public PlayingPiece(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}

class Player {
    public String name;
    public PlayingPiece playingPiece;

    public Player(String name, PlayingPiece playingPiece) {
        this.name = name;
        this.playingPiece = playingPiece;
    }

    public String getPlayerName() {
        return this.name;
    }

    public PlayingPiece getPlayingPiece() {
        return this.playingPiece;
    }
}

class Board {
    public int size;
    public PlayingPiece[][] board;

    public Board(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
    }

    public boolean addPiece(int row, int col, PlayingPiece playingPiece) {
        if(row >= size || col >= size) return false;
        if(board[row][col] == null) {
            board[row][col] = playingPiece;
            return true;
        } else {
            return false;
        }
    }

    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] != null) {
                    System.out.print(board[i][j].pieceType.name() + " ");
                } else {
                    System.out.print("  ");
                }
                 System.out.print(" | ");
            }
            System.out.println();
        }
    }

    public List<Pair<Integer, Integer>> getFreeCells() {
        List<Pair<Integer, Integer>> freeCells = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] == null) {
                    Pair<Integer,Integer> rowCol = new Pair<>(i, j);
                    freeCells.add(rowCol);
                }
            }
        }
        return freeCells;
    }
}

class TicTacToeGame {
    Deque<Player> players;
    Board gameboard;

    public void initializeGame() {
        players = new LinkedList<>();
        PlayingPiece pieceX = new PlayingPiece(PieceType.X);
        Player p1 = new Player("P1", pieceX);

        PlayingPiece pieceO = new PlayingPiece(PieceType.O);
        Player p2 = new Player("P2", pieceO);

        players.add(p1);
        players.add(p2);

        gameboard = new Board(3);
    }

    public String startGame() {
        boolean noWinner = true;
        while(noWinner) {
            Player currPlayer = players.removeFirst();
            gameboard.printBoard();
            List<Pair<Integer, Integer>> freeSpaces = gameboard.getFreeCells();
            if(freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }

            System.out.println("Player: "+ currPlayer.name+ " enter row: ");
            Scanner sc = new Scanner(System.in);
            int inputRow = sc.nextInt();
            System.out.println("Player: "+ currPlayer.name+  " enter col: ");
            int inputCol = sc.nextInt();

            boolean pieceAddedSuccessfully = gameboard.addPiece(inputRow, inputCol, currPlayer.playingPiece);
            if(!pieceAddedSuccessfully) {
                System.out.println("Incorrect position, try again!");
                players.addFirst(currPlayer);
                continue;
            }
            players.addLast(currPlayer);

            boolean winner = isWinner(inputRow, inputCol, currPlayer.playingPiece.pieceType);
            if(winner) {
                return currPlayer.name;
            }
        }
        return "Tie";
    }

    public boolean isWinner(int row, int col, PieceType pieceType) {
        boolean rowMatch = true;
        boolean colMatch = true;
        boolean firstDiagMatch = true;
        boolean secondDiagMatch = true;

        for(int i = 0; i < gameboard.size; i++) {
            if(gameboard.board[row][i] == null || gameboard.board[row][i].pieceType != pieceType) {
                rowMatch = false;
            }
        }
        for(int i = 0; i < gameboard.size; i++) {
            if(gameboard.board[i][col] == null || gameboard.board[i][col].pieceType != pieceType) {
                colMatch = false;
            }
        }
        for(int i = 0, j = 0; i < gameboard.size; i++, j++) {
            if(gameboard.board[i][j] == null || gameboard.board[i][j].pieceType != pieceType) {
                firstDiagMatch = false;
            }
        }
        for(int i = 0, j = gameboard.size - 1; i < gameboard.size; i++, j--) {
            if(gameboard.board[i][j] == null || gameboard.board[i][j].pieceType != pieceType) {
                secondDiagMatch = false;
            }
        }

        return rowMatch || colMatch || firstDiagMatch || secondDiagMatch;
    }

}

class Main {
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        game.initializeGame();
        System.out.println("WINNER IS: " + game.startGame());
    }
}