package po.trafficsim;

import javafx.application.*;
import javafx.scene.canvas.Canvas;
import javafx.stage.*;
import javafx.scene.*;
import po.trafficsim.simulation.*;
import po.trafficsim.viewer.*;
import po.trafficsim.generator.*;
import po.trafficsim.collision.*;

public class TrafficSim extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        //set basic properties
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Traffic Sim");

        //set implementation of simulation engine
        simulationEngine = new SimulationEngine(WholeMapViewer.class, MapGenerator.class, CollisionEngine.class);

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
}
