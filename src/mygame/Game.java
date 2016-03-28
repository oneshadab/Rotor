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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.*;import java.util.ArrayList;
import java.util.List;
;

/**
 * test
 * @author Shadab
 */
public class Game extends SimpleApplication{
    ArrayList<Updatable> updateList;
    BulletAppState bAppState;
    Player player;
    Scene scene;
 
     @Override
    public void simpleInitApp() {  
        
        setupPhysics();
        setupCamera();
        setupLight();
        updateList = new ArrayList<Updatable>();
        
        Weapon revolver = new Weapon(assetManager.loadModel("/Models/wade_high_rez_colt/wade_high_rez_colt.j3o"), 
                                    new Vector3f(10f, 3f, 3f), null, this);
        Crate b = new Crate(new Geometry("Box", new Box(10, 10, 10)), 
                                    new Vector3f(-10f, 3f, 3f), null, this);
        player = new Player(new CapsuleCollisionShape(1.5f, 6f, 1), this);
        scene = new Scene(assetManager.loadModel("/Scenes/town/main.scene"), this);
        
        player.setInputMapping(inputManager, player);
    }
    
    public void setupCamera(){
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        flyCam.setMoveSpeed(100);
    }
    
    public void setupPhysics(){
        bAppState = new BulletAppState();
        stateManager.attach(bAppState);
        //bAppState.getPhysicsSpace().enableDebug(assetManager);
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
    }
    
    public BulletAppState getBulletAppState(){
        return bAppState;
    }
    
    public void addUpdateList(Updatable a){
        updateList.add(a);
    }

}
