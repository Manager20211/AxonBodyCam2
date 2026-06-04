package cn.pr0xy.client;

/**Author:pr0xy | 2026-06-04**/

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BodyCamHUD implements HudRenderCallback {

    private static final int COLOR_RED = 0xFFFF3333;
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_GRAY = 0xFF888888;

    private static final String DEVICE_ID = "AXON BODY 2 X81237971";
    private static final String MOD_INFO = "AXON BodyCam Mod";

    private static final Identifier AXON_FONT = new Identifier("axon", "axon_body");
    private static final Style AXON_STYLE = Style.EMPTY.withFont(AXON_FONT);

    private static final Identifier REC_DOT_TEXTURE = new Identifier("axon", "textures/gui/rec_dot.png");
    private static final int DOT_SIZE = 10;

    private static final Identifier LOGO_TEXTURE = new Identifier("axon", "icon.png");
    private static final int LOGO_SIZE = 18;
    private static final int LOGO_GAP = 6;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        TextRenderer font = client.textRenderer;

        // top-left: REC
        renderRecIndicator(context, font, 18, 14);
        // top-right: watermark + logo
        renderWatermark(context, font, sw - 18, 14);
        // bottom-left: info
        renderInfoBar(context, font, 18, sh - 18);
    }

    // blinking red dot + REC label
    private void renderRecIndicator(DrawContext context, TextRenderer font, int x, int y) {
        long t = System.currentTimeMillis();
        float phase = (t % 1000) / 1000.0f;
        float alpha = 0.2f + 0.8f * Math.max(0, (float) Math.cos(phase * Math.PI * 2));

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        context.drawTexture(REC_DOT_TEXTURE, x, y, 0, 0, DOT_SIZE, DOT_SIZE, DOT_SIZE, DOT_SIZE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        context.drawText(font, Text.literal("REC").setStyle(AXON_STYLE), x + DOT_SIZE + 4, y, COLOR_RED, true);
    }

    // right-aligned: timestamp + device id + logo
    private void renderWatermark(DrawContext context, TextRenderer font, int rightX, int topY) {
        String ts = dateFormat.format(new Date());
        Text tsText = Text.literal(ts).setStyle(AXON_STYLE);
        Text idText = Text.literal(DEVICE_ID).setStyle(AXON_STYLE);

        int tsW = font.getWidth(tsText);
        int idW = font.getWidth(idText);
        int blockW = Math.max(tsW, idW);
        int totalW = blockW + LOGO_GAP + LOGO_SIZE;

        int left = rightX - totalW;
        int logoX = left + blockW + LOGO_GAP;

        context.drawText(font, tsText, left, topY, COLOR_WHITE, true);
        context.drawText(font, idText, left, topY + 12, COLOR_WHITE, true);
        context.drawTexture(LOGO_TEXTURE, logoX, topY - 1, 0, 0, LOGO_SIZE, LOGO_SIZE, LOGO_SIZE, LOGO_SIZE);
    }

    // bottom-left mod info
    private void renderInfoBar(DrawContext context, TextRenderer font, int x, int y) {
        context.drawText(font, Text.literal(MOD_INFO).setStyle(AXON_STYLE), x, y - 10, COLOR_GRAY, false);
    }

}
