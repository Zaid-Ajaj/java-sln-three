import java.util.*;
import java.util.function.Consumer;
import java.lang.*;

public class ShapeList
{
    private List<IShape> shapes;
    private boolean stopped;
    private int maxSize;

    public ShapeList(int maxSize)
    {
        shapes = new ArrayList<IShape>();
        stopped = false;
        this.maxSize = Math.max(0, maxSize);
    }

    public boolean stopped()
    {
        return stopped;
    }

    public boolean isFull()
    {
        return this.maxSize == shapes.size();
    }

    public void stop() 
    {
        stopped = true;
    }
    

    public Optional<Rectangle> addRectangle(double x, double y, double height, double width)
    {
        boolean inputValid = x != Double.NaN
                          && y != Double.NaN
                          && height != Double.NaN
                          && width != Double.NaN
                          && height >= 0
                          && width >= 0;
        if (!inputValid) 
        {
            return Optional.empty();
        }
        else
        {
            Rectangle rect = new Rectangle(x, y, height, width);
            this.shapes.add(rect);
            return Optional.of(rect);
        }
    }

    public Optional<Circle> addCircle(double x, double y, double radius)
    {
        if (radius < 0 || x == Double.NaN || y == Double.NaN || radius == Double.NaN) 
        {
            return Optional.empty();
        }
        else
        {
            Circle circle = new Circle(x, y , radius);
            this.shapes.add(circle);
            return Optional.of(circle);
        }
    }

    public Optional<String> removeShapeAtIndex(int i)
    {
        if (this.shapes.size() == 0) 
        {
            return Optional.of("The list is already empty");
        }
        else if (i < 0 || i >= this.shapes.size())
        {
            return Optional.of("The input index lies outside the bounds of the shape list");
        }
        else
        {
            this.shapes.remove(i);
            return Optional.empty();
        }
    }

    public boolean isEmpty()
    {
        return shapes.size() == 0;
    }

    public void readShapesUsing(Consumer<IShape> consumer)
    {
        for(IShape shape : shapes)
        {
            consumer.accept(shape);
        }
    }
}