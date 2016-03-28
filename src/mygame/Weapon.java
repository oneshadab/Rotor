package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class Weapon extends TestObject{
    Camera cam; 
    public Weapon(Spatial testObject, Vector3f pos, Quaternion dir, Game game){
         super(testObject, pos, dir, game);
         this.cam = game.getCamera();
     }
     public void shoot(){
         new Projectile(game, cam.getLocation(), cam.getRotation(), cam.getDirection());
     }
}