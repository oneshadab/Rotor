package mygame;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.List;

public class Exit extends TestObject implements Updatable{
    public Exit(Vector3f pos, Quaternion dir, final Game game){
        super(new Geometry("Box", new Box(2, 30, 15)){
                 public Geometry init(){
                     this.setMaterial(new Material(game.getAssetManager(), "/Common/MatDefs/Misc/Unshaded.j3md"));
                     this.getMaterial().setTexture("ColorMap", game.getAssetManager().loadTexture("Textures/doorTexture.JPG"));
                     return this;
                 }
              }.init(),
                pos, dir, game);
        disablePhysics();
        addUpdate();
        bAppState.getPhysicsSpace().add(ghostControl);
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