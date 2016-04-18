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

    public TownLevel(Game game) {  
        super(game);     
        Weapon revolver = new Weapon(assetManager.loadModel("/Models/wade_high_rez_colt/wade_high_rez_colt.j3o"), 
                                    new Vector3f(10f, 3f, 3f), null, game);
        player = new Player(new CapsuleCollisionShape(1.5f, 6f, 1), game);
        scene = new Scene(assetManager.loadModel("/Scenes/town/main.scene"), game);

        player.setInputMapping(inputManager, player);
        
        
        scoreBoard = new ScoreBoard(0, game);
        
        Enemy oto = new Enemy(game);
        oto.setInputMapping(inputManager, oto);
        
        Exit exit = new Exit(new Vector3f(201f, 3f, -18f), Quaternion.IDENTITY, game);
        exit.testObject.setLocalScale(0.6f);
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