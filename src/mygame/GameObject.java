/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Shadab
 */
public class GameObject{
    Game game;
    Spatial shape;
    float shapeWeight;
    RigidBodyControl objectControl;
    ObjectGhostControl ghostControl;
    Node rootNode;
    BulletAppState bAppState;
    CollisionShape testShape;
    
    boolean left, right, up, down;
    
    
    
    public GameObject(Spatial testObject, Vector3f pos, Quaternion dir, Game game, float localScale){
        this.game = game;
        this.shape = testObject;
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        this.shapeWeight = 0.3f;
        
        testObject.setLocalScale(localScale);
        setupAll();
        if(testObject instanceof Geometry && ((Geometry)testObject).getMaterial() == null){
            ((Geometry)testObject).setMaterial(new Material(game.getAssetManager(), "/Common/MatDefs/Misc/Unshaded.j3md"));
        }
        if(pos != null) objectControl.setPhysicsLocation(pos);
        if(dir != null) objectControl.setPhysicsRotation(dir);
        
    }
    
    public GameObject(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
        this(testObject, pos, dir, game, .2f);
    }
    
    public void setupControls(){
        objectControl = new RigidBodyControl(testShape, shapeWeight);
        shape.addControl(objectControl);
        
        
        ghostControl = new ObjectGhostControl(testShape, this);
        shape.addControl(ghostControl);
        

    }
    
    
    
    
    public void setupAll(){
        testShape = CollisionShapeFactory.createBoxShape(shape);
        this.setupControls();
        this.enablePhysics();
        rootNode.attachChild(shape);

    }
    
    
    public Spatial getSpatial(){
        return shape;
    }
    
    public void disablePhysics(){
        bAppState.getPhysicsSpace().remove(objectControl);
        bAppState.getPhysicsSpace().remove(ghostControl);
    }
    
    public void enablePhysics(){
        bAppState.getPhysicsSpace().add(ghostControl);
        bAppState.getPhysicsSpace().add(objectControl);
    }
    
}
