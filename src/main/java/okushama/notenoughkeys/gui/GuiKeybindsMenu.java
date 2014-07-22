package okushama.notenoughkeys.gui;

import java.util.ArrayList;

import okushama.notenoughkeys.keys.KeybindTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiKeybindsMenu extends GuiScreen {
	public GuiKeybindsScrollPanel scroll;

	@Override
	public void initGui() {
		ArrayList<String> types = new ArrayList<String>();
		for (String modtype : KeybindTracker.modKeybinds.keySet())
			types.add(modtype);
		scroll = new GuiKeybindsScrollPanel(this, types.toArray(new String[0]));
		scroll.registerScrollButtons(7, 8);
		buttonList.add(new GuiButton(1337, width / 2 - 100, height - 28, I18n.getString("gui.done")));
		KeybindTracker.updateConflictCategory();
		super.initGui();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawBackground(0);
		scroll.drawScreen(par1, par2, par3);
		drawCenteredString(mc.fontRenderer, "Controls", width / 2, 5, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		super.actionPerformed(par1GuiButton);
		if (par1GuiButton.id == 1337) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(null, Minecraft.getMinecraft().gameSettings));
			KeybindTracker.updateConflictCategory();
			return;
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}
}