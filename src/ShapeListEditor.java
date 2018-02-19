import java.lang.*;
import java.util.*;

public class ShapeListEditor
{
    private ShapeList shapeList;
    private Scanner scanner;
    private boolean stopped;

    public ShapeListEditor()
    {
        shapeList = new ShapeList();
        scanner = new Scanner(System.in);
        stopped = false;
    }

    /** Read user input after prompting him/her with a message  */
    public String readUserInput(String inputMsg)
    {
        System.out.print(inputMsg);

        if (scanner.hasNextLine())
        {
            return scanner.nextLine();
        }

        return "";
    } 

    /**  Safely parses integers from an input string, returning empty value when parsing failes. */
    public OptionalInt parseInt(String input)
    {
        try 
        {
            int result = Integer.parseInt(input);
            return OptionalInt.of(result);
        }
        catch (Exception ex)
        {
            return OptionalInt.empty();
        }
    }

    void writeLn(String input)
    {
        System.out.println(input);
    }

    void write(String input)
    {
        System.out.print(input);
    }

    public void stop()
    {
        stopped = true;
    }

    private void showWelcomeMessages()
    {
        writeLn("Welcome to the shape list editor.");
        writeLn("Enter a command to manipulate the list:");

        String[] commands = 
        {
            "'quit' => stops the program",
            "'show' => lists the geometric objects",
            "'circle x y r' => adds a circle at (x, y) with radius r if the array is not full",
            "'rectangle x y h w' => adds a rectangle at (x, y) with height h and width w",
            "'move i dx dy' => moves the i-th object over the specified distance in x and y direction",
            "'remove i' => removes the i-th object.",
            "'sort x|y' => sorts the list"
        };

        Arrays.stream(commands).forEach(cmd -> writeLn("  |-- " + cmd));
        writeLn("");
    }

    public Optional<Tuple<String, int[]>> parseCommand(String input)
    {
        String[] parts = input.split(" ");

        Arrays.stream(parts).forEach(part -> writeLn("'" + part + "'"));
        
        if (parts.length == 0)
        {
            // empty input
            writeLn("Your input was empty");
            return Optional.empty();
        }
        else if (parts.length == 1 && parts[0] == "")
        {
            // empty input
            writeLn("Your input was empty");
            return Optional.empty();
        }
        else if (parts.length == 1)
        {
            String command = parts[0];
            // the only commands without paramters are 'show' and 'quit'
            if (command.equalsIgnoreCase("show") || command.equalsIgnoreCase("quit"))
            {
                // no-argument command has 0 arguments 
                writeLn("Found single argument");
                int[] args = { };
                return Optional.of(new Tuple(command, args));
            }
            else
            {
                writeLn("The single argument command you entered was not recognized");
                return Optional.empty();
            }
        }
        else if (parts.length == 2 && parts[0].equalsIgnoreCase("sort"))
        {
            return Optional.empty();
        }
        else
        {
            String[] possibleCmds = { "circle", "rectangle", "move", "remove" };
            // multi-parameter command
            String command = parts[0];
            // if the command entered is not one of the possible commands
            if (!Arrays.stream(possibleCmds).noneMatch(cmd -> cmd.equalsIgnoreCase(command)))
            {
                writeLn("The command you entered was not recognized");
                return Optional.empty();
            }

            int[] arguments = Arrays.stream(parts)
                                    .skip(1)
                                    .map(arg -> parseInt(arg))
                                    .filter(optArg -> optArg.isPresent())
                                    .mapToInt(optArg -> optArg.getAsInt())
                                    .toArray();
                                    

            if (command == "circle" && arguments.length != 3)
            {
                writeLn("The circle command requires three parameters as integers");
                return Optional.empty();
            }

            if (command == "rectangle" && arguments.length != 4)
            {
                writeLn("The rectangle command requires four parameters as integers");
                return Optional.empty();
            }

            if (command == "move" && arguments.length != 3)
            {
                writeLn("The move command requires three parameters as integers");
                return Optional.empty();
            }

            if (command == "remove" && arguments.length != 1) 
            {
                writeLn("The remove command requires one parameter as an integer");
                return Optional.empty();
            }

            return Optional.of(new Tuple(command, arguments));
        }
    }

    public void start()
    {   
        showWelcomeMessages();

        while (!stopped)
        {
            String input = readUserInput("Command: ");
            Optional<Tuple<String, int[]>> parsedCommand = parseCommand(input);

            if (!parsedCommand.isPresent())
            {
                continue;
            }

            String command = parsedCommand.get().x;
            int[] args = parsedCommand.get().y;

            if (command.equalsIgnoreCase("quit")) 
            {
                stop();
                continue;
            }

            if (command.equalsIgnoreCase("show"))
            {
                writeLn("Shape list contains:");
                shapeList.showUsing(shape -> writeLn(" |-- " + shape.toString()));
                continue;
            }

        }

        writeLn("Finished editing the shape list");
    }
}