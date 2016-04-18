package mygame;

import com.jme3.math.Vector3f;

public class Assault extends Weapon {
    public Assault(Vector3f pos, Game game){
        super(game.getAssetManager().loadModel("Models/carbine.j3o"), pos, null, game); 
    }
    
    public void shoot(){
        super.shoot();
        super.shoot();
    }
   
}