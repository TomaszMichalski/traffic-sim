package po.trafficsim.simulation;

import javafx.scene.canvas.Canvas;

public interface ISimulationEngine
{
    /*
        Sets the application canvas for the simulation engine's map viewer drawing purposes
     */
    void setCanvas(Canvas canvas);
    /*
        Launches the simulation
     */
    void run();
}
