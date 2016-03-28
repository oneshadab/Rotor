package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Crate extends TestObject implements Triggerable, Updatable{
    int hp;
    public Crate(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
        super(testObject, pos, dir, game);
        this.hp = 100;
    }
    public void trigger(){
        hp -= 30;
        System.out.println("I was Shot!! => " + hp);
        if(hp < 0){
            System.out.println("I was Killed");
            this.destroy();
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