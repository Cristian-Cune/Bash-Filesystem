
//package tema3_poo;

import java.util.*;
import java.io.*;
public class FileSystem implements Component{
    
    private String name;
    private Directory WorkingDirectory ,root = new Directory("/",null);
    public FileSystem(String name) {
        this.name = name;
        this.WorkingDirectory = root;
    }

    public Directory getWorkingDirectory() {
        return WorkingDirectory;
    }

    public void setWorkingDirectory(Directory WorkingDirectory) {
        this.WorkingDirectory = WorkingDirectory;
    }
    
    
    List<Component> components = new ArrayList<>();
    
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

    public void setName(String name) {
        this.name = name;
    }
    
    
     
    
    public void ls(String path,PrintWriter out,PrintWriter err,String arg){
        
        Directory dir = getSearchedDirectory(path);
        
        if(dir == null){
            err.println("ls: " + path + ": No such directory");
            return;
        }
       
        
        if(dir.getParent() == null){
            
            //sortare
            Collections.sort(components, new Comparator<Component>(){
                @Override
                public int compare(Component c1 , Component c2){
                    return c1.getName().compareTo(c2.getName());
                }
            });
          
            out.println("/:");
            for(int i = 0 ; i < components.size() - 1 ; i++)
                out.print(getChild(i).getName() + " ");
            if(components.size() != 0)
                out.println(getChild(components.size() - 1).getName());
            else
                out.println();
            out.println();
            if(arg.equals("-R")){
                
                
                 for(int i = 0 ; i < components.size() ; i++){
                    
                    if(getChild(i) instanceof Directory)
                        ls(getChild(i).getName(),out,err,arg);
                    
                 }
            }
            
        }
        else {
            //sortare
            Collections.sort(dir.getComponents(), new Comparator<Component>(){
                @Override
                public int compare(Component c1 , Component c2){
                    return c1.getName().compareTo(c2.getName());
                }
            });
           
            out.println( dir.getName() + ":");
            
            for(int i = 0 ; i < dir.getComponents().size() - 1 ; i++)
                out.print(dir.getChild(i).getName() + " ");
            if(dir.getComponents().size() != 0)
                out.println(dir.getChild(dir.getComponents().size() - 1 ).getName());
            else
                out.println();
            out.println();
             if(arg.equals("-R")){
                 
                for(int i = 0 ; i < dir.components.size() ; i++){
                    
                    if(dir.getChild(i) instanceof Directory)
                        ls(dir.getChild(i).getName(),out,err,arg);
                    
                }
            }
        }
        
    }
    
    public void pwd(String path,PrintWriter out,PrintWriter err){
        out.println(WorkingDirectory.getName());
    }
    
    
    
    public void cd(String path,PrintWriter err){
        
        Directory dir = getSearchedDirectory(path);
        
       
        if(dir == null){
            err.println("cd: " + path + ": No such directory");
            return;
        }
        
        if(dir.getParent() == null ){
            setWorkingDirectory(root);
            return ;
        }
        
        setWorkingDirectory(dir);

    }
    
    
    
