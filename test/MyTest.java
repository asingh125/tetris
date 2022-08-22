//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class MyTest {
//
//    // GRID TESTS
//
//    //Stick to grid
//    @Test
//    public void stickToGridTest() {
//
//        //In bounds does stick
//        Grid g = new Grid(2,2, new Point(0,0));
//        Piece p = new Piece(new Point(0,0), Piece.Types.O);
//        g.stickToGrid(p);
//        Color[][] actual = g.getWell();
//        Color[][] expected = {{Color.yellow, Color.yellow}, {Color.yellow, Color.yellow}};
//
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                assertEquals(actual[i][j], expected[i][j]);
//            }
//        }
//
//        //Out of bounds doesn't stick
//        Grid g2 = new Grid(2,2, new Point(0,0));
//        Piece p2 = new Piece(new Point(3,4), Piece.Types.O);
//        g2.stickToGrid(p2);
//        Color[][] actual2 = g2.getWell();
//
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                assertNull(actual2[i][j]);
//            }
//        }
//    }
//
//    //getWell
//    @Test
//    public void getWellTest() {
//        //Empty
//        Grid g = new Grid(3,3, new Point(0,0));
//        Color[][] actual = g.getWell();
//        Color[][] expected = new Color[3][3];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                assertEquals(actual[i][j], expected[i][j]);
//            }
//        }
//
//        //Non-empty
//        Grid g2 = new Grid(4,1, new Point(0,0));
//        Piece p = new Piece(new Point(1,0), Piece.Types.I);
//        g2.stickToGrid(p);
//        Color[][] actual2 = g2.getWell();
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 1; j++) {
//                assertEquals(actual2[i][j], Color.cyan);
//            }
//        }
//
//        //Encapsulation
//        Grid g3 = new Grid(2,2, new Point(0,0));
//        Piece p3 = new Piece(new Point(0,0), Piece.Types.O);
//        g.stickToGrid(p3);
//        Color[][] actual3 = g.getWell();
//        Color[][] expected3 = {{Color.yellow, Color.yellow}, {Color.yellow, Color.yellow}};
//        actual3[0][0] = null;
//        actual3[0][1] = Color.red;
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                assertEquals(g.getWell()[i][j], expected3[i][j]);
//            }
//        }
//    }
//
//    //set square color
//    @Test
//    public void setSquareColorTest() {
//        Grid g = new Grid(3,2, new Point(0,0));
//        g.stickToGrid(new Piece(new Point(1,1), Piece.Types.T));
//        Color[][] expected = g.getWell();
//
//        //Setting to same color
//        g.setSquareColor(0, 1, Color.magenta);
//        expected[0][1] = Color.magenta;
//        for (int i = 0; i < g.getDim().getA(); i++) {
//            for (int j = 0; j < g.getDim().getB(); j++) {
//                assertEquals(g.getWell()[i][j], expected[i][j]);
//            }
//        }
//
//        //Setting to different color
//        g.setSquareColor(1, 0, Color.black);
//        expected[1][0] = Color.black;
//        for (int i = 0; i < g.getDim().getA(); i++) {
//            for (int j = 0; j < g.getDim().getB(); j++) {
//                assertEquals(g.getWell()[i][j], expected[i][j]);
//            }
//        }
//
//        //Setting a square that is out of bounds
//        Color[][] before = g.getWell();
//        g.setSquareColor(30, 25, Color.pink);
//        for (int i = 0; i < g.getDim().getA(); i++) {
//            for (int j = 0; j < g.getDim().getB(); j++) {
//                assertEquals(g.getWell()[i][j], before[i][j]);
//            }
//        }
//    }
//
//    //get pos
//    @Test
//    public void getPosTest() {
//        //Normal functionality
//        Grid g = new Grid(3,2, new Point(0,0));
//        Point p = g.getPos();
//        assertEquals(p, new Point(0,0));
//
//        Grid g2 = new Grid(1,1, new Point(30,-90));
//        Point p2 = g2.getPos();
//        assertEquals(p2, new Point(30,-90));
//
//        //Encapsulation
//        p.shift(-20, 400);
//        assertEquals(g.getPos(), new Point(0,0));
//    }
//
//    //get dim
//    @Test
//    public void getDimTest() {
//        //Normal functionality
//        Grid g = new Grid(3,2, new Point(0,0));
//        Point d = g.getDim();
//        assertEquals(d, new Point(3,2));
//
//        Grid g2 = new Grid(1,1, new Point(30,-90));
//        Point d2 = g2.getDim();
//        assertEquals(d2, new Point(1,1));
//
//        //Encapsulation
//        d.shift(-255, 3);
//        assertEquals(g.getDim(), new Point(3,2));
//    }
//
//    //clear well
//    @Test
//    public void clearWellTest() {
//        //Empty
//        Grid g = new Grid(3,5, new Point(0,0));
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 5; j++) {
//                assertEquals(g.getWell()[i][j], null);
//            }
//        }
//        g.clearWell();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 5; j++) {
//                assertEquals(g.getWell()[i][j], null);
//            }
//        }
//
//        //Non-empty
//        Grid g2 = new Grid(4,1, new Point(0,0));
//        Piece p = new Piece(new Point(1,0), Piece.Types.I);
//        g2.stickToGrid(p);
//        g2.clearWell();
//        Color[][] actual2 = g2.getWell();
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 1; j++) {
//                assertEquals(actual2[i][j], null);
//            }
//        }
//    }
//
//    // PIECE TESTS
//
//    //shift changes coordinates
//    @Test
//    public void shiftTest() {
//        //Up
//        Piece p = new Piece(new Point(0,0), Piece.Types.L);
//        Point[] initCoords = p.getGridCoords();
//        p.shift(Board.Direction.UP);
//        Point[] finalCoords = p.getGridCoords();
//        for (int i = 0; i < initCoords.length; i++) {
//            initCoords[i].shift(0, -1); //Direction of y is inverted in java graphics
//            assertEquals(initCoords[i], finalCoords[i]);
//        }
//
//        //Down
//        Piece p2 = new Piece(new Point(0,0), Piece.Types.J);
//        Point[] initCoords2 = p2.getGridCoords();
//        p2.shift(Board.Direction.DOWN);
//        Point[] finalCoords2 = p2.getGridCoords();
//        for (int i = 0; i < initCoords2.length; i++) {
//            initCoords2[i].shift(0, 1); //Direction of y is inverted in java graphics
//            assertEquals(initCoords2[i], finalCoords2[i]);
//        }
//
//        //Left
//        Piece p3 = new Piece(new Point(0,0), Piece.Types.Z);
//        Point[] initCoords3 = p3.getGridCoords();
//        p3.shift(Board.Direction.LEFT);
//        Point[] finalCoords3 = p3.getGridCoords();
//        for (int i = 0; i < initCoords3.length; i++) {
//            initCoords3[i].shift(-1, 0);
//            assertEquals(initCoords3[i], finalCoords3[i]);
//        }
//
//        //Right
//        Piece p4 = new Piece(new Point(0,0), Piece.Types.S);
//        Point[] initCoords4 = p4.getGridCoords();
//        p4.shift(Board.Direction.RIGHT);
//        Point[] finalCoords4 = p4.getGridCoords();
//        for (int i = 0; i < initCoords4.length; i++) {
//            initCoords4[i].shift(1, 0);
//            assertEquals(initCoords4[i], finalCoords4[i]);
//        }
//    }
//
//    //shift inverse undoes shift
//    @Test
//    public void shiftInverseTest() {
//        //Up
//        Piece p = new Piece(new Point(0,0), Piece.Types.L);
//        Point[] initCoords = p.getGridCoords();
//        p.shift(Board.Direction.UP);
//        p.shiftInverse(Board.Direction.UP);
//        Point[] finalCoords = p.getGridCoords();
//        for (int i = 0; i < initCoords.length; i++) {
//            assertEquals(initCoords[i], finalCoords[i]);
//        }
//
//        //Down
//        Piece p2 = new Piece(new Point(0,0), Piece.Types.J);
//        Point[] initCoords2 = p2.getGridCoords();
//        p2.shift(Board.Direction.DOWN);
//        p2.shiftInverse(Board.Direction.DOWN);
//        Point[] finalCoords2 = p2.getGridCoords();
//        for (int i = 0; i < initCoords2.length; i++) {
//            assertEquals(initCoords2[i], finalCoords2[i]);
//        }
//
//        //Left
//        Piece p3 = new Piece(new Point(0,0), Piece.Types.Z);
//        Point[] initCoords3 = p3.getGridCoords();
//        p3.shift(Board.Direction.LEFT);
//        p3.shiftInverse(Board.Direction.LEFT);
//        Point[] finalCoords3 = p3.getGridCoords();
//        for (int i = 0; i < initCoords3.length; i++) {
//            assertEquals(initCoords3[i], finalCoords3[i]);
//        }
//
//        //Right
//        Piece p4 = new Piece(new Point(0,0), Piece.Types.S);
//        Point[] initCoords4 = p4.getGridCoords();
//        p4.shift(Board.Direction.RIGHT);
//        p4.shiftInverse(Board.Direction.RIGHT);
//        Point[] finalCoords4 = p4.getGridCoords();
//        for (int i = 0; i < initCoords4.length; i++) {
//            assertEquals(initCoords4[i], finalCoords4[i]);
//        }
//    }
//
//    public Piece addRandomPieceToList() {
//        Piece.Types[] t = Piece.Types.values();
//        int randIndex = new Random().nextInt(t.length);
//        Piece p = new Piece(new Point(0, 0), t[randIndex]);
//        return p;
//    }
//
//    //rotateCW
//    @Test
//    public void rotateCWTest() {
//
//        //Iterates through all possible pieces
//        Piece.Types[] t = Piece.Types.values();
//        for (int i = 0; i < t.length; i++) {
//            Piece p = new Piece(new Point(0, 0), t[i]);
//            Piece p2 = new Piece(new Point(0, 0), t[i]);
//
//            //1 CW = 3 CW
//            p.rotateCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            assertEquals(p, p2);
//
//            //2 CW = 2 CCW
//            p.rotateCW();
//            p.rotateCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            assertEquals(p, p2);
//
//            //3 CW = 1 CCW
//            p.rotateCW();
//            p.rotateCW();
//            p.rotateCW();
//            p2.rotateCCW();
//            assertEquals(p, p2);
//
//            //4 CW = 4 CCW
//            p.rotateCW();
//            p.rotateCW();
//            p.rotateCW();
//            p.rotateCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            p2.rotateCCW();
//            assertEquals(p, p2);
//        }
//
//    }
//
//    //getGridCoords
//    @Test
//    public void getGridCoordsTest() {
//        //Iterates through all possible pieces
//        Piece.Types[] t = Piece.Types.values();
//        for (int i = 0; i < t.length; i++) {
//
//            //Test encapsulation
//            Piece p = new Piece(new Point(0, 0), t[i]);
//            Piece p2 = new Piece(new Point(0, 0), t[i]);
//            Point[] coords = p.getGridCoords();
//            coords[0] = null;
//            coords[2] = new Point(2,2);
//            assertEquals(p, p2);
//        }
//    }
//
//    // BOARD TESTS
//
//    //Deleting Lines - Putting down pieces in a line will cause the line to be deleted
//    @Test
//    public void deletingLinesTest() {
//        Board b = new Board(new JLabel());
//        b.reset();
//
//        //First square
//        Piece p1 = new Piece(new Point(0,18), Piece.Types.O);
//        b.fixCurrPiece(p1);
//        b.tick();
//        b.tick();
//
//        //Second square
//        Piece p2 = new Piece(new Point(2,18), Piece.Types.O);
//        b.fixCurrPiece(p2);
//        b.tick();
//        b.tick();
//
//        //Third square
//        Piece p3 = new Piece(new Point(4,18), Piece.Types.O);
//        b.fixCurrPiece(p3);
//        b.tick();
//        b.tick();
//
//        //Fourth square
//        Piece p4 = new Piece(new Point(6,18), Piece.Types.O);
//        b.fixCurrPiece(p4);
//        b.tick();
//        b.tick();
//
//        //Fifth square
//        Piece p5 = new Piece(new Point(8,18), Piece.Types.O);
//        b.fixCurrPiece(p5);
//        b.tick();
//        b.tick();
//
//        b.tick();
//
//        //Check to make sure both lines have been deleted
//        Color[][] actual = b.getMainGrid();
//        Color[][] expected = new Color[10][20];
//
//        for (int i = 0; i < actual.length; i++) {
//            for (int j = 0; j < actual[0].length; j++) {
//                assertEquals(actual[i][j], expected[i][j]);
//            }
//        }
//    }
//
//    //Hitting the top - when a piece is in spawn location, game is over
//    @Test
//    public void hittingTheTopTest() {
//        JLabel j = new JLabel();
//        Board b = new Board(j);
//        b.reset();
//
//
//        //Piece stuck in spawn location causes game over
//        Piece p = new Piece(Board.START_POS, Piece.Types.J);
//        b.fixCurrPiece(p);
//        b.stickPieceToBoard();
//        Piece p2 = new Piece(Board.START_POS, Piece.Types.L);
//        b.fixCurrPiece(p2);
//        b.stickPieceToBoard();
//        assertEquals(j.getText(), "Game Over!");
//
//    }
//
//    //Trying shift, rotate on a piece will not make it go out of bounds
//    @Test
//    public void shiftPieceTest() {
//        JLabel j = new JLabel();
//        Board b = new Board(j);
//        b.reset();
//
//        //Piece on left side cannot shift left, but can shift right
//        Piece p = new Piece(new Point(0,10), Piece.Types.O);
//        b.fixCurrPiece(p);
//        assertFalse(b.shift(Board.Direction.LEFT));
//        assertTrue(b.shift(Board.Direction.RIGHT));
//
//        b.reset();
//        //Piece on right side cannot shift right, but can shift left
//        Piece p2 = new Piece(new Point(18,3), Piece.Types.O);
//        b.fixCurrPiece(p2);
//        assertFalse(b.shift(Board.Direction.RIGHT));
//
//        b.reset();
//        //Piece on bottom cannot shift down, but can shift up
//        Piece p3 = new Piece(new Point(5,18), Piece.Types.O);
//        b.fixCurrPiece(p3);
//        assertFalse(b.shift(Board.Direction.DOWN));
//        assertTrue(b.shift(Board.Direction.UP));
//    }
}
