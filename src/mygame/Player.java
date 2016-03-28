/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Shadab
 */
public class Player implements ActionListener, Updatable{
    Game game;
    Node rootNode;
    Camera cam;
    BulletAppState bAppState;
    
    Spatial playerModel;
    float playerHeight;
    CharacterControl playerControl;
    GhostControl ghostControl;
    CapsuleCollisionShape playerShape;
    Spatial weapon;
  
    Weapon weaponObject;
    
    Vector3f camDir = new Vector3f();
    Vector3f walkDir = new Vector3f();
    Vector3f camLeft = new Vector3f();
    
    boolean left, right, up, down;
    

    public Player(CapsuleCollisionShape playerShape, Game game){
        this.game = game;
        this.playerShape = playerShape;
        
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        this.playerHeight = 0.05f;
        this.cam = game.getCamera();
        
        playerModel = new Geometry("Player", new Box(1, 1, 1));
        playerModel.setMaterial(new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"));
        rootNode.attachChild(playerModel);
        setupControls();
       
        enablePhysics();
        addUpdate();
        
    }
    
    public void enablePhysics(){
        bAppState.getPhysicsSpace().add(playerControl);
        bAppState.getPhysicsSpace().add(ghostControl);
        
    }
    
    public void setupControls(){
        
        playerControl = new CharacterControl(playerShape, playerHeight);
        playerModel.addControl(playerControl);
        playerControl.setJumpSpeed(15);
        playerControl.setGravity(30);
        playerControl.setFallSpeed(30);
        playerControl.setPhysicsLocation(new Vector3f(0, 10, 0));
        
        
        ghostControl = new GhostControl(playerShape);
        playerModel.addControl(ghostControl);
        
        
    }
    
    public void setInputMapping(InputManager inputManager, ActionListener al){
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Drop", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(al, "Left");
        inputManager.addListener(al, "Right");
        inputManager.addListener(al, "Up");
        inputManager.addListener(al, "Down");
        inputManager.addListener(al, "Jump");
        inputManager.addListener(al, "Drop");
        inputManager.addListener(al, "Shoot");
    }
    
    public void onAction(String bindings, boolean isPressed, float tpf) {
        if(bindings.equals("Left")){
            left = isPressed;
        }
        else if(bindings.equals("Right")){
            right = isPressed;
        }
        else if(bindings.equals("Up")){
            up = isPressed;
        }
        else if(bindings.equals("Down")){
            down = isPressed;
        }
        else if(bindings.equals("Jump")){
            if(isPressed) playerControl.jump();
        }
        else if(bindings.equals("Drop")){
            if(isPressed) this.toggleWeapon();
        }
        else if(bindings.equals("Shoot")){
            if(isPressed) this.fireWeapon();
        }
    }
    
    public void addUpdate(){
        game.addUpdateList(this);
    }
    
    public void update(){
        updatePosition();
        updateWeapon();
    }
    
    public void setWeapon(Spatial weapon){
        this.weapon = weapon;
    }
    
    public void updatePosition(){
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDir.set(0, 0, 0);
        if(left){
            walkDir.addLocal(camLeft);
        }
        if(right){
            walkDir.addLocal(camLeft.negate());
     
        }
        if(up){
            walkDir.addLocal(camDir);
        }
        if(down){
            walkDir.addLocal(camDir.negate());
        }
        walkDir.y = 0;
        playerControl.setWalkDirection(walkDir);

        cam.setLocation(playerControl.getPhysicsLocation());
    }
    
    public void updateWeapon(){
        if(weapon == null) return;
        Vector3f vectorDifference = new Vector3f(cam.getLocation().subtract(weapon.getWorldTranslation()));
        weapon.setLocalTranslation(vectorDifference.addLocal(weapon.getLocalTranslation()));

        Quaternion worldDiff = new Quaternion(cam.getRotation().mult(weapon.getWorldRotation().inverse()));
        worldDiff.multLocal(weapon.getLocalRotation());
        weapon.setLocalRotation(worldDiff);

        // Move it to the bottom right of the screen
        weapon.move(cam.getDirection().mult(3));
        weapon.move(cam.getUp().mult(-0.8f));
        weapon.move(cam.getLeft().mult(-1f));
        weapon.rotate(-0.3f, 0.15f, 0);
        
    }
    
    public void toggleWeapon(){
        if(weaponObject == null && ghostControl.getOverlappingCount() != 0){
            pickupWeapon();
        }
        else{
            dropWeapon();
        }
    }
    
    public void pickupWeapon(){
        for(Object a: ghostControl.getOverlappingObjects()){
            if(a instanceof TestGhostControl && ((TestGhostControl)a).getTestObject() instanceof Weapon){
                weaponObject = (Weapon)(((TestGhostControl)a).getTestObject());
            }
        }
        if(weaponObject == null) return;
        weapon = weaponObject.getSpatial();
        weaponObject.disablePhysics();
    }
    
    public void dropWeapon(){
        if(weaponObject == null) return;
        
        
        //camDir.set(cam.getDirection()).multLocal(0.6f);
        weaponObject.enablePhysics();
        weaponObject.testControl.setPhysicsLocation(weapon.getWorldTranslation());
        weaponObject.testControl.setPhysicsRotation(Matrix3f.ZERO);
        
        weaponObject.testControl.applyImpulse(cam.getDirection().mult(2f), Vector3f.ZERO);
        weapon = null;
        weaponObject = null;
        
    }
    
    public void fireWeapon(){
        if(weaponObject != null){
            weaponObject.shoot(); 
        }
    }
}
