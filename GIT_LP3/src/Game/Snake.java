/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 teste*/
package Game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author Murilllo Henrique
 */
public class Snake extends Pane{
    public static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4, space = 11;
    private double x, y, w, h;  private int direcao=0, pt=0, rate;
    private boolean pause, over;
    private ArrayList<Rectangle> root;
    private Rectangle bloco, c;
    Timeline loop;
    
    public void begin(double w, double h, double x, double y){
        this.w = w; this.h = h; this.x = x; this.y = y; Play.score.setText("Pontos: "+pt);
        root = new ArrayList<>(); pause = false; over = false; rate=2; getChildren().clear();
        
        for(int i=1; i<space;i++){//x = x+space do bloco anterior
            bloco = new Rectangle(x+space, y+space, 10, 10);
            bloco.setFill(Color.rgb(128,128,0));
            x=bloco.getX();
            root.add(bloco);
        }
        getChildren().addAll(root);
        bloco = root.get(root.size()-1);
        loop = new Timeline();
        loop.setCycleCount( Timeline.INDEFINITE );
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.09),(ActionEvent ae) -> {
            switch(direcao){
                case UP:
                    if(!root.isEmpty() && bloco.getY()>this.y && colisao(bloco)){
                        bloco = new Rectangle(bloco.getX(), bloco.getY()-space, 10, 10);
                        bloco.setFill(Color.rgb(128,128,0));
                        root.add(bloco);
                        Cereja();
                    }
                    else over();
                    break;
                case DOWN:
                    if(!root.isEmpty() && bloco.getY()<h-space && colisao(bloco)){
                        bloco = new Rectangle(bloco.getX(), bloco.getY()+space, 10, 10);
                        bloco.setFill(Color.rgb(128,128,0));
                        root.add(bloco);
                        Cereja();
                    }
                    else over();
                    break;
                case RIGHT:
                    if(!root.isEmpty() && bloco.getX()<w+space && colisao(bloco)){
                        bloco = new Rectangle(bloco.getX()+space, bloco.getY(), 10, 10);
                        bloco.setFill(Color.rgb(128,128,0));
                        root.add(bloco);
                        Cereja();
                    }
                    else over();
                    break;
                case LEFT:
                    if(!root.isEmpty() && bloco.getX()-space>this.x && colisao(bloco)){
                        bloco = new Rectangle(bloco.getX()- space, bloco.getY(), 10, 10);
                        bloco.setFill(Color.rgb(128,128,0));
                        root.add(bloco);
                        Cereja();
                    }
                    else over();
                    break;
            }
        });
        loop.getKeyFrames().add( kf );
        reproducao(loop); 
    }
    
    private boolean colisao(Rectangle bloco){
        return root.stream().anyMatch((r) -> ((r.getX()==bloco.getX()&&(r.getY()==bloco.getY()))));
    }
    
    private double X(){
        Random r = new Random();
        return r.nextInt((int)w+1);
    }
    private double Y(){
        Random r = new Random();
        return r.nextInt((int)h+1);
    }
    
    private void Cereja(){
            getChildren().clear();
            getChildren().addAll(root);
        if(c == null){
            c = new Rectangle(X(), Y(), 8, 8);
            c.setFill(Color.RED);
        }
        else{
            if(bloco.intersects(c.getX(), c.getY(), c.getWidth(), c.getHeight())){
                c.setX(X());  c.setY(Y());  pt=pt+10;
                Play.score.setText("Pontos: "+pt);  System.out.println("Pontos: "+pt+"\n");
                loop.setRate((0.5)*rate++);
            }
            else root.remove(0);
        }
        getChildren().add(c);
    }
    
    private Timeline reproducao(Timeline loop){
        if(!pause && !over) loop.play();
        else if(pause) loop.pause();
        else if(over) loop.stop();
        return loop;
    }
    
    private void over(){
        over = true;
        reproducao(loop);
        Play.over.setDisable(false);
        Play.over.setVisible(true);
        FadeTransition GAMEover = new FadeTransition(Duration.millis(500), Play.over);
        GAMEover.setFromValue(0.1);
        GAMEover.setToValue(1);
        GAMEover.setCycleCount(-1);
        GAMEover.setCycleCount(-1);
        GAMEover.setAutoReverse(true);
        GAMEover.play();
        Play.over.setOnMouseClicked((MouseEvent event) -> {
             direcao=0; pt=0;
             Play.over.setDisable(true);
             Play.over.setVisible(false);
             begin(w,h,x,y);   
        });
    }
    
    public void teclado(KeyEvent evt){
        if ((evt.getCode()==KeyCode.A || evt.getCode()==KeyCode.LEFT )&& direcao != RIGHT) direcao= LEFT;
        if ((evt.getCode()==KeyCode.D || evt.getCode()==KeyCode.RIGHT )&& direcao != LEFT) direcao= RIGHT;
        if ((evt.getCode()==KeyCode.W || evt.getCode()==KeyCode.UP )&& direcao != DOWN) direcao= UP;
        if ((evt.getCode()==KeyCode.S || evt.getCode()==KeyCode.DOWN )&& direcao != UP) direcao= DOWN;
        if ((evt.getCode()==KeyCode.SPACE || evt.getCode()==KeyCode.ESCAPE )){
            pause = !pause;
            if(direcao!=0) reproducao(loop);
        }
    }
}
