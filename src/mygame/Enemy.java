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
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Enemy implements ActionListener, Updatable, AnimEventListener, Triggerable{
    Spatial shape;
    CharacterControl enemyControl;
    Game game;
    Node rootNode;
    BulletAppState bAppState;
    
    AnimChannel channel;
    AnimControl control;
    Vector3f prevPos;
    int moveCount = 0;
    int moveLimit;
    boolean walk = false;
    
    public Enemy(Game game){
        this(new Vector3f(15, 15, 0), 500, game);
    }
    
    public Enemy(Vector3f pos, Game game){
        this(pos, 500, game);
    }
    
    public Enemy(Vector3f pos, int moveLimit, Game game){
        this.shape = game.getAssetManager().loadModel("Models/Oto/Oto.mesh.xml");        
        this.game = game;
        this.rootNode = game.getRootNode();
        this.bAppState = game.getBulletAppState();
        this.moveLimit = moveLimit;
        
        this.addUpdate();
        setupControls();
        
        
        
        rootNode.attachChild(shape);
        bAppState.getPhysicsSpace().add(enemyControl);
        enemyControl.setPhysicsLocation(pos);

        
        
    }
    
    public void setupControls(){
        this.control = shape.getControl(AnimControl.class);
        this.channel = control.createChannel();
        control.addListener(this);
        channel.setAnim("stand");
        
        enemyControl = new CharacterControl(new CapsuleCollisionShape(1.5f, 6.5f, 1), 6.5f);
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
        //lookAtPlayer();
        moveSteps();
        shootPlayer();
    }
    
    public void moveSteps(){
        //System.out.println(moveCount);
        if(walk) moveCount += 1;
        if(moveCount > moveLimit){
            moveCount = 0;
            enemyControl.setViewDirection(enemyControl.getViewDirection().negate());
        }
    }
    public void shootPlayer(){
        
        CollisionResults results = new CollisionResults();
        Vector3f x = game.currentLevel.getPlayer().playerModel.getWorldTranslation().subtract(shape.getWorldTranslation());
        Ray ray = new Ray(shape.getWorldTranslation(), x);
        game.getVisibleNode().collideWith(ray, results);
        
        //System.out.println(results.size());
        for(int i = 0; i < results.size(); i++){
            //System.out.println(results.getCollision(i).getGeometry().getName());

        }
    }
    
    public void lookAtPlayer(){
         //System.out.println(shape.getWorldRotation());
        Vector3f x = game.currentLevel.getPlayer().playerModel.getWorldTranslation().subtract(shape.getWorldTranslation());
        enemyControl.setViewDirection(x);
    }
    
    
    public void moveWalk(){
        Vector3f walkDir = new Vector3f(0, 0 ,0);
        if(walk)walkDir.addLocal(shape.getLocalRotation().getRotationColumn(2).mult(0.1f));
        
        Vector3f currentPos = shape.getWorldTranslation();
        if(prevPos != null){
            Vector3f diff = currentPos.subtract(prevPos);
            System.out.println(diff);
            //if(Math.abs(diff.x) <= .000001 && Math.abs(diff.y) <= .000001 && Math.abs(diff.z) <= .000001)
                //enemyControl.setViewDirection(walkDir.negate());
        }
        enemyControl.setWalkDirection(walkDir);
        //System.out.println(prevPos);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void trigger(){
        //System.out.println("Player is Visible" + ++cnt);
        lookAtPlayer();
    }
}