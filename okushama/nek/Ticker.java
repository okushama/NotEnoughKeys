package okushama.nek;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptions;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class Ticker implements ITickHandler{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.CLIENT))){
			if(Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiControls){
				//Minecraft.getMinecraft().displayGuiScreen(new GuiKeybinds("IndustrialCraft 2", Minecraft.getMinecraft().gameSettings));
				//Minecraft.getMinecraft().displayGuiScreen(new GuiKeybinds("Minecraft", Minecraft.getMinecraft().gameSettings));
				Minecraft.getMinecraft().displayGuiScreen(new GuiKeybindsMenu());
				NotEnoughKeys.log("Replaced an instance of the controls gui!");
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Not Enough Ticker";
	}

}
