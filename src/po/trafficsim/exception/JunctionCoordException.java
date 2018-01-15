package po.trafficsim.exception;

public class JunctionCoordException extends Exception
{
    public JunctionCoordException()
    {
        super("Junctions coordinates does not match road start/end coordinates. Junctions reset to default.");
    }
}
