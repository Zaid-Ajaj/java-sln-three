/** An ad-hoc implementation of the Result datatype as a tuple container. It holds two values representing a value of success of type `TVal` or an error of some type `TError`  
 * @author Zaid Ajaj
 */
public class Result<TVal> 
{ 
    public final TVal value; 
    public final Error error; 
    
    public Result(TVal value, Error error) 
    { 
      this.value = value; 
      this.error = error; 
    } 

    /** Result Factory */
    public static <T> Result<T> create(T value, Error error)
    {   
        return new Result(value, error);
    }
  } 
  