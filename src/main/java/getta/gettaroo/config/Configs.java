package getta.gettaroo.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import getta.gettaroo.Constants;
import getta.gettaroo.utils.ColorUtils;
import getta.gettaroo.utils.DirectionUtils;
import getta.gettaroo.utils.RowUtils;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public class Configs implements IConfigHandler {

    static MinecraftClient mc = MinecraftClient.getInstance();

    public static class Utils{

        public static final ConfigString CLIENT_NAME = new ConfigString("clientName", "", "Changes the name of your client");
        public static final ConfigString WINDOW_NAME = new ConfigString("windowName", "", "Changes the name of your window");
        public static final ConfigOptionList AUTO_MOVEMENT_DIRECTION = new ConfigOptionList("autoMovementDirection", DirectionUtils.FORWARD, "The movement the auto movement will go");
        public static final ConfigOptionList SCOREBOARD_OBJECTIVE_COLOR = new ConfigOptionList("scoreBoardObjectiveColor", ColorUtils.WHITE, "The color of the objective");
        public static final ConfigOptionList SCOREBOARD_SCORE_COLOR = new ConfigOptionList("scoreBoardScoreColor", ColorUtils.WHITE, "The color of the score");
        public static final ConfigOptionList CUSTOM_SWAP_ROW = new ConfigOptionList("customSwapRow", RowUtils.ROW_2, "The row you move set to default to use the other ones");
        public static final ConfigDouble JETPACK_SPEED = new ConfigDouble("jetpackSpeed", 1, 1, 50, true, "Amount of jumps");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                CLIENT_NAME,
                WINDOW_NAME,
                AUTO_MOVEMENT_DIRECTION,
                SCOREBOARD_OBJECTIVE_COLOR,
                SCOREBOARD_SCORE_COLOR,
                CUSTOM_SWAP_ROW,
                JETPACK_SPEED
        );
    }

    public static class Server{

        public static final ConfigStringList INSTA_MINE_BLOCKS = new ConfigStringList("instaMineBlocks", ImmutableList.of(), "§4ONLY FOR SERVER \n §rThe blocks in this list will be instamined");
        public static final ConfigBoolean ENDERPEARL_NO_COOLDOWN = new ConfigBoolean("enderPearlNoCooldown", false, "§4ONLY FOR SERVER \n §rWhen you throw an ender pearl, theres no cooldown :D");
        public static final ConfigBoolean LAVA_DOESNT_EXIST = new ConfigBoolean("lavaDoesntExist", false, "§4ONLY FOR SERVER \n  §rDisables the lava in your survival");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                INSTA_MINE_BLOCKS,
                ENDERPEARL_NO_COOLDOWN,
                LAVA_DOESNT_EXIST
        );
    }

    public static class Lists{

        public static final ConfigStringList PREVENT_DROPPING_LIST = new ConfigStringList("preventDroppList", ImmutableList.of(), "The items here won't be dropped");
        public static final ConfigStringList DISABLED_ENTITIES = new ConfigStringList("disabledEntities", ImmutableList.of(), "The entities in this list wont be rendered \n entities still there, they can attack you");


        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                PREVENT_DROPPING_LIST,
                DISABLED_ENTITIES
        );
    }

    private static final String CONFIG_FILE_NAME = Constants.MOD_ID + ".json";
    private static final int CONFIG_VERSION = 1;

    public void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readHotkeyToggleOptions(root, "GettaHotkeys", "GettaToggles",  ImmutableList.copyOf(FeatureToggle.values()));
                ConfigUtils.readHotkeyToggleOptions(root, "CheatHotkeys", "CheatToggles",  ImmutableList.copyOf(Cheats.values()));
                ConfigUtils.readConfigBase(root, "utils", Utils.OPTIONS);
                ConfigUtils.readHotkeys(root, "hotkeys", Hotkeys.HOTKEY_LIST);
                ConfigUtils.readConfigBase(root, "server", Server.OPTIONS);
                ConfigUtils.readConfigBase(root, "lists", Lists.OPTIONS);
            }
        }
    }

    public void saveToFile() {

        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeHotkeyToggleOptions(root, "GettaHotkeys", "GettaToggles", ImmutableList.copyOf(FeatureToggle.values()));
            ConfigUtils.writeConfigBase(root, "utils", Utils.OPTIONS);
            ConfigUtils.writeHotkeys(root, "hotkeys", Hotkeys.HOTKEY_LIST);
            ConfigUtils.writeConfigBase(root, "server", Server.OPTIONS);
            ConfigUtils.writeHotkeyToggleOptions(root, "CheatHotkeys", "CheatToggles", ImmutableList.copyOf(Cheats.values()));
            ConfigUtils.writeConfigBase(root, "lists", Lists.OPTIONS);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}
