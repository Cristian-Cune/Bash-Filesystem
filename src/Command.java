
//package tema3_poo;

import java.io.*;
public interface Command {
    void execute(String path,PrintWriter out,PrintWriter err,String arg);
}

class Ls implements Command{

    private FileSystem filesystem;

    public Ls(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
         filesystem.ls(path,out,err,arg);
    }
}

class Pwd implements Command{

    private FileSystem filesystem;
    
    public Pwd(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.pwd(path,out,err);
    }
}

class Cd implements Command{

    private FileSystem filesystem;
    
    public Cd(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.cd(path,err);
    }
}

class Cp implements Command{

    private FileSystem filesystem;
    
    public Cp(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.cp(path,err);
    }
}

class Mv implements Command{

    private FileSystem filesystem;
    
    public Mv(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.mv(path,err);
    }
}

class Rm implements Command{

    private FileSystem filesystem;
    
    public Rm(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.rm(path,err);
    }
}

class Touch implements Command{

    private FileSystem filesystem;
    
    public Touch(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
         filesystem.touch(path,err);
    }
}

class Mkdir implements Command{

    private FileSystem filesystem;
    
    public Mkdir(FileSystem filesystem) {
        this.filesystem = filesystem;
    }
    
    @Override
    public void execute(String path,PrintWriter out,PrintWriter err,String arg) {
        
        filesystem.mkdir(path,err);
    }
}
