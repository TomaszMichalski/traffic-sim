package po.trafficsim;

import javafx.application.*;
import javafx.scene.canvas.Canvas;
import javafx.stage.*;
import javafx.scene.*;
import po.trafficsim.simulation.*;
import po.trafficsim.ioc.*;
import java.lang.reflect.Constructor;

public class TrafficSim extends Application
{
    public static void main(String[] args)
    {
        //setting provider class
        Object providerObject = null;
        if(args.length == 0)
        {
            System.err.println("Invalid argument (should be <providerClassName>.class)");
            System.exit(1);
        }
        else
        {
            try
            {
                Class providerClass = Class.forName(args[0]);
                Constructor defaultConstructor = providerClass.getConstructor();
                providerObject = defaultConstructor.newInstance();
            }
            catch(ClassNotFoundException e)
            {
                System.err.println("Provider class not found");
                System.exit(2);
            }
            catch(NoSuchMethodException e)
            {
                System.err.println("No default constructor found");
                System.exit(3);
            }
            catch(ReflectiveOperationException e)
            {
                System.err.println("Reflective operation fail");
                System.exit(4);
            }
            if(providerObject instanceof IProvider)
            {
                provider = (IProvider)providerObject;
            }
            else
            {
                System.err.println("Class does not implement IProvider interface");
                System.exit(5);
            }
        }
        //launching javafx app
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        //set basic properties
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Traffic Sim");

        provider.init();
        simulationEngine = new SimulationEngine(provider.getMapViewerInstance(), provider.getMapGeneratorInstance(), provider.getCollisionEngineInstance());

        //create root node
        Group root = new Group();
        //create canvas to draw simulation view on
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
        simulationEngine.setCanvas(canvas);
        //create scene and add it to the stage
        simScene = new SimScene(root);
        primaryStage.setScene(simScene);

        simulationEngine.run();

        primaryStage.show();
    }

    private SimScene simScene;
    private ISimulationEngine simulationEngine;
    private static IProvider provider;
}
