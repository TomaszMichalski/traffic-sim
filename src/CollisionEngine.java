public class CollisionEngine implements ICollisionEngine
{
    public CollisionEngine(IMapViewer viewer)
    {
        mapViewer = viewer;
    }

    public void run()
    {
        while(true)
        {
            for(IVehicle vehicle : mapViewer.getMapSchema().getVehicles())
            {
                if(!checkVehicleCollision(vehicle) || !checkJunctionPass(vehicle))
                    vehicle.halt(true);
                else vehicle.halt(false);
            }
        }
    }

    public boolean checkJunctionPass(IVehicle vehicle)
    {
        //getting next junction
        Junction incomingJunction = vehicle.getIncomingJunction();
        Road currentRoad = vehicle.getCurrentRoad();
        //getting distance to the junction
        double distance;
        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
            distance = Math.abs(incomingJunction.getPosX() - vehicle.getPosX());
        else //for vertical roads
            distance = Math.abs(incomingJunction.getPosY() - vehicle.getPosY());
        //stopping the car if there is passage is blocked ("red light")
        if(distance < mapViewer.getJunctionSize()) {
            if ((currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL
                    && incomingJunction.getPassage() == Junction.Passage.PASSAGE_VERTICAL)
                    || (currentRoad.getOrientation() == Road.RoadOrientation.RO_VERTICAL
                    && incomingJunction.getPassage() == Junction.Passage.PASSAGE_HORIZONTAL))
                return false;
        }
        return true;
    }

    public boolean checkVehicleCollision(IVehicle vehicle)
    {
        Road currentRoad = vehicle.getCurrentRoad();
        IVehicle.VehicleOrientation orientation = vehicle.getOrientation();
        //for each of the other cars
        for(IVehicle other : mapViewer.getMapSchema().getVehicles())
        {
            if(other != vehicle) //collisions must not be checked for the same vehicle
            {
                if(other.getCurrentRoad() == currentRoad) //if vehicles are on the same road
                {
                    if(other.getOrientation() == orientation) //if they are going in the same direction
                    {
                        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
                        {
                            if(orientation == IVehicle.VehicleOrientation.VO_WEST &&
                                    vehicle.getPosX() - other.getPosX() < mapViewer.getVehicleSize(other)
                                    && vehicle.getPosX() - other.getPosX() > 0)
                            {
                                return false;
                            }
                            else if(orientation == IVehicle.VehicleOrientation.VO_EAST &&
                                    other.getPosX() - vehicle.getPosX() < mapViewer.getVehicleSize(other)
                                    && other.getPosX() - vehicle.getPosX() > 0)
                            {
                                return false;
                            }
                        }
                        else //for vertical roads
                        {
                            if(orientation == IVehicle.VehicleOrientation.VO_NORTH &&
                                    vehicle.getPosY() - other.getPosY() < mapViewer.getVehicleSize(other)
                                    && vehicle.getPosY() - other.getPosY() > 0)
                            {
                                return false;
                            }
                            else if(orientation == IVehicle.VehicleOrientation.VO_SOUTH &&
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
