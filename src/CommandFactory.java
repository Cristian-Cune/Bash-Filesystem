
//package tema3_poo;


public class CommandFactory {
    
    private static final CommandFactory INSTANCE=new CommandFactory();
    
    private CommandFactory(){
        
    }
    
    public static CommandFactory getInstance(){
        return INSTANCE;
    }
    
    public Command createCommand(String name,FileSystem fl){
        Command command = null;
        switch (name) {
            case "ls":
                command = new Ls(fl);
                break;
            case "pwd":
                command = new Pwd(fl);
                break;
            case "cd":
                command = new Cd(fl);
                break;
            case "cp":
                command = new Cp(fl);
                break;
            case "mv":
                command = new Mv(fl);
                break;
            case "rm":
                command = new Rm(fl);
                break;
            case "touch":
                command = new Touch(fl);
                break;
            case "mkdir":
                command = new Mkdir(fl);
                break;
            default:
                break;
        }
        
        return command;
        
    }
}
