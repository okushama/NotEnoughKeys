package okushama.nek;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Binds {
	public static Binds instance = null;
	public HashMap<Integer, String> binds = new HashMap<Integer, String>();

	public static void init() {
		instance = new Binds();
		instance.loadBinds();
	}

	public static void addBind(int key, String content) {
		instance.binds.put(key, content);
		instance.saveBinds();
	}

	public static void removeBind(int key) {
		if (instance.binds.containsKey(key)) {
			instance.binds.remove(key);
			instance.saveBinds();
		}
	}

	public static boolean keyBound(int key) {
		for (int k : instance.binds.keySet()) {
			if (k == key) {
				return true;
			}
		}
		return false;
	}

	public void saveBinds() {
		try {
			File f = new File("binds.json");
			NotEnoughKeys.logger.log(Level.INFO, f.getAbsolutePath());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonified = gson.toJson(instance);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			writer.write(jsonified);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadBinds() {
		try {
			Gson gson = new Gson();
			Reader in = new InputStreamReader(new FileInputStream(new File("binds.json")));
			instance = gson.fromJson(in, Binds.class);
			in.close();
		} catch (Exception e) {
			saveBinds();
			loadBinds();
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();

	public static void tick() {
		for (int key : instance.binds.keySet()) {
			if (Keyboard.isKeyDown(key) && Minecraft.getMinecraft().currentScreen == null) {
				if (pressedKeys.containsKey(key)) {
					if (pressedKeys.get(key)) {
						continue;
					}
				}
				Minecraft.getMinecraft().thePlayer.sendChatMessage(instance.binds.get(key));
				pressedKeys.put(key, true);
				return;
			}
		}
		Integer[] keys = pressedKeys.keySet().toArray(new Integer[0]);
		for (int i = 0; i < keys.length; i++) {
			if (!Keyboard.isKeyDown(keys[i])) {
				pressedKeys.put(keys[i], false);
			}
		}
	}
}