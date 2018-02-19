import java.lang.*;
import java.util.*;

public class Runner 
{
    public static void main(String[] args)
    {
        Test.Case("parse command works correctly for single argument 'show'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Tuple<String, int[]>> parsed = editor.parseCommand("show");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().x, "show", "The parsed command is correct");
            Test.AreEqual(parsed.get().y.length, 0, "The parsed arguments have length 0");
        });

        Test.Case("parse command works correctly for single argument 'quit'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Tuple<String, int[]>> parsed = editor.parseCommand("quit");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().x, "quit", "The parsed command is correct");
            Test.AreEqual(parsed.get().y.length, 0, "The parsed arguments have length 0");
        });

        Test.Case("parse command works correctly for multi-argument command 'circle'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Tuple<String, int[]>> parsed = editor.parseCommand("circle 1 2 3");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().x, "circle", "The parsed command is correct");
            int[] arguments = parsed.get().y;
            Test.AreEqual(arguments.length, 3, "The parsed arguments have length 0");
            Test.AreEqual(arguments[0], 1, "First argument is parsed");
            Test.AreEqual(arguments[1], 2, "Second argument is parsed");
            Test.AreEqual(arguments[2], 3, "Third argument is parsed");
        });

        Test.Report();
    }
}