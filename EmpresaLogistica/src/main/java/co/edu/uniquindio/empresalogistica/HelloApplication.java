package co.edu.uniquindio.empresalogistica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/empresalogistica/fxml/Login.fxml"));

            if (fxmlLoader.getLocation() == null) {
                System.err.println("No se encontró el archivo FXML");
                System.exit(1);
            }

            Scene scene = new Scene(fxmlLoader.load(), 400, 600);

            stage.setTitle("Logística Express - Login");
            stage.setScene(scene);
            stage.setResizable(false); // No permitir cambiar tamaño en login
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el FXML: " + e.getMessage());
        }
    }





    public static void main(String[] args) {
        launch();
    }
}