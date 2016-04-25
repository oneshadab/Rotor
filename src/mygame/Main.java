package mygame;

import com.jme3.system.AppSettings;

public class Main {
       
    public static void main(String[] args) {
        Game app = new Game();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        settings.setResolution(840, 600);
        app.setSettings(settings);
        //app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        app.start();
    }
}
