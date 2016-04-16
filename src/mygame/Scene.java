package mygame;

import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Shadab
 */
public class Scene {
    Spatial sceneModel;
    RigidBodyControl sceneControl;
    Node rootNode;
    BulletAppState bAppState;
    CollisionShape sceneShape;

    public void setupControls(){
        sceneControl = new RigidBodyControl(sceneShape, 0);
        sceneModel.addControl(sceneControl);
        bAppState.getPhysicsSpace().add(sceneControl);
    }
    
    public Scene(Spatial sceneModel, Game game){
        this.sceneModel = sceneModel;
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        
        Node t;
        
        sceneModel.setLocalScale(2f);
        sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
        this.setupControls();
        
        game.getVisibleNode().attachChild(sceneModel);
    }
}
