package po.trafficsim.exception;

public class RoadCoordException extends Exception
{
    public RoadCoordException()
    {
        super("Road coordinates does not match junction coordinates. Road not added to the junction");
    }
}
