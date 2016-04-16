package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

public class TownLevel extends Level{
    Game game;
    Node rootNode;
    AssetManager assetManager;
    InputManager inputManager;
    
    BulletAppState bAppState;
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;

    public TownLevel(Game game) {  
        super(game);
        this.game = game;
        this.rootNode = game.getRootNode();
        this.assetManager = game.getAssetManager();
        this.inputManager = game.getInputManager();
        
        setupPhysics();
        setupCamera();
        setupLight();
        
        Weapon revolver = new Weapon(assetManager.loadModel("/Models/wade_high_rez_colt/wade_high_rez_colt.j3o"), 
                                    new Vector3f(10f, 3f, 3f), null, game);
       player = new Player(new CapsuleCollisionShape(1.5f, 6f, 1), game);
        scene = new Scene(assetManager.loadModel("/Scenes/town/main.scene"), game);
        
        
        
        player.setInputMapping(inputManager, player);
        
        
        scoreBoard = new ScoreBoard(0, game);
        
        Enemy oto = new Enemy(assetManager.loadModel("Models/Oto/Oto.mesh.xml"), game);
        oto.setInputMapping(inputManager, oto);
        
    }
    
    public Player getPlayer(){
        return player;
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
    
    public ScoreBoard getScoreBoard(){
        return scoreBoard;
    }

    @Override
    public void nextLevel() {
    }
}