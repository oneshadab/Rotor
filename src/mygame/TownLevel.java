package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

public class TownLevel extends Level{
    
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;

    public TownLevel(Game game, Player player){
        this(game);
        this.player = player;
        player.setInputMapping(inputManager, player);
    }
    
    public TownLevel(Game game) {  
        super(game);     
        //Model is not the Right size [Need to Fix]
        Assault assault = new Assault( new Vector3f(-110, 10, 110), game);
        scene = new Scene(assetManager.loadModel("/Scenes/town/main.j3o"), game);
        
        player = new Player(new Vector3f(-120, 10, 120), new CapsuleCollisionShape(1.5f, 6f, 1), game);
        player.setInputMapping(inputManager, player);
        
        
        scoreBoard = new ScoreBoard(game);
        
        Enemy oto1 = new Enemy(game);
        Enemy oto2 = new Enemy(new Vector3f(0, 10f, -70f), game);
        Enemy oto3 = new Enemy(new Vector3f(220, 10f, -40f), game);

        
        Exit exit = new Exit(new Vector3f(201f, 3f, -18f), Quaternion.IDENTITY, game, 0.6f);
        
        //
    }

    @Override
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public void nextLevel() {
        this.dispose();
        
        game.setCurrentLevel(new TerrainLevel(game));
    }

    @Override
    public Player getPlayer() {
        return player;
    }
   
}