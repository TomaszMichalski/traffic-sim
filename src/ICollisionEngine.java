public interface ICollisionEngine extends Runnable
{
    public boolean checkVehicleCollision(IVehicle vehicle);

    public boolean checkJunctionPass(IVehicle vehicle);
}
