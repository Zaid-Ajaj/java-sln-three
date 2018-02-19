import java.lang.*;
import java.util.*;

public class Test
{
    private static String testContextKey = "";
    private static Boolean hasFailed = false;
    private static Map<String, ArrayList<String>> testContext = new Hashtable<String, ArrayList<String>>();


    public static void Report()
    {
        if (hasFailed)
        {
            throw new Error("One or more assertions has failed");
        }
    }

    public static void Case(String testName, Runnable expresstion)
    {
        if (!testContext.containsKey(testName)) 
        {
            testContext.put(testName, new ArrayList<String>());
        }

        try 
        {
            testContextKey = testName;
            expresstion.run();
            System.out.println("✓ " + testName);
            ArrayList<String> testCases = testContext.get(testContextKey);
            for(String testCase : testCases) 
            {
                System.out.println(testCase);
            }
        }
        catch (Exception ex)
        {
            System.out.println("× " + testContextKey);
            ArrayList<String> testCases = testContext.get(testContextKey);
            for(String testCase : testCases) 
            {
                System.out.println(testCase);
            }

            StackTraceElement[] stackTrace = ex.getStackTrace();
            for(StackTraceElement el : stackTrace) 
            {
                String stacktrace = el.toString();
                if (stacktrace.contains("Runner.java") && stacktrace.contains("lambda$")) 
                {
                    String errorLine = stacktrace.substring(stacktrace.indexOf(":") + 1,stacktrace.indexOf(")"));
                    System.out.println("    × At Line: " + errorLine); 
                }
            }
        }    
    }

    public static <T extends Comparable<T>> void AreEqual(T x, T y, String msg)
    {
        ArrayList<String> testCases = testContext.get(testContextKey);

        if (x.compareTo(y) != 0) 
        {
            testCases.add("  × " + msg);
            testCases.add("    × " + "Expected " + x + " but got " + y);
            hasFailed = true;
            throw new RuntimeException();
        }
        else
        {
            testCases.add("  ✓ " + msg + "");
        }
    }

    public static <T extends Comparable<T>> void AreEqual(T x, T y)
    {
        ArrayList<String> testCases = testContext.get(testContextKey);
        if (x.compareTo(y) != 0) 
        {
            testCases.add("  × Unspecified test case ");
            testCases.add("    × " + "Expected " + x + " but got " + y);
            hasFailed = true;
            throw new RuntimeException();
        }
    }


}