    public void cp(String path,PrintWriter err){
       
        StringTokenizer tok = new StringTokenizer(path);
        String source = tok.nextToken();
        String dest = tok.nextToken();
        
        String parent = get_parent_path(source);
        
        Directory source_dir = getSearchedDirectory(parent);
        Directory dest_dir = getSearchedDirectory(dest);//directorul destinatie
        
        
        Component source_file = null;//fisierul sursa
        
        /* error 1*/
        if(source_dir == null ){    
            err.println("cp: cannot copy " + source + ": No such file or directory");
            return;
        }
        if(source_dir.getParent() == null){
            for( int i = 0 ; i < components.size() ; i++){
                if(getChild(i).getName().equals("/" + searched_name(source))){
                    source_file = getChild(i);
                    break;
                }
            }
        }
        else{
            for( int i = 0 ; i < source_dir.getComponents().size() ; i++) {
                if(source_dir.getChild(i).getName().equals(source_dir.getName() + "/" + searched_name(source))){
                    source_file = source_dir.getChild(i);
                    break;
                }
            }
        }
        if(path.equals("."))
            source_file = WorkingDirectory;
        if(path.equals(".."))
            source_file = WorkingDirectory.getParent();
        
        if(source_file == null){ 
            err.println("cp: cannot copy " + source + ": No such file or directory");
            return ;
        }
        /*error 2*/
        
        if(dest_dir == null ){
            err.println("cp: cannot copy into " + dest + ": No such directory");
            return ;
        }
        /*error 3*/
        if(dest_dir.getParent() == null){
            for(int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals("/"+searched_name(source_file.getName()))){
                    err.println("cp: cannot copy " + source + ": Node exists at destination");
                    return ;
                }
        }
        else {
            for(int i = 0 ; i < dest_dir.getComponents().size() ; i++)
                if(dest_dir.getChild(i).getName().equals(dest_dir.getName() + "/" + searched_name(source_file.getName()))){
                    err.println("cp: cannot copy " + source + ": Node exists at destination");
                    return ;
                }
        }
         /*copy*/
        copy(source_file,dest_dir);
    }
    
    
    public void copy(Component c, Directory dest){
        
        Directory dir;
        Leaf_File file;
        if(c instanceof Leaf_File){
            if(dest.getParent()==null)
                file = new Leaf_File(dest.getName()  + searched_name(c.getName()),dest);
            else
                file = new Leaf_File(dest.getName() + "/" + searched_name(c.getName()),dest);
            
            if(dest.getParent()==null)
                add(file);
            else
                dest.add(file);
        }
        else if(c instanceof Directory){
            if(dest.getParent()==null)
                dir = new Directory(dest.getName()  + searched_name(c.getName()),dest);
            else
                dir = new Directory(dest.getName() + "/" + searched_name(c.getName()),dest);
            
            for(int i = 0; i < ((Directory) c).getComponents().size() ; i++){
                copy(c.getChild(i),dir);
            }
            if(dest.getParent()==null)
                add(dir);
            else
                dest.add(dir);
        }
        
        
    }
    
    
    public void mv(String path,PrintWriter err){
       
        StringTokenizer tok = new StringTokenizer(path);
        String source = tok.nextToken();
        String dest = tok.nextToken();

        String parent = get_parent_path(source);
        
        Directory source_dir = getSearchedDirectory(parent);
        
        Directory dest_dir = getSearchedDirectory(dest);//directorul destinatie
        
        Component source_file = null;//fisierul sursa
        
        /* error 1*/
        if(source_dir == null ){
            err.println("mv: cannot move " + source + ": No such file or directory");
            return;
        }
        if(source_dir.getParent() == null){
            for( int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals("/" + searched_name(source))){
                    source_file = getChild(i);
                    break;
                }
        }
        else{
            for( int i = 0 ; i < source_dir.getComponents().size() ; i++)
                if(source_dir.getChild(i).getName().equals(source_dir.getName() + "/" + searched_name(source))){
                    source_file = source_dir.getChild(i);
                    break;
                }       
        }
        if(path.equals("."))
            source_file = WorkingDirectory;
        if(path.equals(".."))
            source_file = WorkingDirectory.getParent();
        
        if(source_file == null){
            err.println("mv: cannot move " + source + ": No such file or directory");
            return ;
        }
        /*error 2*/
        
        if(dest_dir == null ){
            err.println("mv: cannot move into " + dest + ": No such directory");
            return ;
        }
        /*error 3*/
        if(dest_dir.getParent() == null){
            for(int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals( "/" + searched_name(source_file.getName()))){
                    err.println("mv: cannot move " + source + ": Node exists at destination");
                    return ;
                }
        }
        else {
            for(int i = 0 ; i < dest_dir.getComponents().size() ; i++)
                if(dest_dir.getChild(i).getName().equals(dest_dir.getName() + "/" + searched_name(source_file.getName()))){
                    err.println("cp: cannot copy " + source + ": Node exists at destination");
                    return ;
                }
        }
        
        /*error 4*/
        String WorkingDirectoryName = null;
        if(WorkingDirectory.getName().contains(source_file.getName()))
            WorkingDirectoryName = WorkingDirectory.getName();//salvam numele directorului curent
        
        if(dest_dir.getParent() == null)
            source_file.setName("/" + searched_name(source));
        else
            source_file.setName(dest_dir.getName() + "/" + searched_name(source));
        
        /*move*/
        copy(source_file,dest_dir);
        
        if(source_dir.getParent() == null)
            remove(source_file);
        else
            source_dir.remove(source_file);
        
        /*error 4 */
        Directory NewWorkingDirectory;
        if(WorkingDirectoryName != null){
            NewWorkingDirectory = getSearchedDirectory(WorkingDirectoryName);
            setWorkingDirectory(NewWorkingDirectory);
        }
    }
    
    
    public void rm(String path,PrintWriter err){
        
        Directory dir = getSearchedDirectory(get_parent_path(path));
        
        if(dir == null ){ //error 1
            err.println("rm: cannot remove " + path + ": No such file or directory");
            return ;
            
        }
        
        if(WorkingDirectory.getName().contains(searched_name(path))) // error 2
            return ;
        
        if(dir.getParent() == null){
            
            for(int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals("/" + searched_name(path))){
                    remove(getChild(i));
                    return;
                }
        }
        else {
            
            for(int i = 0 ; i < dir.getComponents().size() ; i++)
                if(dir.getChild(i).getName().equals(dir.getName() + "/" + searched_name(path))){
                    dir.remove(dir.getChild(i));
                    return;
                }
        }
        
        err.println("rm: cannot remove " + path + ": No such file or directory");
    }
    
    
    public void touch(String path,PrintWriter err){
        
        String file_name = searched_name(path);
        
        Directory directory = getSearchedDirectory(get_parent_path(path));

        if(directory == null) {// error 1
            String parent = get_parent_path(path);
            err.println("touch: " + parent + ": No such directory");
            return ;
        }
        Leaf_File new_file;
        
        if(directory.getParent() == null )
            new_file = new Leaf_File(directory.getName() +  file_name,directory);
        else
            new_file = new Leaf_File(directory.getName() + "/" +  file_name,directory);

       
        
        if(directory.getParent() == null){//error 2
            
            for(int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals("/" + searched_name(path))){
                    err.println("touch: cannot create file " + getChild(i).getName() + ": Node exists");
                    return;
                }
        }
        else {
            
            for(int i = 0 ; i < directory.getComponents().size() ; i++)
                if(directory.getChild(i).getName().equals(directory.getName() + "/" + searched_name(path))){
                    err.println("touch: cannot create file " + directory.getChild(i).getName() + ": Node exists");
                    return;
                }
        }
        
        
         if(directory.getParent() == null){
            add(new_file);
            return ;
        }
        
        directory.add(new_file);
    }
    
    
    public void mkdir(String path,PrintWriter err){
        
        String dir_name = searched_name(path);
        
       
        Directory directory = getSearchedDirectory(get_parent_path(path));
        
        Directory new_directory = new Directory(dir_name,directory);

        
        if(directory == null) {// error 1
            String parent = get_parent_path(path);
            
            err.println("mkdir: " + parent + ": No such directory");
            return ;
        }
        
        if(get_parent_path(path).contains(dir_name)){ // error 2
            err.println("mkdir: cannot create directory " + path + ": Node exists");
            return;
        }
        
        if(directory.getParent() == null){//error 2
            
            for(int i = 0 ; i < components.size() ; i++)
                if(getChild(i).getName().equals("/" + searched_name(path))){
                    err.println("mkdir: cannot create directory " + getChild(i).getName() + ": Node exists");
                    return;
                }
        }
        else {
            
            for(int i = 0 ; i < directory.getComponents().size() ; i++)
                if(directory.getChild(i).getName().equals(directory.getName() + "/" + searched_name(path))){
                    err.println("mkdir: cannot create directory " + directory.getChild(i).getName() + ": Node exists");
                    return;
                }
        }
        
        if(directory.getParent() == null){
            new_directory.setName("/" + dir_name);
            add(new_directory);
        }
        else{
            new_directory.setName(directory.getName() + "/" + dir_name);
            directory.add(new_directory);
        }   
    }
    
