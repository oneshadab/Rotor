package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import java.util.ArrayList;

//Terrain Level Incomplete

public class HighScoreLevel extends Level{
    
    Player player;
    Scene scene;
    ScoreBoard scoreBoard;

    public HighScoreLevel(Game game, boolean gameover) {  
        super(game);     
        game.getGuiNode().detachAllChildren();
        
        BitmapText bmt = null;
        bmt = new BitmapText(game.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        bmt.setQueueBucket(RenderQueue.Bucket.Inherit);
        bmt.setSize(25);
        if(gameover) 
            bmt.setText("\n\n     Congrats!!\n\nYou Finished the game");
        else
            bmt.setText("\n\nEnemies killed: " + ScoreBoard.points);
        
        game.getGuiNode().attachChild(bmt);
        
        int screenWidth = game.getContext().getSettings().getWidth();
        int screenHeight = game.getContext().getSettings().getHeight();
        
        bmt.setLocalTranslation(new Vector3f(screenWidth - bmt.getLineWidth() - 250, screenHeight - 5, 0));
    }

    
    @Override
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public void nextLevel() {
        this.dispose();
        
        System.exit(0);
    }

    @Override
    public Player getPlayer() {
        return player;
    }
   
}