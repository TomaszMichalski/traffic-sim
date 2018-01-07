package po.trafficsim.world;

import java.util.*;
import po.trafficsim.exception.*;

public class Junction
{
    public Junction(double posX, double posY)
    {
        connectors = new ArrayList<>();
        this.posX = posX;
        this.posY = posY;
        passage = Passage.PASSAGE_ALL;
        //sets the traffic light change delay to random value between 1 and 4 seconds (the value is stored in nanoseconds)
        TRAFFIC_LIGHT_DELAY = (long)((new Random().nextInt(3) + 1) * 1e9);
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
        if(current.equals(getNorthConnector())) return getEastConnector();
        if(current.equals(getEastConnector())) return getSouthConnector();
        if(current.equals(getSouthConnector())) return getWestConnector();
        if(current.equals(getWestConnector())) return getNorthConnector();
        return null;
    }

    /*
        Returns the connected road seen as 'right turn' road from current param Road
        Return null if there is no such road
     */
    public Road getRight(Road current)
    {
        if(current.equals(getNorthConnector())) return getWestConnector();
        if(current.equals(getEastConnector())) return getNorthConnector();
        if(current.equals(getSouthConnector())) return getEastConnector();
        if(current.equals(getWestConnector())) return getSouthConnector();
        return null;
    }
    /*
        Returns junction's north connector
        If it does not exist, returns null
     */
    private Road getNorthConnector()
    {
        for(Road connector : connectors)
        {
            if(connector.getRoadLine().getStartY() < posY || connector.getRoadLine().getEndY() < posY)
                return connector;
        }
        return null;
    }
    /*
        Returns junction's east connector
        If it does not exist, returns null
     */
    private Road getEastConnector()
    {
        for(Road connector : connectors)
        {
            if(connector.getRoadLine().getStartX() > posX || connector.getRoadLine().getEndX() > posX)
                return connector;
        }
        return null;
    }
    /*
        Returns junction's south connector
        If it does not exist, returns null
     */
    private Road getSouthConnector()
    {
        for(Road connector : connectors)
        {
            if(connector.getRoadLine().getStartY() > posY || connector.getRoadLine().getEndY() > posY)
                return connector;
        }
        return null;
    }
    /*
        Returns junction's west connector
        If it does not exist, returns null
     */
    private Road getWestConnector()
    {
        for(Road connector : connectors)
        {
            if(connector.getRoadLine().getStartX() < posX || connector.getRoadLine().getEndX() < posX)
                return connector;
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
        Return current passage state
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
        return TRAFFIC_LIGHT_DELAY;
    }

    public String toString()
    {
        return "Junction [x=" + posX + ",y=" + posY + ",size=" + getSize() + "," + passage + "]";
    }

    private ArrayList<Road> connectors;
    private double posX;
    private double posY;
    private Passage passage;
    private final long TRAFFIC_LIGHT_DELAY;
    private long previousPassageChangeTime;
}
