import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;

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
        simulationEngine = new SimulationEngine(WholeMapViewer.class, SimpleRoadGenerator.class, CollisionEngine.class);

        //create root node
        Group root = new Group();
        //add viewer's canvas to the root node
        root.getChildren().add(simulationEngine.getSimulationView());
        //create scene and add it to the stage
        simScene = new SimScene(root);
        primaryStage.setScene(simScene);

        simulationEngine.run();

        primaryStage.show();
    }

    private SimScene simScene;
    private ISimulationEngine simulationEngine;
}
