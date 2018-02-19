public interface IShape
{
    double Area();
    double TopBorder();
    double RightBorder();
    double LeftBorder();
    double BottomBorder();
    void Move(double dx, double dy);
}