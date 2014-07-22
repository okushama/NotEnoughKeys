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
public class GuiKeybindsScrollPanel extends GuiSlot
{
    protected static final ResourceLocation WIDGITS = new ResourceLocation("textures/gui/widgets.png");
    private GuiKeybindsMenu controls;
    private String[] message;
    private int _mouseX;
    private int _mouseY;
    private int selected = -1;
    
    public String[] buttonNames;
    
    public GuiKeybindsScrollPanel(GuiKeybindsMenu controls, String[] buttons)
    {
        super(Minecraft.getMinecraft(), controls.width, controls.height, 16, (controls.height - 32) + 4, 25);
        this.controls = controls;
        buttonNames = buttons;
    }

    @Override
    protected int getSize()
    {
    	return buttonNames.length;
        //return options.keyBindings.length;
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
        	String type = buttonNames[selected];
        	if(type.equalsIgnoreCase("all")){
        		Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
         		Minecraft.getMinecraft().displayGuiScreen(new GuiControlsOverride(controls, Minecraft.getMinecraft().gameSettings));
         		KeybindTracker.updateConflictCategory();
         		selected = -1;
         		return;
        	}
        	
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    		Minecraft.getMinecraft().displayGuiScreen(new GuiSubKeybindsMenu(controls, type, KeybindTracker.modKeybinds.get(type).toArray(new KeyBinding[0]),Minecraft.getMinecraft().gameSettings));
    		KeybindTracker.updateConflictCategory();
    		selected = -1;
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
        super.drawScreen(mX, mY, f);
    }

    @Override
    protected void drawSlot(int index, int xPosition, int yPosition, int l, Tessellator tessellator)
    {
    	String s = buttonNames[index];
      //  controls.drawTexturedModalRect(xPosition, yPosition, 0, 46 + 1 * 20, 70, 20);
       // controls.drawString(mc.fontRenderer, s, xPosition + 70 + 4, yPosition + 6, 0xFFFFFFFF);
        int width = 70;
        int height = 20;
        xPosition -= 20;
        boolean flag = _mouseX >= xPosition && _mouseY >= yPosition && _mouseX < xPosition + width && _mouseY < yPosition + height;
        int k = (flag ? 2 : 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(WIDGITS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        controls.drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
        controls.drawTexturedModalRect(xPosition + width / 2 , yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
        
        controls.drawString(Minecraft.getMinecraft().fontRenderer, s, xPosition + width + 4, yPosition + 6, 0xFFFFFFFF);

        controls.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Configure", xPosition + (width / 2), yPosition + (height - 8) / 2, 0xFFFFFFFF);
    }

    public boolean keyTyped(char c, int i)
    {
        if (selected != -1)
        {
    		KeybindTracker.updateConflictCategory();
            selected = -1;
            return false;
        }
        return true;
    }
}
