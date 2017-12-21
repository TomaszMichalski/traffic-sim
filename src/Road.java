import javafx.scene.shape.Line;

public class Road
{
    public Road(double startX, double startY, double endX, double endY)
    {
        //set the line representation of a road
        roadLine = new Line(startX, startY, endX, endY);
        //define road orientation
        if(Math.abs(startY - endY) < 10e-6) //startX == endX
            orientation = RoadOrientation.RO_HORIZONTAL;
        else if(Math.abs(startX - endX) < 10e-6) //startY - endY
            orientation = RoadOrientation.RO_VERTICAL;
        //set default junctions
        Junction defStart = new Junction(startX, startY);
        Junction defEnd = new Junction(endX, endY);
        try
        {
            setJunctions(defStart, defEnd);
        }
        catch(JunctionCoordException e)
        {
            e.printStackTrace();
        }
    }

    public Line getRoadLine()
    {
        return roadLine;
    }

    /*
        Set juctions passed as parameters to the road
        Matches junctions coords to start/end of road
        If coords do not match, JunctionCoordException is thrown
     */
    public void setJunctions(Junction s, Junction e) throws JunctionCoordException
    {
        if(s.getPosX() - roadLine.getStartX() < 10e-6 && s.getPosY() - roadLine.getStartY() < 10e-6
                && e.getPosX() - roadLine.getEndX() < 10e-6 && e.getPosY() - roadLine.getEndY() < 10e-6)
        {
            startJunction = s;
            endJunction = e;
        }
        else if(s.getPosX() - roadLine.getEndX() < 10e-6 && s.getPosY() - roadLine.getEndY() < 10e-6
                && e.getPosX() - roadLine.getStartX() < 10e-6 && e.getPosY() - roadLine.getStartY() < 10e-6)
        {
            endJunction = s;
            startJunction = e;
        }
        else throw new JunctionCoordException();
    }

    public Junction getStartJunction()
    {
        return startJunction;
    }

    public Junction getEndJunction()
    {
        return endJunction;
    }

    /*
       Defines possible orientations of a road - either horizontal or vertical
    */
    public enum RoadOrientation
    {
        RO_HORIZONTAL,
        RO_VERTICAL
    }

    public RoadOrientation getOrientation() {
        return orientation;
    }

    public String toString()
    {
        return "Road [startX=" + roadLine.getStartX() + ",startY=" + roadLine.getStartY() + ",endX=" + roadLine.getEndX()
                + ",endY=" + roadLine.getEndY() + "," + orientation + "]";
    }

    private Line roadLine;
    private RoadOrientation orientation;
    //junctions are defined by MapSchema addRoad() method
    private Junction startJunction;
    private Junction endJunction;
}
