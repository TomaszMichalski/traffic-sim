import java.util.*;

public class Junction
{
    public Junction(double posX, double posY)
    {
        connectors = new ArrayList<Road>();
        this.posX = posX;
        this.posY = posY;
        passage = Passage.PASSAGE_ALL;
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
     */
    public void changePassage()
    {
        if(passage == Passage.PASSAGE_ALL) return;
        if(passage == Passage.PASSAGE_HORIZONTAL) passage = Passage.PASSAGE_VERTICAL;
        else if(passage == Passage.PASSAGE_VERTICAL) passage = Passage.PASSAGE_HORIZONTAL;
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

    private ArrayList<Road> connectors;
    private double posX;
    private double posY;
    private Passage passage;
}
