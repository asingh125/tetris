import java.awt.Color;

public class Grid {
    private Color[][] well; 
    private Point pos; 
    private Point d; 
    
    public Grid(int x, int y, Point p) {
        well = new Color[x][y]; 
        pos = p;
        d = new Point(x,y); 
    }
    
    public void stickToGrid(Piece p) {
        Point[] coords = p.getGridCoords(); 
        for (int i = 0; i < coords.length; i++) {
            int x = coords[i].getA(); 
            int y = coords[i].getB(); 
            if (0 <= x && x < d.getA() && 0 <= y && y < d.getB()) {
                well[x][y] = p.getColor(); 
            }
        }
    }
    
    public Color[][] getWell() {
        Color[][] copy = new Color[d.getA()][d.getB()]; 
        for (int x = 0; x < d.getA(); x++) {
            for (int y = 0; y < d.getB(); y++) {
                copy[x][y] = well[x][y]; 
            }
        }
        return copy; 
    }
    
    public void setSquareColor(int x, int y, Color c) {
        if (0 <= x && x < d.getA() && 0 <= y && y < d.getB()) {
            well[x][y] = c;  
        }
    }
    
    public Point getPos() {
        return new Point(pos.getA(), pos.getB()); 
    }
    
    public Point getDim() {
        return new Point(d.getA(), d.getB()); 
    }
    
    public void clearWell() {
        well = new Color[d.getA()][d.getB()]; 
    }

}
