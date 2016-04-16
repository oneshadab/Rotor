package mygame;

import com.jme3.scene.Node;

public abstract class Level{
   
    
    public Level(Game game){
    }

    public abstract Player getPlayer();
    public abstract void nextLevel();
}