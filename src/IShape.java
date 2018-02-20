/** Defines common shape operations
 * 
 * @author Zaid Ajaj - s4807561
 * @author Luna-Elise Schernthaner - s4703928 
 */
public interface IShape
{
    double Area();
    double TopBorder();
    double RightBorder();
    double LeftBorder();
    double BottomBorder();
    void Move(double dx, double dy);
}