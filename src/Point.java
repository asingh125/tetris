
public class Point {
    private int a; 
    private int b; 
    
    public Point(int a, int b) {
        this.a = a;
        this.b = b; 
    }
    
    public int getA() {
        return a; 
    }
    
    public int getB() {
        return b; 
    }
    
    public void shift(int da, int db) {
        a = a + da;
        b = b + db; 
    }
    
    public void shift(Point p) {
        shift(p.getA(), p.getB()); 
    }
    
    public Point copy() {
        return new Point(a,b); 
    }
    
    @Override
    public boolean equals(Object o) {
        Point p = (Point) o; 
        return (a == p.getA() && b == p.getB()); 
    }

}
