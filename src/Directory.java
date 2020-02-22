
//package tema3_poo;

import java.util.*;
public class Directory implements Component{
    
    private String name;
    Directory parent ;
    
    public Directory(String name,Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    List<Component> components = new ArrayList<>();

    public List<Component> getComponents() {
        return components;
    }
    
    
    @Override
    public void add(Component component){
        components.add(component);
    }
    
    @Override
    public void remove(Component component){
        components.remove(component);
    }
    
    
    @Override
    public Component getChild(int i){
        return components.get(i);
    }

    
    @Override
    public String getName() {
        return name;
    }
    
    
    public String ls(){
        return " ";
    }
    
    public String pwd(){
        return " ";
    }
    
    public String cd(){
        return " ";
    }
    
    public String cp(){
        return " ";
    }
    
    public String mv(){
        return " ";
    }
    
    public String rm(){
        return " ";
    }
    
    public String touch(){
        return " ";
    }
    
    public String mkdir(){
        return " ";
    }
}
