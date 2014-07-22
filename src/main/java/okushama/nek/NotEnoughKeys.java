package okushama.nek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "notenoughkeys", name = "Not Enough Keys", version = "0.0.4")

public class NotEnoughKeys {

    @Instance("notenoughkeys")
    public static NotEnoughKeys instance;
    public static Logger logger = Logger.getLogger("Not Enough Keys");

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    	if(event.getSide() == Side.CLIENT){
    		KeybindTracker.modKeybinds.put("All", new ArrayList<KeyBinding>());   
    		ArrayList<KeyBinding> vanillaKeys = new ArrayList<KeyBinding>();
	    	for(KeyBinding kb : Minecraft.getMinecraft().gameSettings.keyBindings){
	    		vanillaKeys.add(kb);
	    	}
    		KeybindTracker.modKeybinds.put("Minecraft", vanillaKeys);

    	}
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
    	if(event.getSide() == Side.CLIENT){
	    	TickRegistry.registerTickHandler(new Ticker(), Side.CLIENT);
    	}
    }
    
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	if(event.getSide() == Side.CLIENT){
    		for(ModContainer mod : Loader.instance().getActiveModList()){
	    		KeybindTracker.modIds.put(mod.getSource().getName(), mod.getName());
	    	}
	    	KeybindTracker.populate();
    	}
    	
    }
    public static void log(String s, boolean warning) {
    	if(!logger.getParent().equals(FMLLog.getLogger())){
    		logger.setParent(FMLLog.getLogger());
    	}
        logger.log(warning ? Level.WARNING : Level.INFO, s);
    }

    public static void log(String s) {
        log(s, false);
    }
}
