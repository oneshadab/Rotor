package mygame;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.List;

public class VisionProjectile extends Projectile{
    Triggerable tri;
    public VisionProjectile(Triggerable tri, Game game, Vector3f pos, Quaternion rot, Vector3f dir){
        super(game, pos, rot, dir, false);
        this.tri = tri;
    }
    
    public void addUpdate(){
        new Thread(){
            public void run(){
                update();
            }
        }.start();
    }
    
    public void checkOverlap(){
        List<PhysicsCollisionObject> AL = ghostControl.getOverlappingObjects();
        for(Object a: AL){
            if(a instanceof ObjectGhostControl && ((ObjectGhostControl)a).getObject() instanceof Player){
                tri.trigger();
                this.destroy();
                break;
            }  
        }
    }
}