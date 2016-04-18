/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapText;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;

public class ScoreBoard implements Updatable{
    Game game;
    static int points = 0;
    static int health = 100;
    BitmapText bmt;
    BitmapText bmtHealth;
    Camera cam;
    Node node;
    
    public ScoreBoard(Game game){
        this.game = game;
        this.cam = game.getCamera();
        
        bmt = new BitmapText(game.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        bmt.setQueueBucket(RenderQueue.Bucket.Inherit);
        bmt.setSize(25);
            
        bmtHealth = new BitmapText(game.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        bmtHealth.setQueueBucket(RenderQueue.Bucket.Inherit);
        bmtHealth.setSize(25);
        game.getGuiNode().attachChild(bmt);
        game.getGuiNode().attachChild(bmtHealth);
        addUpdate();
    }
    
    public ScoreBoard(int health, int points, Game game){
        this(game);
        this.points = points;
        this.health = health;
    }
    
    
    public void updateScore(int extra){
        points = points + extra;
    }
    
    public void addUpdate(){
        game.addUpdateList(this);
    }
    
    public void update(){
        //updatePosition();
        health = game.getCurrentLevel().getPlayer().health;
        bmt.setText("Points: " + points);
        bmtHealth.setText("Health: " + health);
            
        int screenWidth = game.getContext().getSettings().getWidth();
        int screenHeight = game.getContext().getSettings().getHeight();
        
        bmt.setLocalTranslation(new Vector3f(screenWidth - bmt.getLineWidth() - 5, screenHeight - 5, 0));
        bmtHealth.setLocalTranslation(new Vector3f(5, screenHeight - 5, 0));

    }
    
    public void updatePosition(){
        Vector3f vectorDifference = new Vector3f(cam.getLocation().subtract(node.getWorldTranslation()));
        node.setLocalTranslation(vectorDifference.addLocal(node.getLocalTranslation()));

        Quaternion worldDiff = new Quaternion(cam.getRotation().mult(node.getWorldRotation().inverse()));
        worldDiff.multLocal(node.getLocalRotation());
        node.setLocalRotation(worldDiff);

        
    }
}