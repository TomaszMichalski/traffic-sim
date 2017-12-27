import javafx.scene.canvas.Canvas;

public interface ISimulationEngine
{
    /*
        Sets the application canvas for the simulation engine's map viewer drawing purposes
     */
    public void setCanvas(Canvas canvas);
    /*
        Launches the simulation
     */
    public void run();
}
