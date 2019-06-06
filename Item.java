
/**
 * Write a description of class item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private int peso;
    private String descripcion;
    /**
     * Constructor for objects of class item
     */
    public Item(String descripcion, int peso)
    {
        this.descripcion = descripcion;
        this.peso = peso;
    }

    public int getPeso(){
        return peso;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
}
