/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Murilllo Henrique
 */
public class Play extends Application {
    
    private Label titulo;
    static Label score, over;
	@Override
	public void start(Stage stage) throws Exception {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            
            VBox pane = new VBox(10);
            Scene scene = new Scene(new StackPane(pane));
            scene.setFill(Color.DARKSLATEGREY);
            
            titulo = new Label("Jogo da Cobra - Snake");
            score = new Label();
            over = new Label("Fim de Jogo!\nClick aqui para reiniciar...");
            over.setDisable(true);
            over.setVisible(false);
            
            Rectangle region = new Rectangle(d.getWidth()-200, d.getHeight()-200);
            region.setStrokeLineCap(StrokeLineCap.ROUND);
            region.setStroke(Color.WHITE);
            
            Snake go = new Snake();
            go.begin(region.getWidth(), region.getHeight(), region.getX(), region.getY());
            
            titulo.setFont(Font.font(30));
            titulo.setEffect(new DropShadow(20, Color.RED));
            score.setFont(Font.font(STYLESHEET_MODENA, FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 25));
            score.setTextFill(Color.BLUE);
            over.setTextAlignment(TextAlignment.CENTER);
            over.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 35));
            over.setEffect(new InnerShadow(10, Color.DARKRED));
            
            pane.getChildren().addAll(titulo, new StackPane(new StackPane(region, go, over)), score);
            pane.setAlignment(Pos.CENTER);
                
            scene.setOnKeyReleased((KeyEvent evt) -> {
                    go.teclado(evt);
            });
             
            stage.setTitle("Snake");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();	
	}

    public static void main(String[] args) {
        launch(args);
    }
}
