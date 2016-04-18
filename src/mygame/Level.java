package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class Level{
    Game game;
    Node rootNode;
    AssetManager assetManager;
    InputManager inputManager;
    BulletAppState bAppState;

    
    public Level(Game game){
        this.game = game;
        this.rootNode = game.getRootNode();
        this.assetManager = game.getAssetManager();
        this.inputManager = game.getInputManager();
        
        setupPhysics();
        setupCamera();
        setupLight();
    }
    
     
     
    public void setupSettings(){
        
        
    }
    
    public void setupCamera(){
        game.getViewPort().setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        game.getFlyByCamera().setMoveSpeed(100);
    }
    
    public void setupPhysics(){
        game.setBulletAppState(bAppState = new BulletAppState());
        game.getStateManager().attach(bAppState);
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
    
    public BulletAppState getBulletAppState(){
        return bAppState;
    }
    
    public void removeLight(){
        for(Light light: rootNode.getWorldLightList()){
            rootNode.removeLight(light);
        }
        for(Light light: rootNode.getLocalLightList()){
            rootNode.removeLight(light);
        }
    }
    
      
    public void dispose(){
        //game.updateList.clear();
        game.clearUpdateList();
        rootNode.detachAllChildren();
        game.getVisibleNode().detachAllChildren();
        game.getGuiNode().detachAllChildren();
        
        removeLight();
        game.getStateManager().detach(bAppState);
        rootNode.attachChild(game.getVisibleNode());
        if(getPlayer() != null) getPlayer().dropWeapon();
        //game.getInputManager().clearMappings();
        
    }
    
    public abstract ScoreBoard getScoreBoard();
    public abstract void nextLevel();
    public abstract Player getPlayer();

}