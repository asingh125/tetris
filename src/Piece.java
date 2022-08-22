import java.awt.Color;

public class Piece {
    private Point pos; //stores position of piece
    private int coordIndex; //stores index of current orientation coordinates
    private Point[][] orientations; //stores 4 arrays of the 4 possible piece orientations
    private Color color; //color of the shape
    
    //Enum for the different shape types
    public enum Types { I, O, L, J, T, S, Z }; 
    private final Types type; 
    
    Piece(Point pos, Types type) {
        this.pos = pos; 
        coordIndex = 0;
        this.type = type; 
        
        if (type.equals(Types.I)) {
            color = Color.cyan; 
            Point[][] ors = 
                {
                    {new Point(-1,0), new Point(0,0), new Point(1,0), new Point(2,0)}, 
                    {new Point(1,1), new Point(1,0), new Point(1,-1), new Point(1,-2)}, 
                    {new Point(-1,-1), new Point(0,-1), new Point(1,-1), new Point(2,-1)},
                    {new Point(0,1), new Point(0,0), new Point(0,-1), new Point(0,-2)}, 
                }; 
            orientations = ors;
        } else if (type.equals(Types.O)) {
            color = Color.yellow;
            Point[][] ors = 
                {
                    {new Point(0,0), new Point(0,-1), new Point(1,0), new Point(1,-1)}, 
                    {new Point(0,0), new Point(0,-1), new Point(1,0), new Point(1,-1)}, 
                    {new Point(0,0), new Point(0,-1), new Point(1,0), new Point(1,-1)}, 
                    {new Point(0,0), new Point(0,-1), new Point(1,0), new Point(1,-1)}, 
                }; 
            orientations = ors;
        } else if (type.equals(Types.L)) {
            color = Color.orange; 
            Point[][] ors = 
                {
                    {new Point(-1,0), new Point(0,0), new Point(1,0), new Point(1,1)}, 
                    {new Point(0,1), new Point(0,0), new Point(0,-1), new Point(1,-1)}, 
                    {new Point(1,0), new Point(0,0), new Point(-1,0), new Point(-1,-1)}, 
                    {new Point(0,-1), new Point(0,0), new Point(0,1), new Point(-1,1)}
                }; 
            orientations = ors;
        } else if (type.equals(Types.J)) {
            color = Color.blue; 
            Point[][] ors = 
                {
                    {new Point(1,0), new Point(0,0), new Point(-1,0), new Point(-1,1)}, 
                    {new Point(0,-1), new Point(0,0), new Point(0,1), new Point(1,1)}, 
                    {new Point(-1,0), new Point(0,0), new Point(1,0), new Point(1,-1)}, 
                    {new Point(0,1), new Point(0,0), new Point(0,-1), new Point(-1,-1)}
                }; 
            orientations = ors;
        } else if (type.equals(Types.T)) {
            color = Color.magenta; 
            Point[][] ors = 
                {
                    {new Point(-1,0), new Point(0,0), new Point(0,1), new Point(1,0)}, 
                    {new Point(0,1), new Point(0,0), new Point(1,0), new Point(0,-1)}, 
                    {new Point(1,0), new Point(0,0), new Point(0,-1), new Point(-1,0)},  
                    {new Point(0,-1), new Point(0,0), new Point(-1,0), new Point(0,1)}, 
                }; 
            orientations = ors;
        } else if (type.equals(Types.S)) {
            color = Color.green;
            Point[][] ors = 
                {
                    {new Point(-1,-1), new Point(0,-1), new Point(0,0), new Point(1,0)}, 
                    {new Point(-1,1), new Point(-1,0), new Point(0,0), new Point(0,-1)}, 
                    {new Point(1,1), new Point(0,1), new Point(0,0), new Point(-1,0)},  
                    {new Point(1,-1), new Point(1,0), new Point(0,0), new Point(0,1)}, 
                }; 
            orientations = ors;
        } else if (type.equals(Types.Z)) {
            color = Color.red;
            Point[][] ors = 
                {
                    {new Point(-1,0), new Point(0,0), new Point(0,-1), new Point(1,-1)}, 
                    {new Point(0,1), new Point(0,0), new Point(-1,0), new Point(-1,-1)}, 
                    {new Point(1,0), new Point(0,0), new Point(0,1), new Point(-1,1)},  
                    {new Point(0,-1), new Point(0,0), new Point(1,0), new Point(1,1)}, 
                }; 
            orientations = ors;
        } 
    }
    
    public Color getColor() {
        return color; 
    }
    public Types getType() {
        return type; 
    }
    public void shift(Board.Direction d) {
        if (d.equals(Board.Direction.UP)) {
            pos.shift(0, -1);
        } else if (d.equals(Board.Direction.DOWN)) {
            pos.shift(0, 1);
        } else if (d.equals(Board.Direction.LEFT)) {
            pos.shift(-1, 0);
        } else if (d.equals(Board.Direction.RIGHT)) {
            pos.shift(1, 0);
        } 
    }
    public void shiftInverse(Board.Direction d) { //reverses a shift
        if (d.equals(Board.Direction.UP)) {
            pos.shift(0, 1);
        } else if (d.equals(Board.Direction.DOWN)) {
            pos.shift(0, -1);
        } else if (d.equals(Board.Direction.LEFT)) {
            pos.shift(1, 0);
        } else if (d.equals(Board.Direction.RIGHT)) {
            pos.shift(-1, 0);
        } 
    }
    public void rotateCW() { //rotates clockwise
        if (coordIndex < 3) {
            coordIndex++;
        } else {
            coordIndex = 0;
        }
    } 
    public void rotateCCW() { //rotates counterclockwise
        if (coordIndex > 0) {
            coordIndex--;
        } else {
            coordIndex = 3;
        }
    } 
    public Point[] getGridCoords() { //uses position & orientation to return grid coords
        Point[] gridCoords = new Point[4];
        Point[] c = orientations[coordIndex]; 
        for (int i = 0; i < 4; i++) {
            //add x coord and subtract y because y coordinate in java increases as we go down
            gridCoords[i] = new Point(pos.getA() + c[i].getA(), pos.getB() - c[i].getB()); 
        }
        return gridCoords;
    }
    
    @Override
    public boolean equals(Object o) {
        Piece p = (Piece) o; 
        
        //Must have same type
        if (!(type == p.getType())) {
            return false; 
        } 
        
        //Must have same coords
        boolean sameCoords = true; 
        Point[] coords = getGridCoords(); 
        for (int i = 0; i < coords.length; i++) {
            if (!(coords[i].equals(p.getGridCoords()[i]))) {
                sameCoords = false;
            }
        }
        return sameCoords; 
    }
    
}
