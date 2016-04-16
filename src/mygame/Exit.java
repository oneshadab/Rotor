package mygame;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.List;

public class Exit extends Crate{
    public Exit(Vector3f pos, Quaternion dir, Game game){
        super(pos, dir, game);
        addUpdate();
    }
    
    public void addUpdate(){
        game.addUpdateList(this);
    }
    
    public void update(){
        checkOverlap();
    }
    

    public void checkOverlap(){
        List<PhysicsCollisionObject> AL = ghostControl.getOverlappingObjects();
        boolean changeLevel = false;
        for(Object a: AL){
            if(a instanceof ObjectGhostControl && ((ObjectGhostControl)a).getObject() instanceof Player){
                game.setChangeLevel(true);
            }  
        }
    }
}