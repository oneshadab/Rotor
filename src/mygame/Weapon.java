package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class Weapon extends GameObject{
    Camera cam;
    public Weapon(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
        this(testObject, pos, dir, game, .2f);
    }
    public Weapon(Spatial testObject, Vector3f pos, Quaternion dir, Game game, float scale){
         super(testObject, pos, dir, game, scale);
         this.cam = game.getCamera();
     }
     public void shoot(){
        Vector3f x = cam.getLocation().add(cam.getDirection().normalize().mult(8f));
        new Projectile(game, x, cam.getRotation(), cam.getDirection());
     }
}