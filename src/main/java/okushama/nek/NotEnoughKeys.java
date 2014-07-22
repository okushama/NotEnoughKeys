package okushama.nek;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = NotEnoughKeys.modid, name = NotEnoughKeys.name, version = NotEnoughKeys.version)
public class NotEnoughKeys {
	public static final String modid = "notenoughkeys", name = "Not Enough Keys", version = "0.0.4";

	public static Logger logger;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		if (e.getSide() == Side.CLIENT) {
			KeybindTracker.modKeybinds.put("All", new ArrayList<KeyBinding>());
			ArrayList<KeyBinding> vanillaKeys = new ArrayList<KeyBinding>();
			for (KeyBinding kb : Minecraft.getMinecraft().gameSettings.keyBindings)
				vanillaKeys.add(kb);
			KeybindTracker.modKeybinds.put("Minecraft", vanillaKeys);
		}
	}

	@EventHandler
	public static void init(FMLInitializationEvent e) {
		if (e.getSide() == Side.CLIENT)
			TickRegistry.registerTickHandler(new Ticker(), Side.CLIENT);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent e) {
		if (e.getSide() == Side.CLIENT) {
			for (ModContainer mod : Loader.instance().getActiveModList())
				KeybindTracker.modIds.put(mod.getSource().getName(), mod.getName());
			KeybindTracker.populate();
		}
	}
}