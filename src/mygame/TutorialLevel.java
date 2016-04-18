package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

public class TutorialLevel extends Level{
    Game game;
    Node rootNode;
    AssetManager assetManager;
    InputManager inputManager;
    
    BulletAppState bAppState;
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;
    

    public TutorialLevel(Game game) {  
        super(game);
        this.game = game;
        this.rootNode = game.getRootNode();
        this.assetManager = game.getAssetManager();
        this.inputManager = game.getInputManager();
        
        scoreBoard = new ScoreBoard(100, 0, game);

        
        Revolver revolver = new Revolver(new Vector3f(-5f, 10f, 35f), game);
        player = new Player(new Vector3f(10f, 10f, 35f), new CapsuleCollisionShape(1.5f, 6f, 1), game);
        scene = new Scene(assetManager.loadModel("/Scenes/tutorialLevel/tutorialLevel.j3o"), game);
                
        
        
        
        player.setInputMapping(inputManager, player);
        
        
        
        Enemy oto = new Enemy(new Vector3f(0, 0, -35), 250, game);
        oto.setInputMapping(inputManager, oto);
        
        //Material
        
        Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("Color", ColorRGBA.Blue);
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/crateTex1.png"));
        //scene.sceneModel.setMaterial(mat);
        Exit exitBox = new Exit(new Vector3f(47f, 3f, 0f), Quaternion.IDENTITY, game, .2f);
    }

    public Player getPlayer(){
        return player;
    }
    
    public ScoreBoard getScoreBoard(){
        return scoreBoard;
    }
    

    @Override
    public void nextLevel(){
        System.out.println("Next Level!!");
        this.dispose();
        
        game.setCurrentLevel(new TownLevel(game));
    }
}