import java.lang.*;
import java.util.*;

/** Uses the terminal as a user interface to manipulate a list of shapes by reading different commands from the user 
 * 
 * @author Zaid Ajaj - s4807561
 * @author Luna-Elise Schernthaner - s4703928
 */
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

    public boolean finishedEditing()
    {
        return stopped;
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
            shapeList.readShapesUsing(shape -> writeLn(" |-- " + shape));
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

        while (!finishedEditing())
        {
            String input = readUserInput("Command: ");
            Tuple<Optional<Command>, String> parsedResult = Command.tryParse(input);
            
            Optional<Command> parsedCommand = parsedResult.value;
            String parseError = parsedResult.error;


            if (!parsedCommand.isPresent())
            {
                writeLn(parseError);
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
                int index = (int)args[0];
                
                Optional<String> error = shapeList.removeShapeAtIndex(index);
                
                if (error.isPresent())
                {
                    writeLn("Error while removing a shape from the list: " + error);
                }

                continue;
            }

            if (command.equalsIgnoreCase("move"))
            {
                int index = (int)args[0];
                double deltaX = args[1];
                double deltaY = args[2];

                continue;
            }
        }

        writeLn("Finished editing the shape list");
    }
}