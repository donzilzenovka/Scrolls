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

    public static final int MAX_LINES = 10;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation("scrolls", "textures/gui/scroll_bg.png");

    public GuiScroll(EntityPlayer player, int handSlot) {
        this.player = player;
        this.handSlot = handSlot;
        this.stack = player.inventory.getStackInSlot(handSlot);
        initLinesFromStack();
    }

    private void initLinesFromStack() {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        lines = new String[MAX_LINES];
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemScroll.NBT_PAGE)) {
            String saved = stack.getTagCompound().getString(ItemScroll.NBT_PAGE);
            if (saved != null && !saved.isEmpty()) {
                String[] split = saved.split("\n", MAX_LINES);
                for (int i = 0; i < split.length && i < MAX_LINES; i++) {
                    lines[i] = split[i] != null ? split[i] : "";
                }
            }
        }
// Fill any remaining lines with empty strings
        for (int i = 0; i < MAX_LINES; i++) {
            if (lines[i] == null) lines[i] = "";
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        int x = (width - 100) / 2;
        int y = (height - 160) / 2;

        buttonList.add(new GuiButton(0, x + 75, y + 145, 20, 20, "[X]"));
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
        if (button.id == 0) {
            saveText();
            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }

        if (keyCode == Keyboard.KEY_BACK && lines[cursorLine].length() > 0) {
            lines[cursorLine] = lines[cursorLine].substring(0, lines[cursorLine].length() - 1);
        } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
            if (cursorLine < MAX_LINES - 1) cursorLine++;
        } else if (typedChar >= 32 && typedChar <= 126) {
            String test = lines[cursorLine] + typedChar;
            int width = fontRendererObj.getStringWidth(test);
            if (width <= ScrollTextFormatter.MAX_LINE_WIDTH) {
                lines[cursorLine] = test;
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

        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

        final int LEFT_MARGIN = 16; // tweak this for desired positioning
        final int TOP_MARGIN = 32;     // vertical offset from top of scroll (tweak as needed)

        int lineHeight = fontRendererObj.FONT_HEIGHT + 2;
        for (int i = 0; i < MAX_LINES; i++) {
            fontRendererObj.drawString(lines[i], x + LEFT_MARGIN, y + TOP_MARGIN + i * lineHeight, 0x000000);
        }

        if ((cursorTick / 6) % 2 == 0 && cursorLine < MAX_LINES) {
            String textBeforeCursor = lines[cursorLine];
            int cursorX = x + LEFT_MARGIN + fontRendererObj.getStringWidth(textBeforeCursor);
            int cursorY = y + TOP_MARGIN + cursorLine * lineHeight;
            fontRendererObj.drawString("_", cursorX, cursorY, 0x000000);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void saveText() {
        ItemStack stack = player.inventory.getStackInSlot(handSlot);
        if (stack == null || !(stack.getItem() instanceof ItemScroll)) return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_LINES; i++) {
            sb.append(lines[i]);
            if (i < MAX_LINES - 1) sb.append("\n");
        }
        String pageText = sb.toString();
        String pageAuthor = player.getDisplayName();

        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = stack.getTagCompound();

        tag.setString(ItemScroll.NBT_PAGE, pageText);
        tag.setString(ItemScroll.NBT_AUTHOR, pageAuthor);  // track last editor

        player.inventory.setInventorySlotContents(handSlot, stack);
        Scrolls.NETWORK.sendToServer(new PacketSaveScroll(handSlot, pageText, pageAuthor));
    }
}
