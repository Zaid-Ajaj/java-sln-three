import java.util.*;
import java.util.function.Consumer;
import java.lang.*;

/**
 * Contains logic for manipulating shapes inside a list 
 * 
 * @author Zaid Ajaj - s4807561
 * @author Luna-Elise Schernthaner - s4703928
 */
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
                          && width >= 0
                          && !isFull();
                          
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
        if (radius < 0 || x == Double.NaN || y == Double.NaN || radius == Double.NaN || isFull()) 
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

    /** Safely removes a shape from the list at index i, returns an optional string representing whether there was an error or not */
    public Optional<Error> removeShapeAtIndex(int i)
    {
        if (this.shapes.size() == 0) 
        {
            return Optional.of(Error.of("The list is already empty"));
        }
        else if (i < 0 || i >= this.shapes.size())
        {
            return Optional.of(Error.of("The input index lies outside the bounds of the shape list"));
        }
        else
        {
            this.shapes.remove(i);
            return Optional.empty();
        }
    }

    public Optional<Error> moveShapeAtIndex(int i, double deltaX, double deltaY)
    {
        if (this.shapes.size() == 0) 
        {
            return Optional.of(Error.of("The list is already empty"));
        }
        else if (i < 0 || i >= this.shapes.size())
        {
            return Optional.of(Error.of("The input index lies outside the bounds of the shape list"));
        }
        else
        {
            IShape shape = this.shapes.get(i);
            shape.Move(deltaX ,deltaY);
            return Optional.empty();
        }
    }

    /** Returns whether or not the shape list is empty */
    public boolean isEmpty()
    {
        return shapes.size() == 0;
    }

    public void sortByArea()
    {
        IShape[] shapesArray = new IShape[shapes.size()];
        shapes.toArray(shapesArray);
        Arrays.sort(shapesArray);
        shapes = List.of(shapesArray);
    }

    public void sortByLeftBorder()
    {
        IShape[] shapesArray = new IShape[shapes.size()];
        shapes.toArray(shapesArray);
        
        Arrays.sort(shapesArray, new Comparator<IShape>() {
            @Override
            public int compare(IShape a, IShape b) {
                return  a.LeftBorder() > b.LeftBorder() ? 1
                     :  a.LeftBorder() < b.LeftBorder() ? -1
                     :  0;
            }
        });
        
        shapes = List.of(shapesArray);
    }

    public void sortByBottomBorder()
    {
        IShape[] shapesArray = new IShape[shapes.size()];
        shapes.toArray(shapesArray);
        
        Arrays.sort(shapesArray, new Comparator<IShape>() {
            @Override
            public int compare(IShape a, IShape b) {
                return a.BottomBorder() > b.BottomBorder() ? 1
                    :  a.BottomBorder() < b.BottomBorder() ? -1
                    :  0;
            }
        });
        
        shapes = List.of(shapesArray);
    }

    /** Allows a read-only consumer to read the string representation of a shape
     * Therefore, forbidding the outside world from mutating the values of the shapes
     */
    public void readShapesUsing(Consumer<String> consumer)
    {
        for(IShape shape : shapes)
        {
            consumer.accept(shape.toString());
        }
    }
}