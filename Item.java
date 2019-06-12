
/**
 * Write a description of class item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
public class Item
{
    // instance variables - replace the example below with your own
    private Integer id;
    private int peso;
    private String descripcion;
    private boolean pickeable;
    /**
     * Constructor for objects of class item
     */
    public Item(Integer id, String descripcion, int peso, boolean pickeable)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.pickeable = pickeable;
    }

    public int getPeso(){
        return peso;
    }
    
    public boolean getPickeable(){
        return pickeable;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public Integer getId(){
        return id;
    }
}
