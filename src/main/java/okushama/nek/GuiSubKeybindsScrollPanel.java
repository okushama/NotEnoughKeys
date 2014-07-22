package okushama.nek;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * Original code from Minecraft Forge over at http://minecraftforge.net
 */
public class GuiSubKeybindsScrollPanel extends GuiSlot
{
    protected static final ResourceLocation WIDGITS = new ResourceLocation("textures/gui/widgets.png");
    private GuiSubKeybindsMenu controls;
    private GameSettings options;
    private Minecraft mc;
    private String[] message;
    private int _mouseX;
    private int _mouseY;
    private int selected = -1;
    
    public KeyBinding[] keyBindings;
    
    public GuiSubKeybindsScrollPanel(GuiSubKeybindsMenu controls, GameSettings options, Minecraft mc, KeyBinding[] kbs)
    {
        super(mc, controls.width, controls.height, 16, (controls.height - 32) + 4, 25);
        this.controls = controls;
        this.options = options;
        this.mc = mc;
        keyBindings = kbs;
    }

    @Override
    protected int getSize()
    {
    	return keyBindings.length;
    }

    @Override
    protected void elementClicked(int i, boolean flag)
    {
        if (!flag)
        {
            if (selected == -1)
            {
                selected = i;
            }
            else
            {
            	int glob = getGlobalKeybindIndex(selected);
                options.setKeyBinding(glob, -100);
                selected = -1;
                KeyBinding.resetKeyBindingArrayAndHash();
        		KeybindTracker.updateConflictCategory();
            }
        }
    }

    @Override
    protected boolean isSelected(int i)
    {
        return false;
    }

    @Override
    protected void drawBackground() {}

    @Override
    public void drawScreen(int mX, int mY, float f)
    {
        _mouseX = mX;
        _mouseY = mY;

        if (selected != -1 && !Mouse.isButtonDown(0) && Mouse.getDWheel() == 0)
        {
            if (Mouse.next() && Mouse.getEventButtonState())
            {
            	int glob = getGlobalKeybindIndex(selected);
                options.setKeyBinding(glob, -100 + Mouse.getEventButton());
                selected = -1;
                KeyBinding.resetKeyBindingArrayAndHash();
            }
        }
        super.drawScreen(mX, mY, f);
    }

    @Override
    protected void drawSlot(int index, int xPosition, int yPosition, int l, Tessellator tessellator)
    {
    	String s = I18n.getString(this.keyBindings[index].keyDescription);
        int width = 70;
        int height = 20;
        xPosition -= 20;
        boolean flag = _mouseX >= xPosition && _mouseY >= yPosition && _mouseX < xPosition + width && _mouseY < yPosition + height;
        int k = (flag ? 2 : 1);

        mc.renderEngine.bindTexture(WIDGITS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        controls.drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
        controls.drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
        
        controls.drawString(mc.fontRenderer, s, xPosition + width + 4, yPosition + 6, 0xFFFFFFFF);

        boolean conflict = false;
    	int globIndex = getGlobalKeybindIndex(index);
        for (int x = 0; x < options.keyBindings.length; x++)
        {
            if (x != globIndex && options.keyBindings[x].keyCode == options.keyBindings[globIndex].keyCode)
            {
                conflict = true;
                break;
            }
        }
    	int glob = getGlobalKeybindIndex(selected);

        String str = (conflict ? EnumChatFormatting.RED : "") + GameSettings.getKeyDisplayString(keyBindings[index].keyCode);
        str = (index == selected ? EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<" : str);
        controls.drawCenteredString(mc.fontRenderer, str, xPosition + (width / 2), yPosition + (height - 8) / 2, 0xFFFFFFFF);
    }
    
    public int getGlobalKeybindIndex(int localIndex){
    	if(localIndex < 0){
    		return -1;
    	}
    	return KeybindTracker.getKeybindIndex(keyBindings[localIndex]);
    }

    public boolean keyTyped(char c, int i)
    {
        if (selected != -1)
        {
        	int glob = getGlobalKeybindIndex(selected);
            options.setKeyBinding(glob, i);
            selected = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
    		KeybindTracker.updateConflictCategory();
            return false;
        }
        return true;
    }
}
