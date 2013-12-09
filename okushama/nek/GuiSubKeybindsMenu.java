package okushama.nek;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.client.GuiControlsScrollPanel;

@SideOnly(Side.CLIENT)
public class GuiSubKeybindsMenu extends GuiScreen
{
    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;

    /** The title string that is displayed in the top-center of the screen. */
    protected String screenTitle = "Controls";

    /** Reference to the GameSettings object. */
    private GameSettings options;

    /** The ID of the  button that has been pressed. */
    private int buttonId = -1;

    private GuiSubKeybindsScrollPanel scrollPane;
    
    public String subModID = "Misc";
    
    public KeyBinding[] keyBindings = {};

    public GuiSubKeybindsMenu(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.parentScreen = par1GuiScreen;
        this.options = par2GameSettings;
    }
    
    public GuiSubKeybindsMenu(GuiScreen parent, String id, KeyBinding[] kbs, GameSettings gameSettings){
    	this(parent, gameSettings);
    	subModID = id;
    	keyBindings = kbs;
    }

    /**
     * Gets the distance from the left border of the window to left border of the controls screen
     */
    private int getLeftBorder()
    {
        return this.width / 2 - 155;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        scrollPane = new GuiSubKeybindsScrollPanel(this, options, mc, keyBindings);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 28, I18n.getString("gui.done")));
        scrollPane.registerScrollButtons(7, 8);
        this.screenTitle = this.subModID+" "+I18n.getString("controls.title");
		KeybindTracker.updateConflictCategory();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 200)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
		KeybindTracker.updateConflictCategory();
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
		KeybindTracker.updateConflictCategory();
        if (scrollPane.keyTyped(par1, par2))
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	this.drawBackground(0);
        scrollPane.drawScreen(par1, par2, par3);
        drawCenteredString(fontRenderer, screenTitle, width / 2, 4, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
