/** Represents a rectangle shape 
 * 
 * @author Zaid Ajaj - s4807561
 * @author Luna-Elise Schernthaner - s4703928
 */
public class Rectangle implements IShape, Comparable<IShape> 
{
    private double x, y, height, width = 0.0;

    public Rectangle(double x, double y, double height, double width)
    {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /** Returns the area of the rectangle */
    public double Area() 
    {
        return height * width;
    }

    public double TopBorder()
    {
        return y + height;
    }

    public double LeftBorder()
    {
        return x;
    }

    public double RightBorder()
    {
        return x + width;
    }

    public double BottomBorder()
    {
        return y;
    }

    public int compareTo(IShape otherShape)
    {
        double rectangleArea = Area();
        double otherArea = otherShape.Area();
        if (rectangleArea == otherArea) 
        {
            return 0;
        }
        else if (rectangleArea < otherArea)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

    public void Move(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    @Override
    public String toString()
    {
        return "Rectangle(X=" + x + ", Y=" + y + ", Height=" + height + ", Width=" + width + ")";
    } 

}