    public String searched_name(String path){
        
        StringTokenizer tok = new StringTokenizer(path,"/");
        String file = null;
        while(tok.hasMoreTokens())
            file = tok.nextToken();
        return file;
    }
    public String get_parent_path(String path){
        
        if(path.charAt(path.length()-1) == '/')
            path=path.substring(0, path.length()-2);
        StringTokenizer tok = new StringTokenizer(path,"/");
        String file = null;
        while(tok.hasMoreTokens())
            file = tok.nextToken();
        
        if(path.equals("/" + file) )
            return path.substring(0,path.lastIndexOf(file));
        
        if(WorkingDirectory.getParent() == null)
            return path.substring(0,path.lastIndexOf(file));
        if(path.equals(file))
            return path.substring(0,path.lastIndexOf(file));
        return path.substring(0,path.lastIndexOf("/" + file));

       
    }
    public Directory getSearchedDirectory(String path){
        
        if(path.equals("")){
            return WorkingDirectory;
        }
        StringTokenizer tok = new StringTokenizer(path,"/");
       
        Directory directory = null ;
        String file = null;
        
        if(path.charAt(0) == '/'){
            
            if(!tok.hasMoreTokens())
                return root;
            directory = root;
            file = directory.getName()  + tok.nextToken();
            
            int i;
            for( i = 0 ; i < components.size() ; i++)
                if(components.get(i).getName().equals(file)){
                    break;
                }
            
            if(i == components.size())
                directory = null;
            else
                if(components.get(i) instanceof Directory)
                    directory = (Directory) components.get(i);
        }
        else 
            directory = WorkingDirectory;
        if(directory != null){
            while(tok.hasMoreTokens()){ 
                file = tok.nextToken();
                
                if(file.equals(".")){
                   
                }
                else if(file.equals("..")){
                        if(directory.getParent() == null) 
                            return null;
                    directory = directory.getParent();
                }
                else {
                    
                    List<Component> comp;
                    
                    if(directory.getParent() == null){
                        file = directory.getName() + file;
                        comp = components;
                    }
                    else{    
                        file = directory.getName() + "/" + file;
                        comp = directory.getComponents();
                    }
                    
                    
                    int i ;
                    for ( i = 0 ; i < comp.size() ; i++){
                        if(comp.get(i).getName().equals(file) && comp.get(i) instanceof Directory){
                            break;
                        }
                    }
                    if( i == comp.size()){
                       return  null;
                    }
                    else
                        directory = (Directory)comp.get(i);
                    
                }
            }
        }
        return directory;
    }
}
