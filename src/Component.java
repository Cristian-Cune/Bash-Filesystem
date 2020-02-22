
//package tema3_poo;

public interface Component {
    
   
    public void add(Component component);
    public void remove(Component component);
    public Component getChild(int i);
    public String getName();
    public void setName(String name);
}
