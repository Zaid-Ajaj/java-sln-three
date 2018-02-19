import java.lang.*;
import java.util.*;

public class Runner 
{
    public static void main(String[] args)
    {
        Test.Case("parse double works", () -> 
        {
            ShapeListEditor editor = new ShapeListEditor();
            OptionalDouble result = editor.parseDouble("1.0");
            Test.AreEqual(true, result.isPresent(), "parsing works for 1.0");
            Test.AreEqual(1.0, result.getAsDouble(), "The value of double is correct for 1.0");

            result = editor.parseDouble("1");
            Test.AreEqual(true, result.isPresent(), "parsing works for 1");
            Test.AreEqual(1.0, result.getAsDouble(), "The value of double is correct for 1");
        });

        Test.Case("parse command works correctly for single argument 'show'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Command> parsed = editor.parseCommand("show");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().name, "show", "The parsed command is correct");
            Test.AreEqual(parsed.get().arguments.length, 0, "The parsed arguments have length 0");
        });

        Test.Case("parse command works correctly for single argument 'quit'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Command> parsed = editor.parseCommand("quit");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().name, "quit", "The parsed command is correct");
            Test.AreEqual(parsed.get().arguments.length, 0, "The parsed arguments have length 0");
        });

        Test.Case("parse command works correctly for multi-argument command 'circle'", () -> 
        {   
            ShapeListEditor editor = new ShapeListEditor();
            Optional<Command> parsed = editor.parseCommand("circle 1.0 2.0 3.0");

            Test.AreEqual(true, parsed.isPresent(), "The result has a value");
            Test.AreEqual(parsed.get().name, "circle", "The parsed command is correct");
            double[] arguments = parsed.get().arguments;
            Test.AreEqual(arguments.length, 3, "The parsed arguments have length 0");
            Test.AreEqual(arguments[0], 1.0, "First argument is parsed");
            Test.AreEqual(arguments[1], 2.0, "Second argument is parsed");
            Test.AreEqual(arguments[2], 3.0, "Third argument is parsed");
        });

        Test.Report();
    }
}