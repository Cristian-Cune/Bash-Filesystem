
//package tema3_poo;

import java.util.*;
public class Leaf_File implements Component{
    
    private String name;
    Directory parent;
    
    public Leaf_File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }
    
    
    @Override
    public void add(Component component){
        
    }
    
    @Override
    public void remove(Component component){
        
    }
    
    
    @Override
    public Component getChild(int i){
        return null;
    }

    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
