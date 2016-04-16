package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;

public class Projectile implements Updatable{
    int remainingFrames = 500;
    Game game;
    Node rootNode;
    BulletAppState bAppState;
    Spatial shape;
    GhostControl ghostControl;
    
    Vector3f pos;
    Vector3f dir;
    Quaternion rot;
    boolean visible;
    
    public Projectile(Game game, Vector3f pos, Quaternion rot , Vector3f dir, boolean visible){
        this.pos = pos;
        this.rot = rot;
        this.dir = dir.multLocal(.1f);
        this.visible = visible;
        
        this.game = game;
        
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        this.shape = new Geometry("Projectile", new Box(1, 1, 5));
        
        Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        shape.setMaterial(mat);
        
        shape.setLocalScale(.05f);
        ghostControl = new GhostControl(CollisionShapeFactory.createBoxShape(shape));
        shape.addControl(ghostControl);
        
        if(visible) rootNode.attachChild(shape);
        bAppState.getPhysicsSpace().add(ghostControl);
        
        setupPosition();
        addUpdate();
    }
    
    public Projectile(Game game, Vector3f pos, Quaternion rot , Vector3f dir){
        this(game, pos, rot, dir, true);
    }
    
    public void setupPosition(){
        Vector3f vectorDifference = new Vector3f(pos.subtract(shape.getWorldTranslation()));
        shape.setLocalTranslation(vectorDifference.addLocal(shape.getLocalTranslation()));

        Quaternion worldDiff = new Quaternion(rot.mult(shape.getWorldRotation().inverse()));
        worldDiff.multLocal(shape.getLocalRotation());
        shape.setLocalRotation(worldDiff);
        shape.move(dir.mult(100));
    }
    
    public void addUpdate(){
        game.addUpdateList(this);
    }
    
    public void update(){
        if(ghostControl == null) return;
        if(remainingFrames <= 0) this.destroy(); 
        remainingFrames -= 1;
        for(int i = 0; i < 50; i++){
            if(ghostControl == null) return;
            updatePosition();
            checkOverlap();
        }
        
    }
    
    public void updatePosition(){
        
        shape.move(dir);
        
        //System.out.println(++x + " " + shape.getLocalTranslation()); 
    }
    
    public void checkOverlap(){
        List<PhysicsCollisionObject> AL = ghostControl.getOverlappingObjects();
        for(Object a: AL){
            if(a instanceof TestGhostControl && ((TestGhostControl)a).getTestObject() instanceof Triggerable){
                Triggerable t = (Triggerable)(((TestGhostControl)a).getTestObject());
                t.trigger();
                this.destroy();
                break;
            }  
        }
    }
    
    public void destroy(){
        game.getRootNode().detachChild(shape);
        game.getBulletAppState().getPhysicsSpace().remove(ghostControl);
        ghostControl = null;
    }
}