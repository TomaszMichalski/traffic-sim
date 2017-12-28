package po.trafficsim.world;

import javafx.scene.shape.Line;
import po.trafficsim.exception.*;

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
        setDefaultJunctions();
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
        else
        {
            setDefaultJunctions();
            throw new JunctionCoordException("Junction coordinates do not match road coordinates. Junctions reset to default");
        }
    }

    public void setDefaultJunctions()
    {
        Junction defStart = new Junction(roadLine.getStartX(), roadLine.getStartY());
        Junction defEnd = new Junction(roadLine.getEndX(), roadLine.getEndY());
        try
        {
            setJunctions(defStart, defEnd);
        }
        catch(JunctionCoordException e)
        {
            e.printStackTrace();
        }
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
                + ",endY=" + roadLine.getEndY() + "," + orientation + "]\n" + startJunction.toString()
                + "\n" + endJunction.toString();
    }

    public boolean equals(Object o)
    {
        if(o instanceof Road)
        {
            Road r = (Road)o;
            if(r == this) return true;
            else if((r.getRoadLine().getStartX() == this.getRoadLine().getStartX() &&
                    r.getRoadLine().getStartY() == this.getRoadLine().getStartY() &&
                    r.getRoadLine().getEndX() == this.getRoadLine().getEndX() &&
                    r.getRoadLine().getEndY() == this.getRoadLine().getEndY()) ||
                    (r.getRoadLine().getStartX() == this.getRoadLine().getEndX() &&
                     r.getRoadLine().getStartY() == this.getRoadLine().getEndY() &&
                     r.getRoadLine().getEndX() == this.getRoadLine().getStartX() &&
                     r.getRoadLine().getEndY() == this.getRoadLine().getStartY()))
                return true;
        }
        return false;
    }

    private Line roadLine;
    private RoadOrientation orientation;
    //junctions are defined by MapSchema addRoad() method
    private Junction startJunction;
    private Junction endJunction;
}
