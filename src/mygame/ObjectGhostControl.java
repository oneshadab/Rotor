package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;

public class ObjectGhostControl extends GhostControl{
    Object obj;
    public ObjectGhostControl(CollisionShape shape, Object obj){
        super(shape);
        this.obj = obj;
    }
    
    public Object getObject(){
        return obj;
    }
}