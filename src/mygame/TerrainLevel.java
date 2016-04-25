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

//Terrain Level Incomplete

public class TerrainLevel extends Level{
    
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;

    public TerrainLevel(Game game) {  
        super(game);     
        player = new Player(new CapsuleCollisionShape(1.5f, 6f, 1), game);
        scene = new Scene(assetManager.loadModel("/Scenes/wildhouse/main.scene"), game);

        player.setInputMapping(inputManager, player);
        
        
        scoreBoard = new ScoreBoard(game);
        
        Exit exit = new Exit(new Vector3f(-245f, 25f, -50f), Quaternion.IDENTITY, game, 0.6f);
    }

    @Override
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public void nextLevel() {
        this.dispose();
        
        game.setCurrentLevel(new HighScoreLevel(game, true));
    }

    @Override
    public Player getPlayer() {
        return player;
    }
   
}