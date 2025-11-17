package co.edu.uniquindio.empresalogistica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            primaryStage = stage;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/empresalogistica/fxml/Login.fxml"));

            if (fxmlLoader.getLocation() == null) {
                System.err.println("No se encontró el archivo FXML");
                System.exit(1);
            }

            Scene scene = new Scene(fxmlLoader.load(), 400, 600);

            stage.setTitle("Logística Express - Login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el FXML: " + e.getMessage());
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Método auxiliar para cambiar de escena
     */
    public static void changeScene(String fxmlPath, String title, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Scene newScene = new Scene(loader.load(), width, height);
            primaryStage.setScene(newScene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cambiar de escena: " + e.getMessage());
        }
    }

    public static void changeSceneResizable(String fxmlPath, String title, double width, double height, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Scene newScene = new Scene(loader.load(), width, height);
            primaryStage.setScene(newScene);
            primaryStage.setTitle(title);
            primaryStage.setResizable(resizable);
            if (resizable) {
                primaryStage.setMaximized(true);
            }
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cambiar de escena: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}