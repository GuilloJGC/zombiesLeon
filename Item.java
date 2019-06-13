
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
    /**
     * Constructor for objects of class item
     */
    public Item(Integer id, String descripcion, int peso)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
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
}
