package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Crate extends TestObject implements Triggerable, Updatable{
    int hitPoints;
    public Crate(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
        super(testObject, pos, dir, game);
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