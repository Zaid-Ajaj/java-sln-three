import java.lang.*;
import java.util.*;

public class Runner 
{
    public static void main(String[] args)
    {
        Test.Case("Sum of empty list is zero", () -> 
        {   
            List<Integer> emptyList = new ArrayList<Integer>();
            int sum = Util.Sum(emptyList);
            Test.AreEqual(0, sum, "Sum is zero");
        });

        Test.Case("Sum(1 .. 10) = 55", () ->
        {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 10; i++) 
            {
                numbers.add(i);
            }
            int sum = Util.Sum(numbers);

            Test.AreEqual(55, sum, "The sum is correct");
        });

        Test.Report();
    }
}