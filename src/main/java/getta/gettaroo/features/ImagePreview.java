package getta.gettaroo.features;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import getta.gettaroo.Gettaroo;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.TextureHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

public class ImagePreview {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private static BufferedImage image;
    private static String loaded;
    private static int tex = -1;
    private int width = 100;
    private int heigth = 100;

    public static boolean render(String value) {
        try {
            final URL url = new URL(value);
            final String host = url.getHost();
            boolean exists = false;

            for(TrustedHosts trustedHost : TrustedHosts.values()) {
                if(trustedHost.getHost().equalsIgnoreCase(host)) {
                    exists = true;
                    break;
                }
            }

            if(!exists) return false;
        } catch (MalformedURLException e) {
            return false;
        }

        if(!value.startsWith("http")) {
            if(tex != -1) {
                GlStateManager.deleteTexture(tex);
            }
            tex = -1;
            return false;
        }

        if(value.contains("imgur.com/")) {
            final String[] split = value.split("/");
            value = String.format("https://i.imgur.com/%s.png", split[split.length - 1]);
        }

        if(image != null) {

            String identifier = "";

            try {
                String[] split = value.split("/");
                String identifierValue = split[split.length - 1];

                saveBufferedImageAsIdentifier(image, new Identifier(identifierValue));
                identifier = identifierValue;
            } catch (IOException | IndexOutOfBoundsException e) {
                System.out.println("An error ocurred while converting the image to identifier");
                return false;
            }

            //all scalling things
            RenderSystem.pushMatrix();
            RenderSystem.scalef(50, 50, 50);
            RenderSystem.enableTexture();
            MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier(identifier));
            Screen screen = MinecraftClient.getInstance().currentScreen;
            assert screen != null;
            float width =(float) screen.width / 2;
            float height = screen.height / 1.5f;

            drawTexture(0, 0, (int) width, (int)height, Gettaroo.matrixStack.peek().getModel());
            System.out.println("pito");
        }

        return true;
    }

    public static void drawTexture(int x, int y, int width, int height, Matrix4f matrices) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrices, (float)x, (float)y + height, (float)0).texture(0, 1).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(matrices, (float)x + width, (float)y + height, (float)0).texture(1, 1).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(matrices, (float)x + width, y, (float)0).texture(1, 0).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(matrices, (float)x, (float)y, (float)0).texture(0, 0).color(255, 255, 255, 255).next();
        bufferBuilder.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(bufferBuilder);

    }

    private void loadImage(String url) {
        HttpURLConnection connection = null;
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Gettaroo image preview");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);

            try(InputStream stream = connection.getInputStream()) {
                image = ImageIO.read(stream);
                //TODO: mirarselo bien
            }
        } catch (Exception e) {
            System.out.println("Error ocurred while trying to get the image preview");
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void saveBufferedImageAsIdentifier(BufferedImage bufferedImage, Identifier identifier) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        byte[] bytes = stream.toByteArray();

        ByteBuffer data = BufferUtils.createByteBuffer(bytes.length).put(bytes);
        data.flip();
        NativeImage img = NativeImage.read(data);
        NativeImageBackedTexture texture = new NativeImageBackedTexture(img);

        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().getTextureManager().registerTexture(identifier, texture);
        });
    }

}
