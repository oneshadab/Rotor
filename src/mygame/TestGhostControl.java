
package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;

/**
 *
 * @author Shadab
 */
public class TestGhostControl extends GhostControl{
    TestObject obj;
    public TestGhostControl(CollisionShape shape, TestObject obj){
        super(shape);
        this.obj = obj;
    }
    
    public TestObject getTestObject(){
        return obj;
    }
}
