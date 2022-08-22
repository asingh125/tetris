import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.*; 
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel {
    private Grid gridMain; 
    private Grid gridNext;
    private Grid gridSaved; 
    
    private Piece currPiece; 
    private Piece savedPiece; 
    private LinkedList<Piece> nextPieces; 
    private int score;
    private boolean gameOver; 
    private boolean paused; 
    private final String playingMessage = "Playing..."; 
    private final String pausedMessage = "Paused"; 
    private final String gameOverMessage = "Game Over!"; 
    
    //Widths and heights
    public static final int PANEL_WIDTH = 500; //grid is indexed starting with 0
    public static final int PANEL_HEIGHT = 620; 
    public static final int GRID_WIDTH = 10; //grid is indexed starting with 0
    public static final int GRID_HEIGHT = 20; 
    public static final int SQUARE_SIDE = PANEL_HEIGHT / GRID_HEIGHT - 4; 
    private static int leftCol = (int) (PANEL_WIDTH * 0.66);
    
    //Starting position of a board shape
    public static final int START_X = GRID_WIDTH / 2 - 1;
    public static final int START_Y = 1;
    public static final Point START_POS = new Point(START_X, START_Y); 
    
    //Colors
    public static final Color LINE_COLOR = Color.BLACK; 
    public static final Color SQUARE_COLOR = Color.BLACK; 
    public static final Color LABEL_COLOR = Color.WHITE; 
    
    //Timer
    private Timer timer; 
    public static final int INTERVAL = 1000;
    
    //Game status
    private JLabel status;
    
    //Score file
    public static final String FILE = "files/Tetris_Scores.txt"; 
    
    public Board(JLabel status) {
        gridMain = new Grid(10,20,new Point(20,20)); 
        gridNext = new Grid(5,5,new Point(leftCol, (int) (PANEL_HEIGHT * 0.30))); 
        gridSaved = new Grid(5,5,new Point(leftCol, (int) (PANEL_HEIGHT * 0.65))); 
        
        nextPieces = new LinkedList<Piece>();
        addRandomPieceToList(); 
        addRandomPieceToList();
        addRandomPieceToList(); 
        addRandomPieceToList(); //add a few pieces to list to start
        currPiece = nextPieces.remove();
        gameOver = false;
        paused = false; 
        score = 0;
        this.status = status; 

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        //Make font bigger
        Font currFont = this.getFont(); 
        Font newFont = currFont.deriveFont((float) (currFont.getSize() * 2));
        this.setFont(newFont);
        
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && !paused) {
                    tick();
                }
            }
        });
        timer.start(); 

        setFocusable(true);
        
        //Key listener
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gameOver && !paused) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        shift(Direction.LEFT); //shift left
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        shift(Direction.RIGHT); //shift right
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        timer.setDelay(INTERVAL / 6); //speed up the falling speed
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        rotateCW(); //rotate the shape clockwise
                    } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        swapSavedPiece(); //swap saved piece with current piece
                    }
                    repaint(); 
                }
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    timer.setDelay(INTERVAL); //set falling speed back to normal
                } 
                repaint();
            }
        });
    }
    
    public void gameOver() {
        //Change necessary fields
        gameOver = true;
        status.setText(gameOverMessage);
        currPiece = null;
        
        //Fields for name dialog
        String anonName = "ANONYMOUS_USER"; 
        String userName = null;
        
        //While we don't have a userName
        while (userName == null) {
            
            //Display a name input dialog
            String name = JOptionPane.showInputDialog(this,
                    "Input your name to record your score. \n"
                    + "Leave textbox blank to remain anonymous. \n"
                    + "(No non-alphanumeric characters or whitespace): ", "");
            boolean invalidName = false;
            
            if (name == null || name.isEmpty()) {
                //If name is empty or null (user clicks cancel), set userName to anonymous
                userName = anonName; 
            } else {
                //Check if any characters are invalid
                for (int i = 0; i < name.length(); i++) {
                    if (!Character.isLetterOrDigit(name.charAt(i))) {
                        invalidName = true;
                    }
                }
                if (invalidName) {
                    //If name is invalid, display error message and re-enter while loop
                    JOptionPane.showMessageDialog(this, "ERROR: Non-alphanumeric characters or "
                            + "whitespace detected. Please re-enter name. ", "INVALID NAME", 
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    //Otherwise, set the user's input as the userName
                    userName = name; 
                } 
            }
        }
        try {
            writeNameAndScoreToFile(userName, score); //write to file
        } catch (IOException e) {
            //Display error message
            JOptionPane.showMessageDialog(this, "ERROR: There was an error. Score not "
                    + "recorded.", "ERROR", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void writeNameAndScoreToFile(String name, int score) throws IOException {
        //Either create or find file based on static name specified at top of file
        File file = new File(FILE); 
        if (!file.exists()) {
            file.createNewFile(); 
        }
        
        //Write to the file
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)); 
        bw.newLine(); 
        String s = name + " " + score; 
        bw.write(s); 
        System.out.println(s);
        bw.flush(); 
        bw.close();
    }
    
    /*
    public LinkedList<String> getHighScores() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE)); 
        LinkedList<String> lines = new LinkedList<String>(); 
        

        //Create a LinkedList of lines
        String line = br.readLine(); 
        while (!(line == null)) {
            String[] split = line.split(" ");
            if (split.length == 2) {
                lines.add(line); 
            }
            line = br.readLine(); 
        }
        br.close();
        
        
        
        int score1;
        int score2; 
        int score3; 
        
        String user1; 
        String user2; 
        String user3; 
            
    }
    
    private String findMaxScore(LinkedList<String> lines) {
        if (lines.isEmpty()) {
            return null;
        } else {
            String maxString = lines.remove(); 
            int maxScore = Integer.parseInt(maxString.split(" ")[1]); 
            
            for (int i = 0; i < lines.size(); i++) {

                String[] split = lines.get(i).split(" ");
                String score = split[1]; 
                if (score > maxScore)
            }
        }
    }
    */
    
    public static Map<String, LinkedList<Integer>> getScoreMap() 
            throws IOException, NumberFormatException {
        //This method returns a map that maps all users in the score file to a linkedlist of
        //all of their previous scores
        
        Map<String, LinkedList<Integer>> scoreMap = new TreeMap<String, LinkedList<Integer>>(); 
        BufferedReader br = new BufferedReader(new FileReader(FILE)); 
        
        //Iterate through lines in the file
        String line = br.readLine(); 
        while (!(line == null)) {
            String[] split = line.split(" ");
            if (split.length == 2) { //If split is the right length
                String name = split[0];
                
                int score = Integer.parseInt(split[1]); 
                //will throw a numberFormatException if the second entry does not contain a number
                
                if (!scoreMap.containsKey(name)) {
                    //If user is already in the map
                    LinkedList<Integer> scoreList = new LinkedList<Integer>(); 
                    scoreList.add(score); 
                    scoreMap.put(name, scoreList); 
                } else {
                    //Otherwise, if user is not yet in the map
                    scoreMap.get(name).add(score); 
                }
            }
            line = br.readLine(); 
        }
        br.close();
        return scoreMap; 
    }
    
    public void reset() {
        //Create a list of nextPieces
        nextPieces = new LinkedList<Piece>();
        addRandomPieceToList(); 
        addRandomPieceToList();
        addRandomPieceToList(); //add a few pieces to list to start
        currPiece = nextPieces.remove();
        score = 0;
        gameOver = false;
        paused = false; 
        status.setText(playingMessage); 
        gridMain.clearWell(); 
        repaint(); 
        requestFocusInWindow();
    }
    
    public void togglePause() {
        if (paused) {
            paused = false; 
            if (!gameOver) {
                status.setText(playingMessage);
                requestFocusInWindow();
            } else {
                status.setText(gameOverMessage);
            }
        } else {
            paused = true; 
            status.setText(pausedMessage);
        }
    }
    
    public Piece addRandomPieceToList() {
        Piece.Types[] t = Piece.Types.values(); 
        int randIndex = new Random().nextInt(t.length);
        Piece p = new Piece(new Point(START_X, START_Y), t[randIndex]); 
        nextPieces.add(p); 
        return p; 
    }
    
    public enum Direction { UP, DOWN, LEFT, RIGHT, NONE }; 
    
    public Direction wallCollision() {
        Point[] coords = currPiece.getGridCoords(); 
        
        //First, check for a DOWN or UP collision
        for (int i = 0; i < coords.length; i++) {
            Point p = coords[i];
            if (p.getB() >= GRID_HEIGHT) {
                return Direction.DOWN; 
            } else if (p.getB() <= -1) {
                return Direction.UP; 
            } 
        }
        
        //Then check for LEFT or RIGHT collision
        for (int i = 0; i < coords.length; i++) {
            Point p = coords[i];
            if (p.getA() <= -1) {
                return Direction.LEFT;
            } else if (p.getA() >= GRID_WIDTH) {
                return Direction.RIGHT; 
            }
        }
        
        //Otherwise, return NONE
        return Direction.NONE; 
    }
    
    public Direction wellCollision() {
        Point[] coords = currPiece.getGridCoords(); 
        for (int i = 0; i < coords.length; i++) {
            Point p = coords[i];
            
            //if that point is filled on the grid, return a DOWN collision
            if (!(gridMain.getWell()[p.getA()][p.getB()] == null)) { 
                return Direction.DOWN; 
            } 
        }
        //Else, return NONE
        return Direction.NONE; 
    }
    
    public void tick() {
        currPiece.shift(Direction.DOWN);
        Direction wall = wallCollision();  
        if (wall.equals(Direction.UP)) {
            gameOver(); 
        } else if (wall.equals(Direction.DOWN) || wellCollision().equals(Direction.DOWN)) {
            currPiece.shift(Direction.UP);
            stickPieceToBoard(); //sticks piece and sets currPiece to head of list
            addRandomPieceToList(); //add an extra random piece to the list
            deleteFullLines(); //delete all full lines in the board and add to score
        }
        repaint(); 
    }
    
    public boolean rotateCW() {
        currPiece.rotateCW();
        if (wallCollision().equals(Direction.NONE) && wellCollision().equals(Direction.NONE)) {
            return true; 
        } else {
            currPiece.rotateCCW();
            return false; 
        }
    }
    
    public boolean shift(Direction d) {
        currPiece.shift(d); 
        if (wallCollision().equals(Direction.NONE) && wellCollision().equals(Direction.NONE)) {
            return true; 
        } else {
            currPiece.shiftInverse(d);
            return false; 
        }
    }
    
    public void stickPieceToBoard() {
        gridMain.stickToGrid(currPiece);
        currPiece = nextPieces.remove(); 
        if (!wellCollision().equals(Direction.NONE)) {
            gameOver(); 
        }
    }
    
    public void deleteFullLines() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            boolean fullLine = true;
            int x = 0;
            
            //check to see if yth line is full
            while (fullLine && x < GRID_WIDTH) {
                if (gridMain.getWell()[x][y] == null) {
                    fullLine = false; 
                }
                x++;
            }
            
            //if line is full, set all colors in line to null and increment score
            if (fullLine) {
                for (int i = 0; i < GRID_WIDTH; i++) {
                    gridMain.setSquareColor(i,y,null); 
                }
                score++;
            }
        }
    }
    
    public void swapSavedPiece() {
        if (savedPiece == null) { 
            //if savedPiece is null, save current piece and take the head of the nextPieces list
            savedPiece = currPiece; 
            currPiece = nextPieces.remove(); 
        } else {
            //Otherwise swap currPiece and savedPiece
            Piece temp = currPiece;
            currPiece = savedPiece; 
            savedPiece = temp; 
        }
    }
    
    public void drawPiece(Graphics g, Piece p, Grid grid) {
        //Draw given piece relative to a given grid
        Point[] coords = p.getGridCoords(); 
        for (int i = 0; i < coords.length; i++) {
            Point pt = coords[i];
            fillSquare(g, pt.getA(), pt.getB(), p.getColor(), grid.getPos()); 
        }
    }
    
    public void drawGridLines(Graphics g, Grid grid) {
        //Draw the gridlines of the given grid
        for (int y = 0; y < grid.getDim().getB(); y++) {
            for (int x = 0; x < grid.getDim().getA(); x++) {
                g.setColor(LINE_COLOR);
                int xPos = grid.getPos().getA() + (x * SQUARE_SIDE); 
                int yPos = grid.getPos().getB() + (y * SQUARE_SIDE); 
                g.drawRect(xPos, yPos, SQUARE_SIDE, SQUARE_SIDE);
            }
        }
    }
    
    public void drawWell(Graphics g, Grid grid) {
        //Draw all of the color squares of the given grid's well
        for (int y = 0; y < grid.getDim().getB(); y++) {
            for (int x = 0; x < grid.getDim().getA(); x++) {
                if (!(grid.getWell()[x][y] == null)) {
                    fillSquare(g, x, y, grid.getWell()[x][y], grid.getPos()); 
                }
            }
        }
    }
    
    public void fillSquare(Graphics g, int gridX, int gridY, Color c, Point pos) {
        //Fill a square, given its x and y grid coords, its color, and its position
        g.setColor(c);
        int xPos = pos.getA() + (gridX * SQUARE_SIDE); 
        int yPos = pos.getB() + (gridY * SQUARE_SIDE); 
        g.fill3DRect(xPos, yPos, SQUARE_SIDE, SQUARE_SIDE, true);
    }
    
    public void drawGridBorder(Graphics g, Color c, Grid grid) {
        //Draw a border around the given grid
        int width = grid.getDim().getA() * SQUARE_SIDE;
        int height = grid.getDim().getB() * SQUARE_SIDE;
        g.setColor(c);
        g.drawRect(grid.getPos().getA(), grid.getPos().getB(), width, height);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Score
        Font currFont = this.getFont(); 
        Font newFont = currFont.deriveFont((float) (currFont.getSize() * 1.3));
        g.setFont(newFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + Integer.toString(score), leftCol, (int) (PANEL_HEIGHT * 0.1));
        g.setFont(currFont);
        
        //Main Display Grid
        if (!(currPiece == null)) {
            drawPiece(g, currPiece, gridMain); 
        }
        drawWell(g, gridMain); 
        drawGridLines(g, gridMain); 
        drawGridBorder(g, Color.WHITE, gridMain); 
        
        //Next Piece Display Grid
        int labY = (int) (gridNext.getPos().getB() - (PANEL_HEIGHT * 0.05));
        g.setColor(LABEL_COLOR);
        g.drawString("Next Shape:", gridNext.getPos().getA(), labY);
        Piece nextDisplay = new Piece(new Point(2,2), nextPieces.peek().getType()); 
        drawPiece(g, nextDisplay, gridNext); 
        drawGridLines(g, gridNext); 
        drawGridBorder(g, Color.WHITE, gridNext); 
        
        //Saved Piece Display Grid
        int labY2 = (int) (gridSaved.getPos().getB() - (PANEL_HEIGHT * 0.05));
        g.setColor(LABEL_COLOR);
        g.drawString("Saved Shape:", gridSaved.getPos().getA(), labY2);
        if (!(savedPiece == null)) {
            Piece savedDisplay = new Piece(new Point(2,2), savedPiece.getType()); 
            drawPiece(g, savedDisplay, gridSaved); 
        }
        drawGridLines(g, gridSaved); 
        drawGridBorder(g, Color.WHITE, gridSaved); 
    }

    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }
    
    //FOR TESTING ONLY
    public void fixCurrPiece(Piece p) {
        currPiece = p; 
    }
    
    //FOR TESTING ONLY
    public Color[][] getMainGrid() {
        return gridMain.getWell(); 
    }
    
}
