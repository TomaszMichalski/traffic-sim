import java.util.*;

public class Junction
{
    public Junction(double posX, double posY)
    {
        connectors = new ArrayList<Road>();
        this.posX = posX;
        this.posY = posY;
        passage = Passage.PASSAGE_ALL;
        //sets the traffic light change delay to random value between 5 and 10 seconds (the value is stored in nanoseconds)
        TRAFFIC_LIGTH_DELAY = (long)((new Random().nextInt(5) + 5) * 1e9);
    }

    /*
        Connects a new road to the junction
        If road start/end coords do not match with junction coords then RoadCoordException is thrown
     */
    public void addConnector(Road road) throws RoadCoordException
    {
        if((road.getRoadLine().getStartX() - posX < 10e-6 && road.getRoadLine().getStartY() - posY < 10e-6)
            || (road.getRoadLine().getEndX() - posX < 10e-6 && road.getRoadLine().getEndY() - posY < 10e-6))
        {
            connectors.add(road);
        }
        else throw new RoadCoordException();
        if(connectors.size() > 2)
        {
            Random rand = new Random();
            int passageRand = rand.nextInt();
            if(passageRand % 2 == 0) passage = Passage.PASSAGE_HORIZONTAL;
            else passage = Passage.PASSAGE_VERTICAL;
        }
    }

    /*
        Returns the connected road seen as 'ahead' road from current param Road
        Return null if there is no such road
     */
    public Road getStraight(Road current)
    {
        for(Road connector : connectors)
        {
            if(connector != current && connector.getOrientation() == current.getOrientation())
                return connector;
        }
        return null;
    }

    /*
        Returns the connected road seen as 'left turn' road from current param Road
        Return null if there is no such road
     */
    public Road getLeft(Road current)
    {
        for(Road connector : connectors)
        {
            if(current.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
            {
                if(connector.getOrientation() == Road.RoadOrientation.RO_VERTICAL) //seek for vertical connectors
                {
                    if(connector.getRoadLine().getStartY() > current.getRoadLine().getStartY() //if the road lies below then it's y-coord is greater
                            || connector.getRoadLine().getEndY() > current.getRoadLine().getStartY())
                    {
                        return connector;
                    }
                }
            }
            else //for vertical roads
            {
                if(connector.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //seek for horizontal connectors
                {
                    if(connector.getRoadLine().getStartX() < current.getRoadLine().getStartX() //if the road lies on the left then it's x-coord is smaller
                            || connector.getRoadLine().getEndX() < current.getRoadLine().getStartX())
                    {
                        return connector;
                    }
                }
            }
        }
        return null;
    }

    /*
        Returns the connected road seen as 'right turn' road from current param Road
        Return null if there is no such road
     */
    public Road getRight(Road current)
    {
        for(Road connector : connectors)
        {
            if(current.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
            {
                if(connector.getOrientation() == Road.RoadOrientation.RO_VERTICAL) //seek for vertical connectors
                {
                    if(connector.getRoadLine().getStartY() < current.getRoadLine().getStartY() //if the road lies abowe then it's y-coord is smaller
                            || connector.getRoadLine().getEndY() < current.getRoadLine().getStartY())
                    {
                        return connector;
                    }
                }
            }
            else //for vertical roads
            {
                if(connector.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //seek for horizontal connectors
                {
                    if(connector.getRoadLine().getStartX() > current.getRoadLine().getStartX() //if the road lies on the right then it's x-coord is greater
                            || connector.getRoadLine().getEndX() > current.getRoadLine().getStartX())
                    {
                        return connector;
                    }
                }
            }
        }
        return null;
    }
    /*
        Return the size of a junction - number of road connected to it (1-4)
     */
    public int getSize()
    {
        return connectors.size();
    }

    /*
        Return current passsage state
     */
    public Passage getPassage()
    {
        return passage;
    }

    /*
        Changes passage state - from "green light" to "red light" and from "red light" to "green light"
        If junction has passage state of "pass all" then function has no effect
        Overwrites the previous passage change time to now param value
     */
    public void changePassage(long now)
    {
        if(passage == Passage.PASSAGE_ALL) return;
        if(passage == Passage.PASSAGE_HORIZONTAL) passage = Passage.PASSAGE_VERTICAL;
        else if(passage == Passage.PASSAGE_VERTICAL) passage = Passage.PASSAGE_HORIZONTAL;
        previousPassageChangeTime = now;
    }

    /*
        Returns time at which the passage changed last time as a long value
     */
    public long getPreviousPassageChangeTime()
    {
        return previousPassageChangeTime;
    }

    /*
        Defines possible state of junction's passage - either vertical or horizontal "green light"
        If a junction has less than 3 connectors it has a state of continuous pass, as there are no street lights
     */
    public enum Passage
    {
        PASSAGE_HORIZONTAL,
        PASSAGE_VERTICAL,
        PASSAGE_ALL
    }

    public double getPosX()
    {
        return posX;
    }

    public double getPosY()
    {
        return posY;
    }

    public long getTrafficLightDelay()
    {
        return TRAFFIC_LIGTH_DELAY;
    }

    public String toString()
    {
        return "Junction [x=" + posX + ",y=" + posY + ",size=" + getSize() + "," + passage + "]";
    }

    private ArrayList<Road> connectors;
    private double posX;
    private double posY;
    private Passage passage;
    private final long TRAFFIC_LIGTH_DELAY;
    private long previousPassageChangeTime;
}
