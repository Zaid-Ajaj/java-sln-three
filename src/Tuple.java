/** An ad-hoc implementation of the Either datatype as a tuple container. It holds two values representing a value of success of type `TVal` or an error of some type `TError`  */
public class Tuple<TVal, TError> 
{ 
    public final TVal value; 
    public final TError error; 
    
    public Tuple(TVal value, TError error) 
    { 
      this.value = value; 
      this.error = error; 
    } 

    /** Tuple Factory */
    public static <T, E> Tuple<T, E> create(T value, E error)
    {   
        return new Tuple(value, error);
    }
  } 
  