package okushama.nek;

import java.util.EnumSet;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class Ticker implements ITickHandler {
	public boolean liteloaderExists = true;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.CLIENT))) {
			if (liteloaderExists) {
				try {
					Class liteloadergui = Class.forName("com.mumfrey.liteloader.gui.GuiControlsPaginated");
					if (liteloadergui != null) {
						if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen.getClass().equals(liteloadergui)) {
							Minecraft.getMinecraft().displayGuiScreen(new GuiKeybindsMenu());
							NotEnoughKeys.logger.log(Level.INFO, "Replaced the instance of LiteLoader's controls gui!");
						}
					}
				} catch (ClassNotFoundException e) {
					liteloaderExists = false;
				}
			}
			if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiControls && !(Minecraft.getMinecraft().currentScreen instanceof GuiControlsOverride)) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiKeybindsMenu());
				NotEnoughKeys.logger.log(Level.INFO, "Replaced the instance of Minecraft controls gui!");
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Not Enough Ticker";
	}
}