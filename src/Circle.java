import java.lang.*;

public class Circle implements IShape, Comparable<IShape> 
{
    private double x, y, radius = 0.0;
    
    public Circle(double x, double y, double radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double Area()
    {
        return Math.PI * radius * radius;
    }

    public double TopBorder()
    {
        return y + radius;
    }

    public double BottomBorder()
    {
        return y - radius;
    }

    public double RightBorder()
    {
        return x + radius;
    }

    public double LeftBorder()
    {
        return x - radius;
    }

    public void Move(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    public int compareTo(IShape otherShape)
    {
        double circleArea = Area();
        double otherArea = otherShape.Area();
        if (circleArea == otherArea) 
        {
            return 0;
        }
        else if (circleArea < otherArea)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public String toString()
    {
        return "Cirlce(X=" + x + ", Y= " + y + ", Radius = " + radius + ")";
    } 
}