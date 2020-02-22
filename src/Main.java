
//package tema3_poo;

import java.io.*;
import java.util.*;
public class Main {

    
    public static void main(String[] args) {
      
        File file_in = new File(args[0]);
        File file_out = new File(args[1]);
        File file_err = new File(args[2]);
        
        try{
            Scanner input = new Scanner(file_in);
            PrintWriter output = new PrintWriter(file_out);
            PrintWriter error = new PrintWriter(file_err);
            
          
            CommandFactory cf = CommandFactory.getInstance();
            
            FileSystem fs = new FileSystem("/");
            int command_number = 1;
            while(input.hasNextLine()){
                
                String comm = input.nextLine();
                
                output.println(command_number);
                error.println(command_number);
                command_number ++;
                
                StringTokenizer tok = new StringTokenizer(comm);
                String op=tok.nextToken();
                
                if(op.equals("touch")){
                    Command new_command = cf.createCommand("touch", fs);
                    String path = tok.nextToken();
                    new_command.execute(path,output,error,null);
                }
                
                if(op.equals("mkdir")){
                    Command new_command = cf.createCommand("mkdir", fs);
                    String path = tok.nextToken();
                    new_command.execute(path,output,error,null);
                }
                
                if(op.equals("cd")){
                    Command new_command = cf.createCommand("cd", fs);
                    String path = tok.nextToken();
                    new_command.execute(path,output,error,null);
                }
                
                if(op.equals("rm")){
                    Command new_command = cf.createCommand("rm", fs);
                    String path = tok.nextToken();
                    new_command.execute(path, output, error,null);
                }
                if(op.equals("ls")){
                    Command new_command = cf.createCommand("ls", fs);
                    String path,arg = "";
                    if(!tok.hasMoreTokens())
                        path = "";
                    else {
                        String next_arg = tok.nextToken();
                        if(next_arg.equals("-R")){
                            arg = "-R";
                            if(!tok.hasMoreTokens())
                                path = "";
                            else
                                path = tok.nextToken();
                        }
                        else {
                            path = next_arg;
                            if(tok.hasMoreTokens())
                               arg = tok.nextToken();
                        }
                    }
                    new_command.execute(path, output, error, arg);
                }
                
                if(op.equals("pwd")){
                    Command new_command = cf.createCommand("pwd", fs);
                    new_command.execute(null, output, error,null);
                }
                
                if(op.equals("cp")){
                    Command new_command = cf.createCommand("cp", fs);
                    new_command.execute(tok.nextToken() + " "  + tok.nextToken(),output,error,null);//trimitem sursa si destinatia ca parametru
                }
                if(op.equals("mv")){
                    Command new_command = cf.createCommand("mv", fs);
                    new_command.execute(tok.nextToken() + " "  + tok.nextToken(),output,error,null);//trimitem sursa si destinatia ca parametru
                }
            }
            
            output.close();
            error.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Fisier inexistent!");
        }
        
    }
    
}
