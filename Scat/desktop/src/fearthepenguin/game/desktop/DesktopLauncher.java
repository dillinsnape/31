package fearthepenguin.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fearthepenguin.game.Scat;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "31";
		config.width = 1024;
		config.height = 768; 
		config.resizable = false;
		new LwjglApplication(new Scat(), config);
	}
}
