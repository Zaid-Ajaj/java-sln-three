import java.lang.*;

public class Command 
{ 
    public final String name; 
    public final double[] arguments; 

    public Command(String name, double[] arguments) 
    { 
      this.name = name; 
      this.arguments = arguments; 
    } 
} 