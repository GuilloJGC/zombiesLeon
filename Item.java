
/**
 * Write a description of class item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private Integer id;
    private int peso;
    private String descripcion;
    private boolean pickeable;
    private boolean range;
    private static final int RANGED_DMG_MULTIPLIER = 3;
    private static final int UNRANGED_DMG_MULTIPLIER = 2;
    
    /**
     * Constructor for objects of class item
     */
    public Item(Integer id, String descripcion, int peso, boolean pickeable, boolean range)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.pickeable = pickeable;
        this.range = range;
    }

    public boolean isPickeable(){
        return pickeable;
    }
    
    public int getPeso(){
        return peso;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public Integer getId(){
        return id;
    }
    
    public boolean isRanged(){
        return range;
    }
    
    public int getDamage (){
        int totalDmg = 0;
            if(isRanged()){
                totalDmg = getPeso() * RANGED_DMG_MULTIPLIER;
            }
            else{
                totalDmg = getPeso() * UNRANGED_DMG_MULTIPLIER;
            }
        return totalDmg;
    }
}
