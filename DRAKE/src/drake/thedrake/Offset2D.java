package drake.thedrake;

/**
 * bude reprezentovat diskrétní posunutí ve 2D prostoru. To se nám bude hodit na mnoha místech hry pro posouvání se po hrací desce.
 */
public class Offset2D {
    public final int x, y;

    // Konstruktor
    public Offset2D(int x, int y){
        this.x=x;
        this.y=y;
    }

    // Zjištuje, zda se tento offset rovná jinému offsetu
    public boolean equalsTo(int x, int y){
        if(this.x==x && this.y==y)
            return true;
        else return false;
    }

    // Vrací nový offset, kde y souřadnice má obrácené znaménko
    public Offset2D yFlipped(){
        Offset2D reversedY = new Offset2D(this.x, -this.y);
        return reversedY;
    }
}
