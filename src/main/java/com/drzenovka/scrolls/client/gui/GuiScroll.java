package com.drzenovka.scrolls.client.gui;

import com.drzenovka.scrolls.common.item.ItemScroll;
import com.drzenovka.scrolls.common.item.ItemScrollStamped;
import com.drzenovka.scrolls.common.util.ColorUtils;
import com.drzenovka.scrolls.network.PacketSaveScroll;
import com.drzenovka.scrolls.common.core.Scrolls;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
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
    private static final int LINE_HEIGHT = 12; // approximate, can adjust dynamically

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation("scrolls", "textures/gui/scroll_bg.png");
    private static final ResourceLocation STAMP_TEXTURE = new ResourceLocation("scrolls", "textures/gui/stamp_texture.png");

    public GuiScroll(EntityPlayer player, int handSlot) {
        this.player = player;
        this.handSlot = handSlot;
        this.stack = player.inventory.getStackInSlot(handSlot);
        initLinesFromStack();
    }

    private void initLinesFromStack() {
        ensureTag();
        lines = new String[MAX_LINES];

        String saved = getTag().getString(ItemScroll.NBT_PAGE);
        if (saved != null && !saved.isEmpty()) {
            String[] split = saved.split("\n", MAX_LINES);
            for (int i = 0; i < split.length && i < MAX_LINES; i++) {
                lines[i] = split[i] != null ? split[i] : "";
            }
        }

        // Fill remaining lines with empty string
        for (int i = 0; i < MAX_LINES; i++) {
            if (lines[i] == null) lines[i] = "";
        }
    }

    private void ensureTag() {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
    }

    private NBTTagCompound getTag() {
        ensureTag();
        return stack.getTagCompound();
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        int x = (width - 100) / 2;
        int y = (height - 160) / 2;
        buttonList.add(new GuiButton(0, x + 75, y + 145, 30, 20, "Save"));
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
        } else if (keyCode == Keyboard.KEY_BACK && lines[cursorLine].length() > 0) {
            lines[cursorLine] = lines[cursorLine].substring(0, lines[cursorLine].length() - 1);
        } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
            if (cursorLine < MAX_LINES - 1) cursorLine++;
        } else if (typedChar >= 32 && typedChar <= 126) {
            appendCharToLine(typedChar);
        }
    }

    private void appendCharToLine(char c) {
        String next = lines[cursorLine] + c;
        if (fontRendererObj.getStringWidth(next) <= ScrollTextFormatter.MAX_LINE_WIDTH) {
            lines[cursorLine] = next;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        mc.getTextureManager().bindTexture(BG_TEXTURE);

        int guiWidth = 128, guiHeight = 192;
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;

        int meta = stack != null ? stack.getItemDamage() : 0;
        if (meta < 0 || meta >= ColorUtils.GL11_COLOR_VALUES.length) meta = 0;

        float[] color = ColorUtils.GL11_COLOR_VALUES[meta];
        GL11.glColor4f(color[0], color[1], color[2], 1f);

        drawCustomSizedTexture(x, y, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);

        GL11.glColor4f(1f, 1f, 1f, 1f);

        drawTextLines(x, y);
        drawCursor(x, y);
        drawStamp(x, y);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawTextLines(int x, int y) {
        final int LEFT_MARGIN = 32, TOP_MARGIN = 42;
        for (int i = 0; i < MAX_LINES; i++) {
            fontRendererObj.drawString(lines[i], x + LEFT_MARGIN, y + TOP_MARGIN + i * LINE_HEIGHT, 0x000000);
        }
    }

    private void drawCursor(int x, int y) {
        if ((cursorTick / 6) % 2 != 0 || cursorLine >= MAX_LINES) return;
        final int LEFT_MARGIN = 32, TOP_MARGIN = 42;
        String beforeCursor = lines[cursorLine];
        int cursorX = x + LEFT_MARGIN + fontRendererObj.getStringWidth(beforeCursor);
        int cursorY = y + TOP_MARGIN + cursorLine * LINE_HEIGHT;
        fontRendererObj.drawString("_", cursorX, cursorY, 0x000000);
    }

    private void drawStamp(int x, int y) {
        if (!(stack.getItem() instanceof ItemScrollStamped)) return;

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return;

        int count = tag.getInteger("stampCount");
        if (count == 0) return;

        mc.getTextureManager().bindTexture(STAMP_TEXTURE);

        for (int i = 0; i < count; i++) {
            int color = tag.getInteger("stampColor" + i);

            // Convert from 0xRRGGBB to GL floats
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8)  & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;

            GL11.glColor4f(r, g, b, 1f);
            drawCustomSizedTexture(x + 30 + i * 10, y + 150, 0, 0, 16, 16, 16, 16);
        }

        GL11.glColor4f(1,1,1,1);
    }

    private void saveText() {
        if (!(stack.getItem() instanceof ItemScroll)) return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_LINES; i++) {
            sb.append(lines[i]);
            if (i < MAX_LINES - 1) sb.append("\n");
        }
        String pageText = sb.toString();
        String pageAuthor = player.getDisplayName();

        NBTTagCompound tag = getTag();
        tag.setString(ItemScroll.NBT_PAGE, pageText);
        tag.setString(ItemScroll.NBT_AUTHOR, pageAuthor);

        player.inventory.setInventorySlotContents(handSlot, stack);
        Scrolls.NETWORK.sendToServer(new PacketSaveScroll(handSlot, pageText, pageAuthor));
    }

    public void drawCustomSizedTexture(int x, int y, int u, int v,
                                       int width, int height,
                                       int texWidth, int texHeight) {

        float f  = 1F / texWidth;
        float f1 = 1F / texHeight;

        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x,         y + height, this.zLevel, (u) * f,          (v + height) * f1);
        tess.addVertexWithUV(x + width, y + height, this.zLevel, (u + width) * f,  (v + height) * f1);
        tess.addVertexWithUV(x + width, y,          this.zLevel, (u + width) * f,  (v) * f1);
        tess.addVertexWithUV(x,         y,          this.zLevel, (u) * f,          (v) * f1);
        tess.draw();
    }
}
