package com.solitare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.solitare.DisplaySetup;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Group9 Solitaire";
        cfg.width = (int) (1280);
        cfg.height = 720;
        cfg.x = 0;
        cfg.y = 0;
        cfg.resizable = false;
        new LwjglApplication(new DisplaySetup(), cfg);
    }
}
