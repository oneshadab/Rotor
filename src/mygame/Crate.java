package mygame;

import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Crate extends TestObject implements Triggerable, Updatable{
    int hitPoints;
    public Crate(Vector3f pos, Quaternion dir, final Game game){
        super(new Geometry("Box", new Box(10, 10, 10)){
                 public Geometry init(){
                     this.setMaterial(new Material(game.getAssetManager(), "/Common/MatDefs/Misc/Unshaded.j3md"));
                     this.getMaterial().setTexture("ColorMap", game.getAssetManager().loadTexture("Textures/crateTex1.png"));
                     return this;
                 }
              }.init(),
                pos, dir, game);
        this.hitPoints = 100;
    }
    
    public void trigger(){
        hitPoints -= 30;
        System.out.println("I was Shot!! => " + hitPoints);
        if(hitPoints < 0){
            System.out.println("I was Killed");
            this.destroy();
            this.game.getScoreBoard().updateScore(10);
        }
    }

    public void addUpdate() {
        
    }

    public void update() {
    }
    
    public void destroy(){
        disablePhysics();
        rootNode.detachChild(testObject);
    }
}