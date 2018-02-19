import java.util.*;
import java.lang.*;

public class Util
{
    public static int Sum(List<Integer> xs)
    {
        int acc = 0;
        for (int x : xs)
        {
            acc = acc + x;
        }

        return acc;
    }
}