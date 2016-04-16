package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.*;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.*;import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.List;
;

/**
 * test
 * @author Shadab
 */
public class Game extends SimpleApplication{
    ArrayList<Updatable> updateList;
    ArrayList<Level> levelList;
    BulletAppState bAppState;
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;
    Level currentLevel;
    boolean changeLevel;
    Node visibleNode;
    
    public void simpleInitApp() {  
        updateList = new ArrayList<Updatable>();
        visibleNode = new Node();
        rootNode.attachChild(visibleNode);
        
        currentLevel = new TutorialLevel(this);
    }
     
    public Node getVisibleNode(){
        return visibleNode;
    }
    
    public Level getCurrentLevel(){
        return currentLevel;
    }
    
    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }
   

    public void setupLight(){
        AmbientLight amb = new AmbientLight();
        amb.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(amb);
        
        
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
        
        
    }
    

    public void simpleUpdate(float tpf){
        for(Updatable up: updateList){
            up.update();
        }
        if(changeLevel){
            currentLevel.nextLevel();
            changeLevel = false;
        }
    }

    
    public void setChangeLevel(boolean changeLevel){
        this.changeLevel = changeLevel;
    }
    
    public void setBulletAppState(BulletAppState bAppState){
        this.bAppState = bAppState;
    }
    
    public BulletAppState getBulletAppState(){
        return bAppState;
    }
    
    public void addUpdateList(Updatable a){
        updateList.add(a);
    }
    
    
    public ScoreBoard getScoreBoard(){
        return scoreBoard;
    }

}
