import java.lang.*;
import java.util.*;

public class ShapeListEditor
{
    private ShapeList shapeList;
    private Scanner scanner;
    private boolean stopped;

    public ShapeListEditor()
    {
        shapeList = new ShapeList(10);
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

    /**  Safely parses doubles from an input string, returning an empty value when parsing failes. */
    public OptionalDouble parseDouble(String input)
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

    void writeLn(String input)
    {
        System.out.println(input);
    }

    void write(String input)
    {
        System.out.print(input);
    }

    /** Stops the editor loop */
    public void quit()
    {
        stopped = true;
    }

    /** Shows the user what commands are possible */
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

    /** Safely parses the input string of the user into a structured command with it's name and arguments */
    public Optional<Command> parseCommand(String input)
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
                double[] args = { };
                return Optional.of(new Command(command, args));
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
            if (Arrays.stream(possibleCmds).noneMatch(cmd -> cmd.equalsIgnoreCase(command)))
            {
                // then parsing should fail
                writeLn("The command you entered was not recognized as one of the possible commands");
                return Optional.empty();
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

            return Optional.of(new Command(command, arguments));
        }
    }

    /** Shows the elements of the shape list to the user, if any. */
    private void show()
    {
        if (shapeList.isEmpty())
        {
            writeLn("Shape list is empty");
        }
        else
        {
            writeLn("Shape list contains:");
            shapeList.readShapesUsing(shape -> writeLn(" |-- " + shape.toString()));
            writeLn("");
        }
    }

    /** Handles the circle command along with it's arguments */
    private void handleCircle(double x, double y, double radius)
    {
        if (shapeList.isFull())
        {
            writeLn("Cannot add new circle: shape list is full.");
            return;
        }

        Optional<Circle> addedCircle = shapeList.addCircle(x, y, radius);
        if (!addedCircle.isPresent())
        {
            writeLn("Error while adding a new circle: input was invalid");
        }
        else
        {
            Circle circle = addedCircle.get();
            writeLn(circle.toString() + " was added");
        }
    }

    /** Handles the rectangle command along with it's arguments */
    private void handleRectangle(double x, double y, double height, double width)
    {
        if (shapeList.isFull())
        {
            writeLn("Cannot add new rectangle: shape list is full.");
            return;
        }

        Optional<Rectangle> addedRectangle = shapeList.addRectangle(x, y, height, width);
        if (!addedRectangle.isPresent())
        {
            writeLn("Error while adding a new rectangle: input was invalid");
        }
        else
        {
            Rectangle rectangle = addedRectangle.get();
            writeLn(rectangle.toString() + " was added");
        }
    }
    
     /** Starts the editor as an application loop, reading and handling commands and stops when the quit command is recieved */
    public void start()
    {   
        showWelcomeMessages();

        while (!stopped)
        {
            String input = readUserInput("Command: ");
            Optional<Command> parsedCommand = parseCommand(input);

            if (!parsedCommand.isPresent())
            {
                continue;
            }

            String command = parsedCommand.get().name;
            double[] args = parsedCommand.get().arguments;

            if (command.equalsIgnoreCase("quit")) 
            {
                quit();
                continue;
            }

            if (command.equalsIgnoreCase("show"))
            {
                show();
                continue;
            }

            if (command.equalsIgnoreCase("circle"))
            {
                double xCoordinate = args[0];
                double yCoordinate = args[1];
                double radius = args[2];
                handleCircle(xCoordinate, xCoordinate, radius);
                continue;
            }

            if (command.equalsIgnoreCase("rectangle"))
            {
                double xCoordinate = args[0];
                double yCoordinate = args[1];
                double height = args[2];
                double width = args[3];
                handleRectangle(xCoordinate, yCoordinate, height, width);
                continue;
            }

            if (command.equalsIgnoreCase("remove"))
            {

                continue;
            }

            if (command.equalsIgnoreCase("move"))
            {

                continue;
            }
        }

        writeLn("Finished editing the shape list");
    }
}