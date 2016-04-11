package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Enemy implements ActionListener, Updatable, AnimEventListener{
    Spatial shape;
    CharacterControl enemyControl;
    Game game;
    Node rootNode;
    BulletAppState bAppState;
    
    AnimChannel channel;
    AnimControl control;
    
    boolean walk = false;
    
    public Enemy(Spatial shape, Game game){
        this.shape = shape;        
        this.game = game;
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        
        this.addUpdate();
        setupControls();
        
        
        
        rootNode.attachChild(shape);
        bAppState.getPhysicsSpace().add(enemyControl);
        enemyControl.setPhysicsLocation(new Vector3f(15, 15, 0));

        
        
    }
    
    public void setupControls(){
        this.control = shape.getControl(AnimControl.class);
        this.channel = control.createChannel();
        control.addListener(this);
        channel.setAnim("stand");
        
        enemyControl = new CharacterControl(new CapsuleCollisionShape(1.5f, 6.5f, 1), 10);
        shape.addControl(enemyControl);
        enemyControl.setJumpSpeed(15);
        enemyControl.setGravity(30);
        enemyControl.setFallSpeed(30);
        
        

    }

    public void setInputMapping(InputManager inputManager, ActionListener al){
        inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(al, "Walk");
    }
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Walk")){
            walk = isPressed;
        }
    }
    
    public void animateWalk(){
        if(walk && channel.getAnimationName().equals("Walk") == false){
            channel.setAnim("Walk");
            channel.setLoopMode(LoopMode.Loop);
            channel.setSpeed(0.7f);
        }
        else if(!walk && channel.getAnimationName().equals("Walk") == true){
            channel.setAnim("stand");
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
    }

    public void addUpdate() {
        game.addUpdateList(this);
    }

    public void update() {
        animateWalk();
        moveWalk();
    }
    
    public void moveWalk(){
        Vector3f walkDir = new Vector3f(0, 0 ,0);
        if(walk) walkDir.addLocal(shape.getLocalRotation().getRotationColumn(2).mult(0.1f));
        
        
        enemyControl.setWalkDirection(walkDir);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
}