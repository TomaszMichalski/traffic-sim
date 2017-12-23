import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

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

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

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
            }
        }.start();
    }

    private void checkCollisions()
    {
        for(Vehicle vehicle : mapViewer.getMapSchema().getVehicles())
        {
            vehicle.halt(!collisionEngine.checkJunctionPass(vehicle) || !collisionEngine.checkVehicleCollision(vehicle));
        }
    }

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
