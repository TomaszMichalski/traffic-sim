package po.trafficsim;

import javafx.application.*;
import javafx.scene.canvas.Canvas;
import javafx.stage.*;
import javafx.scene.*;
import po.trafficsim.simulation.*;
import po.trafficsim.ioc.Provider;

public class TrafficSim extends Application
{
    public static void main(String[] args)
    {
        //launching javafx app
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        //set basic properties
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Traffic Sim");

        simulationEngine = new SimulationEngine(Provider.getMapViewerInstance(), Provider.getMapGeneratorInstance(), Provider.getCollisionEngineInstance());

        //create root node
        Group root = new Group();
        //create canvas to draw simulation view on
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
        simulationEngine.setCanvas(canvas);
        //create scene and add it to the stage
        simScene = new SimScene(root);
        primaryStage.setScene(simScene);

        primaryStage.show();
    }

    private SimScene simScene;
    private ISimulationEngine simulationEngine;
}
