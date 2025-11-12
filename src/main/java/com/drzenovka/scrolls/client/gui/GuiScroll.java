package com.drzenovka.scrolls.client.gui;

import com.drzenovka.scrolls.common.item.ItemScroll;
import com.drzenovka.scrolls.network.PacketSaveScroll;
import com.drzenovka.scrolls.common.core.Scrolls;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScroll extends GuiScreen {

    private final EntityPlayer player;
    private final int handSlot;
    private final ItemStack stack;

    private String[] lines;
    private int cursorLine = 0;
    private int cursorTick = 0;

    private static final int MAX_LINES = 10;
    private static final int MAX_CHARS_PER_LINE = 13;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation("scrolls", "textures/gui/scroll_bg.png");

    public GuiScroll(EntityPlayer player, ItemStack scrollStack) {
        this.player = player;
        this.stack = scrollStack;
        this.handSlot = 0;
    }

    public GuiScroll(EntityPlayer player, int handSlot) {
        this.player = player;
        this.handSlot = handSlot;
        this.stack = player.inventory.getStackInSlot(handSlot);

        initLinesFromStack();
    }

    /** Load lines from stack NBT or initialize empty */
    private void initLinesFromStack() {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        String saved = stack.getTagCompound().getString(ItemScroll.NBT_PAGE);
        lines = saved.isEmpty() ? new String[MAX_LINES] : saved.split("\n", MAX_LINES);
        for (int i = 0; i < MAX_LINES; i++) {
            if (i >= lines.length || lines[i] == null) lines[i] = "";
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        int x = (width - 100) / 2;
        int y = (height - 160) / 2;

        // Done button
        buttonList.add(new GuiButton(0, x + 75, y + 145, 20, 20, "Done"));
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateScreen() {
        cursorTick++;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) { // Done button
            saveText();
            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null); // optional: close without saving
            return;
        }

        // Backspace
        if (keyCode == Keyboard.KEY_BACK && lines[cursorLine].length() > 0) {
            lines[cursorLine] = lines[cursorLine].substring(0, lines[cursorLine].length() - 1);
        } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
            if (cursorLine < MAX_LINES - 1) cursorLine++;
        } else if (typedChar >= 32 && typedChar <= 126) {
            if (lines[cursorLine].length() < MAX_CHARS_PER_LINE) {
                lines[cursorLine] += typedChar;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int guiWidth = 100;
        int guiHeight = 160;
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;

        // Draw background
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

        // Draw text
        int lineHeight = fontRendererObj.FONT_HEIGHT + 2;
        for (int i = 0; i < MAX_LINES; i++) {
            fontRendererObj.drawString(lines[i], x + 10, y + 30 + i * lineHeight, 0x000000);
        }

        // Draw blinking cursor
        if ((cursorTick / 6) % 2 == 0 && cursorLine < MAX_LINES) {
            String textBeforeCursor = lines[cursorLine];
            int cursorX = x + 10 + fontRendererObj.getStringWidth(textBeforeCursor);
            int cursorY = y + 30 + cursorLine * lineHeight;
            fontRendererObj.drawString("_", cursorX, cursorY, 0x000000);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /** Save text to stack and sync with server */
    private void saveText() {
        ItemStack stack = player.inventory.getStackInSlot(handSlot);
        if (stack == null || !(stack.getItem() instanceof ItemScroll)) return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_LINES; i++) {
            sb.append(lines[i]);
            if (i < MAX_LINES - 1) sb.append("\n");
        }
        String pageText = sb.toString();

        // Update stack locally
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString(ItemScroll.NBT_PAGE, pageText);

        player.inventory.setInventorySlotContents(handSlot, stack);

        // Sync with server
        Scrolls.NETWORK.sendToServer(new PacketSaveScroll(handSlot, pageText)
        );
    }
}
