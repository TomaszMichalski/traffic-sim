/*
    Basic implementation of ICollisionEngine
 */

public class CollisionEngine implements ICollisionEngine
{
    public CollisionEngine(IMapViewer viewer)
    {
        mapViewer = viewer;
    }

    /*
        Returns boolean value of whether a vehicle can pass the next junction
     */
    public boolean checkJunctionPass(Vehicle vehicle)
    {
        //getting next junction
        Junction incomingJunction = vehicle.getIncomingJunction();
        Road currentRoad = vehicle.getCurrentRoad();
        //getting distance to the junction
        double distance = vehicle.getIncomingJunctionDistance();
        //stopping the vehicle if there is passage is blocked ("red light") and the vehicle is close to the junction
        if(distance <= mapViewer.getJunctionSize()) {
            if ((currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL
                    && incomingJunction.getPassage() == Junction.Passage.PASSAGE_VERTICAL)
                    || (currentRoad.getOrientation() == Road.RoadOrientation.RO_VERTICAL
                    && incomingJunction.getPassage() == Junction.Passage.PASSAGE_HORIZONTAL))
                return false;
            if(vehicle instanceof Car && ((Car)vehicle).getIntention() == Car.Intention.LEFT)
            {
                return false;
            }
        }
        return true;
    }

    /*
        Performs a check for a vehicle if it is possible for it to move forward
     */
    public boolean checkVehicleCollision(Vehicle vehicle)
    {
        Road currentRoad = vehicle.getCurrentRoad();
        Vehicle.VehicleOrientation orientation = vehicle.getOrientation();
        //for each of the other cars
        for(Vehicle other : mapViewer.getMapSchema().getVehicles())
        {
            if(other != vehicle) //collisions must not be checked for the same vehicle
            {
                if(other.getCurrentRoad() == currentRoad) //if vehicles are on the same road
                {
                    if(other.getOrientation() == orientation) //if they are going in the same direction
                    {
                        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
                        {
                            if(orientation == Vehicle.VehicleOrientation.VO_WEST &&
                                    vehicle.getPosX() - other.getPosX() < mapViewer.getVehicleSize(other)
                                    && vehicle.getPosX() - other.getPosX() > 0)
                            {
                                return false;
                            }
                            else if(orientation == Vehicle.VehicleOrientation.VO_EAST &&
                                    other.getPosX() - vehicle.getPosX() < mapViewer.getVehicleSize(other)
                                    && other.getPosX() - vehicle.getPosX() > 0)
                            {
                                return false;
                            }
                        }
                        else //for vertical roads
                        {
                            if(orientation == Vehicle.VehicleOrientation.VO_NORTH &&
                                    vehicle.getPosY() - other.getPosY() < mapViewer.getVehicleSize(other)
                                    && vehicle.getPosY() - other.getPosY() > 0)
                            {
                                return false;
                            }
                            else if(orientation == Vehicle.VehicleOrientation.VO_SOUTH &&
                                    other.getPosY() - vehicle.getPosY() < mapViewer.getVehicleSize(other)
                                    && other.getPosY() - vehicle.getPosY() > 0)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private IMapViewer mapViewer;
}
