package moonfather.modestflintoverhaul;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigManager
{
    //************************************************//

    private static class Holder
    {
        public String DispenserOverrideToEnable_name = "Dispenser override - enable";
        public String DispenserOverrideToEnable_option_description = "Gravel blocks' dispenser behavior is disabled if you don't use Quark or Botania mods. Set this to true to enable gravel being placed as blocks in front of dispenser, even without those two mods.";
        public boolean DispenserOverrideToEnable_value = false;
        public String DispenserOverrideToDisable_name = "Dispenser override - disable";
        public String DispenserOverrideToDisable_option_description = "Gravel blocks' dispenser behavior is enabled if you are using Quark or Botania mods. Set this to true to disable gravel being placed as blocks in front of dispenser. The reason for all this is that it is rather bothersome to check other mod's config.";
        public boolean DispenserOverrideToDisable_value = false;
        public String BaseDropChance_name = "Flint drop chance without Fortune";
        public String BaseDropChance_option_description = "This is a chance (in percents) that a piece of flint will drop from gravel block. 30 or 40 is recommended because you can't get more from gravel blocks that drop.";
        public int BaseDropChance_value = 30;
        public String Fortune1Chance_name = "Flint drop chance + bonus for Fortune I";
        public String Fortune1Chance_option_description = "This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune I tool. You set chances for Fortune I and Fortune II here, and mod will extrapolate for levels three and onward.";
        public int Fortune1Chance_value = 40;
        public String Fortune2Chance_name = "Flint drop chance + bonus for Fortune II";
        public String Fortune2Chance_option_description = "This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune II tool. If you go over 100%, it's fine. Default is 120 (30% chance without fortune plus 90% for FortuneII), meaning 12 flint pieces from 10 gravel blocks.";
        public int Fortune2Chance_value = 90;
    }

    private static Holder instance = null;

    //************************************************//

    public static int getBaseDropChance()
    {
        loadIfNeeded();
        return instance.BaseDropChance_value;
    }

    public static int getFortuneBonus(int level)
    {
        loadIfNeeded();
        if (level == 0)
        {
            return 0;
        }
        float x0 = 0, y0 = 0;
        float x1 = 1, y1 = instance.Fortune1Chance_value;
        float x2 = 2, y2 = instance.Fortune2Chance_value; // three points, we will extrapolate others
        float x = level;
        float result = ((x-x1)*(x-x2)/(x0-x1)*(x0-x2))*y0 + ((x-x0)*(x-x2)/(x1-x0)*(x1-x2))*y1 + ((x-x0)*(x-x1)/(x2-x0)*(x2-x1))*y2; //x1 0+y1+0
        return Math.round(result);
    }

    public static boolean shouldDispenseBlocks()
    {
        loadIfNeeded();
        if (FabricLoader.getInstance().isModLoaded("quark") || FabricLoader.getInstance().isModLoaded("botania"))
        {
            return ! instance.DispenserOverrideToDisable_value;
        }
        else
        {
            return instance.DispenserOverrideToEnable_value;
        }
    }

    //************************************************//

    private static void loadIfNeeded()
    {
        if (instance == null)
        {
            Path configPath = FabricLoader.getInstance().getConfigDir().resolve(Constants.MODID + "-server.json");
            if (configPath.toFile().exists())
            {
                try
                {
                    Gson gson = new Gson();
                    instance = gson.fromJson(Files.readString(configPath), Holder.class);
                }
                catch (IOException ignored)
                {
                    ModestFlintOverhaul.LOGGER.error("MFO mod: config reading error.");
                }
            }
            if (instance == null)
            {
                instance = new Holder();
            }
            if (! configPath.toFile().exists())
            {
                try
                {
                    Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
                    String text = gson.toJson(instance, Holder.class);
                    Files.writeString(configPath, text, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
                catch (IOException ignored)
                {
                    ModestFlintOverhaul.LOGGER.error("MFO mod: config writing error.");
                }
            }
        }
    }
}
