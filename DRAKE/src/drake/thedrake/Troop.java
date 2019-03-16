package drake.thedrake;

/**
 * bude představovat bojovou jednotku v naší hře. Každá jednotka má svoje jméno (například "Archer") a takzvaný lícový pivot a rubový pivot.
 */
public class Troop {
    private final String name;
    private final Offset2D aversPivot;
    private final Offset2D reversPivot;
    // Hlavní konstruktor
    public Troop(String name, Offset2D aversPivot, Offset2D reversPivot){
        this.name=name;
        this.aversPivot=aversPivot;
        this.reversPivot=reversPivot;
    }

    // Konstruktor, který nastavuje oba pivoty na stejnou hodnotu
    public Troop(String name, Offset2D pivot){
        this(name, pivot, pivot);
    }

    // Konstruktor, který nastavuje oba pivoty na hodnotu [1, 1]
    public Troop(String name){
        this(name, new Offset2D(1,1), new Offset2D(1,1));
    }

    // Vrací jméno jednotky
    public String name(){
        return name;
    }

    // Vrací pivot na zadané straně jednotky
    public Offset2D pivot(TroopFace face){
        if(face == TroopFace.AVERS) return aversPivot;
        else return reversPivot;
    }
}
