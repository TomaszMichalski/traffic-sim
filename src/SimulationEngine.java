import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

/*
    Basic implementation of ISimulationEngine
 */

public class SimulationEngine implements ISimulationEngine
{
    public SimulationEngine(Class mapViewer, Class mapGenerator, Class collisionEngine)
    {
        try
        {
            this.mapGenerator = (IMapGenerator)mapGenerator.getConstructor().newInstance();
            this.mapViewer = (IMapViewer)mapViewer.getConstructor(MapSchema.class).newInstance(this.mapGenerator.generate());
            this.collisionEngine = (ICollisionEngine)collisionEngine.getConstructor(IMapViewer.class).newInstance(this.mapViewer);
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
    }
    /*
        Sets the application canvas for the simulation engine's map viewer drawing purposes
     */
    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    /*
        Launches the simulation, creating an Animation Timer which performs every important simulation actions
        and checks during the frame
     */
    public void run()
    {
        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                moveVehicles();
                checkCollisions();
                mapViewer.show(canvas);
                changeTrafficLights(now);
            }
        }.start();
    }

    /*
        Changes junctions' traffic light if the actual light change delay is greater or equal to junction's traffic light delay
     */
    private void changeTrafficLights(long now)
    {
        for(Junction junction : mapViewer.getMapSchema().getJunctions())
        {
            if(now - junction.getPreviousPassageChangeTime() >= junction.getTrafficLightDelay())
            {
                junction.changePassage(now);
            }
        }
    }

    private long past;

    /*
        Checks vehicle collisions and halts the vehicles if needed
     */
    private void checkCollisions()
    {
        for(Vehicle vehicle : mapViewer.getMapSchema().getVehicles())
        {
            vehicle.halt(!collisionEngine.checkJunctionPass(vehicle) || !collisionEngine.checkVehicleCollision(vehicle));
        }
    }

    /*
        Moves every vehicle
     */
    private void moveVehicles()
    {
        for(Vehicle vehicle : mapViewer.getMapSchema().getVehicles())
        {
            vehicle.move();
        }
    }

    private IMapViewer mapViewer;
    private IMapGenerator mapGenerator;
    private ICollisionEngine collisionEngine;
    private Canvas canvas;
}
