import java.lang.*;
import java.util.*;

/**
 * A Command represent a function provided by the user that has a name and a list of arguments
 * In our case, the arguments are a list of double values
 * 
 * @author Zaid Ajaj
 * @author Luna Schernthaner 
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


    /** Safely parses a string into either a command or a string that represents an error in case parsing fails */
    public static Tuple<Optional<Command>, String> tryParse(String input)
    {
      String[] parts = input.split(" ");
      String error = "";

      if (parts.length == 0 || (parts.length == 1 && parts[0] == ""))
      {
          // empty input
          error = "Input was empty";
          return Tuple.create(Optional.empty(), error);
      }
      else if (parts.length == 1)
      {
          String commandName = parts[0];
          // the only commands without paramters are 'show' and 'quit'
          if (commandName.equalsIgnoreCase("show") || commandName.equalsIgnoreCase("quit"))
          {
              // no-argument command has 0 arguments 
              double[] emptyArguments = { };
              Optional<Command> command = Optional.of(new Command(commandName, emptyArguments));
              return Tuple.create(command, error);
          }
          else
          {
              error = "The single argument command you entered was not recognized";
              return Tuple.create(Optional.empty(), error);
          }
      }
      else if (parts.length == 2 && parts[0].equalsIgnoreCase("sort"))
      {
          return Tuple.create(Optional.empty(), error);
      }
      else
      {
          String[] possibleCommands = { "circle", "rectangle", "move", "remove" };
          // multi-parameter command
          String command = parts[0];
          
          // if the command entered is not one of the possible commands
          if (Arrays.stream(possibleCommands).noneMatch(cmd -> cmd.equalsIgnoreCase(command)))
          {
              error = "The command you entered was not recognized as one of the possible commands";
              return Tuple.create(Optional.empty(), error);
          }

          double[] arguments = 
              Arrays.stream(parts)                               // create a stream of strings
                    .skip(1)                                     // skip the command name
                    .map(arg -> parseDouble(arg))                // try parse each part
                    .filter(optArg -> optArg.isPresent())        // filter the successfully parsed 
                    .mapToDouble(optArg -> optArg.getAsDouble()) // retrieve parsed values  
                    .toArray();                                  // turn them into an array
                                  
          if (command == "circle" && arguments.length != 3)
          {
              error = "The circle command requires three parameters as valid numbers";
              return Tuple.create(Optional.empty(), error);
          }

          if (command == "rectangle" && arguments.length != 4)
          {
              error = "The rectangle command requires four parameters as valid numbers";
              return Tuple.create(Optional.empty(), error);
          }

          if (command == "move" && arguments.length != 3)
          {
              error = "The move command requires three parameters as valid numbers";
              return Tuple.create(Optional.empty(), error);
          }

          if (command == "remove" && arguments.length != 1) 
          {
              error = "The remove command requires one parameter as a valid number";
              return Tuple.create(Optional.empty(), error);
          }

          if (command == "remove" && arguments.length == 1)
          {
              double shapeIndex = arguments[0];
              boolean shapeIndexIsWholeNumber = Math.floor(shapeIndex) == shapeIndex;
              if (!shapeIndexIsWholeNumber)
              {
                  error = "The argument of the remove command must be a whole number";
                  return Tuple.create(Optional.empty(), error);
              }
          }
          
          // command 
          error = "";
          Command parserCommand = new Command(command, arguments);
          return Tuple.create(Optional.of(parserCommand), error);
      }
    } 
}