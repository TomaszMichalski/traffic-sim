public interface ICollisionEngine extends Runnable
{
    public boolean checkVehicleCollision(Vehicle vehicle);

    public boolean checkJunctionPass(Vehicle vehicle);
}
