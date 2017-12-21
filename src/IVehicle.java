import javafx.scene.shape.*;

public interface IVehicle extends Runnable
{
    public double getPosX();
    public double getPosY();

    public enum VehicleOrientation
    {
        VO_NORTH,
        VO_EAST,
        VO_SOUTH,
        VO_WEST
    }

    public VehicleOrientation getOrientation();
    public void setOrientation(VehicleOrientation newOrientation);

    /*
        Returns current road the vehicle is on
     */
    public Road getCurrentRoad();
    public Junction getIncomingJunction();
    public void halt(boolean halt);
}
