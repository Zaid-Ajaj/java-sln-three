/** A container for an error message, can be sean as an alias for string but makes type definitions with error strings more readable. 
 * i.e. Optional of Error is more specific than Optional of String  
 * */
public class Error 
{
    public final String message;

    private Error(String message)
    {
        this.message = message;
    }

    public static Error of(String msg)
    {
        return new Error(msg);
    }
}