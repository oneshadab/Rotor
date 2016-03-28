package mygame;

public class Main {
       
    public static void main(String[] args) {
        Game app = new Game();
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }
}
