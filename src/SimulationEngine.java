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

    public Canvas getSimulationView()
    {
        return mapViewer.show();
    }

    public void run()
    {
        for (IVehicle vehicle : mapViewer.getMapSchema().getVehicles()) {
            new Thread(vehicle).start();
        }
        new Thread(collisionEngine).start();
    }

    private IMapViewer mapViewer;
    private IMapGenerator mapGenerator;
    private ICollisionEngine collisionEngine;
}
