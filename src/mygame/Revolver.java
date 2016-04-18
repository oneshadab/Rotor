package mygame;

import com.jme3.math.Vector3f;

public class Revolver extends Weapon{
    public Revolver(Vector3f pos, Game game){
        super(game.getAssetManager().loadModel("/Models/wade_high_rez_colt/wade_high_rez_colt.j3o"), pos, null, game);                               
    }
}