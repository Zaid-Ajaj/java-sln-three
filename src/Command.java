import java.lang.*;
import java.util.*;

/**
 * A Command represent a function provided by the user that has a name and a list of arguments
 * In our case, the arguments are a list of double values
 * 
 * @author Zaid Ajaj - s4807561
 * @author Luna-Elise Schernthaner - s4703928 
 */
public class Command 
{ 
    public final String name; 
    public final double[] arguments; 

    private Command(String name, double[] arguments) 
    { 
      this.name = name; 
      this.arguments = arguments; 
    } 

    /**  Safely parses doubles from an input string, returning an empty value when parsing failes. */
    static OptionalDouble parseDouble(String input)
    {
        try 
        {
            double result = Double.parseDouble(input);
            return OptionalDouble.of(result);
        }
        catch (Exception ex)
        {
            return OptionalDouble.empty();
        }
    }


    /** Safely parses an input string into a command result that represents either a command or an error in case parsing fails */
    public static Result<Optional<Command>> tryParse(String input)
    {
        String[] parts = input.split(" ");
        String errorMessage = "";

        if (parts.length == 0 || (parts.length == 1 && parts[0] == ""))
        {
          // empty input
          errorMessage = "Input was empty";
          return Result.create(Optional.empty(), Error.of(errorMessage));
        }
        else if (parts.length == 1)
        {
           String commandName = parts[0];
           // the only commands without paramters are 'show', 'quit' and 'sort'
           String[] validCommands = { "show", "quit", "sort" };
           if (Arrays.stream(validCommands).anyMatch(cmd -> cmd.equalsIgnoreCase(commandName)))
           {
              // no-argument command has 0 arguments 
              double[] emptyArguments = { };
              Optional<Command> command = Optional.of(new Command(commandName, emptyArguments));
              return Result.create(command, Error.of(errorMessage));
           }
           else
           {
              errorMessage = "Only commands {show, sort, quit} are recognised as zero-argument command.";
              return Result.create(Optional.empty(), Error.of(errorMessage));
           }
       }
       else if (parts.length == 2 && parts[0].equalsIgnoreCase("sort"))
       {
          // optional argument is present for sort
          // x => 1.0
          // y => -1.0 
          // because the arguments are of type double[]
          if (parts[1] == "x")
          {
            double[] args = { 1.0 };
            return Result.create(Optional.of(new Command("sort", args)), Error.of(errorMessage));
          }
          else if (parts[1] == "y")
          {
            double[] args = { -1.0 };
            return Result.create(Optional.of(new Command("sort", args)), Error.of(errorMessage));
          }
          else
          {
            errorMessage = "command sort does not recognize the optional argument " + parts[1];
            return Result.create(Optional.empty(), Error.of(errorMessage));
          }
       }
       else
       {
            String[] possibleCommands = { "circle", "rectangle", "move", "remove" };
            // multi-parameter command
            String command = parts[0];
          
            // if the command entered is not one of the possible commands
            if (Arrays.stream(possibleCommands).noneMatch(cmd -> cmd.equalsIgnoreCase(command)))
            {
              errorMessage = "The command you entered was not recognized as one of the possible commands";
              return Result.create(Optional.empty(), Error.of(errorMessage));
            }

            double[] arguments = 
                Arrays.stream(parts)                               // create a stream of strings
                      .skip(1)                                     // skip the command name
                      .map(arg -> parseDouble(arg))                // try parse each part
                      .filter(optArg -> optArg.isPresent())        // filter the successfully parsed 
                      .mapToDouble(optArg -> optArg.getAsDouble()) // retrieve parsed values  
                      .toArray();                                  // turn them into an array
                                  
            if (command.equalsIgnoreCase("circle") && arguments.length != 3)
            {
              errorMessage = "The circle command requires three parameters as valid numbers";
              return Result.create(Optional.empty(), Error.of(errorMessage));
            }

            if (command.equalsIgnoreCase("rectangle") && arguments.length != 4)
            {
              errorMessage = "The rectangle command requires four parameters as valid numbers";
              return Result.create(Optional.empty(), Error.of(errorMessage));
            }

            if (command.equalsIgnoreCase("move") && arguments.length != 3)
            {
              errorMessage = "The move command requires three parameters as valid numbers";
              return Result.create(Optional.empty(), Error.of(errorMessage));
            }

            if (command.equalsIgnoreCase("remove") && arguments.length != 1) 
            {
              errorMessage = "The remove command requires one parameter as a valid number";
              return Result.create(Optional.empty(), Error.of(errorMessage));
            }

            if (command.equalsIgnoreCase("remove") && arguments.length == 1)
            {
                double shapeIndex = arguments[0];
                boolean shapeIndexIsWholeNumber = Math.floor(shapeIndex) == shapeIndex;
                if (!shapeIndexIsWholeNumber)
                {
                  errorMessage = "The argument of the remove command must be a whole number";
                  return Result.create(Optional.empty(), Error.of(errorMessage));
                }
            }
          
            errorMessage = "";
            Command parsedCommand = new Command(command, arguments);
            return Result.create(Optional.of(parsedCommand), Error.of(errorMessage));
       }
    } 
}