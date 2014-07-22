package okushama.nek;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Keybinds extends KeyHandler {
	public Minecraft mc = Minecraft.getMinecraft();
	public static KeyBinding openConsole = new KeyBinding("Show Binds Console", Keyboard.KEY_C);

	public Keybinds() {
		super(new KeyBinding[] { openConsole }, new boolean[] { true });
	}

	@Override
	public String getLabel() {
		return "Key Bindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (kb.keyCode == openConsole.keyCode && tickEnd && mc.currentScreen == null && !isRepeat)
			Minecraft.getMinecraft().displayGuiScreen(NotEnoughKeys.console);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {}
}