public interface ICollisionEngine
{
    /*
        Performs a check for vehicle given as a parameter if it can move without colliding with another entity
        Returns true if the move can be performed
        Returns false if the move cannot be performed
     */
    public boolean checkVehicleCollision(Vehicle vehicle);

    /*
        Performs a check for vehicle given as a parameter if it can pass the next junction
        Returns true if the pass can be performed or the vehicle is still in the safe distance to the junction
        Returns false if the pass cannot be performed and the vehicle is close to the junction
     */
    public boolean checkJunctionPass(Vehicle vehicle);
}
