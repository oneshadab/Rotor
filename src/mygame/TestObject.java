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
public class TestObject{
    Game game;
    Spatial testObject;
    float testWeight;
    RigidBodyControl testControl;
    TestGhostControl ghostControl;
    Node rootNode;
    BulletAppState bAppState;
    CollisionShape testShape;
    
    boolean left, right, up, down;
    
    
    
    public TestObject(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
        this.game = game;
        this.testObject = testObject;
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        this.testWeight = 0.3f;
        
        testObject.setLocalScale(.2f);
        setupAll();
        if(testObject instanceof Geometry && ((Geometry)testObject).getMaterial() == null){
            ((Geometry)testObject).setMaterial(new Material(game.getAssetManager(), "/Common/MatDefs/Misc/Unshaded.j3md"));
        }
        if(pos != null) testControl.setPhysicsLocation(pos);
        if(dir != null) testControl.setPhysicsRotation(dir);
        
    }
    
    public void setupControls(){
        testControl = new RigidBodyControl(testShape, testWeight);
        testObject.addControl(testControl);
        
        
        ghostControl = new TestGhostControl(testShape, this);
        testObject.addControl(ghostControl);
        

    }
    
    
    
    
    public void setupAll(){
        testShape = CollisionShapeFactory.createBoxShape(testObject);
        this.setupControls();
        this.enablePhysics();
        rootNode.attachChild(testObject);

    }
    
    
    public Spatial getSpatial(){
        return testObject;
    }
    
    public void disablePhysics(){
        bAppState.getPhysicsSpace().remove(testControl);
        bAppState.getPhysicsSpace().remove(ghostControl);
    }
    
    public void enablePhysics(){
        bAppState.getPhysicsSpace().add(ghostControl);
        bAppState.getPhysicsSpace().add(testControl);
    }
    
}
