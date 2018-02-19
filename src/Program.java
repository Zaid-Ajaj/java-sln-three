import java.util.*;

public class Program
{
    public static void main(String[] args)
    {
        System.out.println("Hello from Java");
        
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        
        int result = Util.Sum(numbers);

        System.out.println("Sum = " + result);
    }
}