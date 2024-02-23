package moonfather.modestflintoverhaul;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigManager
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final int defaultBaseDropChance = 30;
    private static final int defaultFortune1Chance = 40;
    private static final int defaultFortune2Chance = 90;

    private static final boolean defaultDispenserOverrideToDisable = false;
    private static final boolean defaultDispenserOverrideToEnable = false;

    private static final ModConfigSpec.IntValue BaseDropChance_ = BUILDER
            .comment("This is a chance (in percents) that a piece of flint will drop from gravel block. 30 or 40 is recommended because you can't get more from gravel blocks that drop.")
            .defineInRange("Flint drop chance without Fortune", defaultBaseDropChance, 1, 100);
    private static final ModConfigSpec.IntValue Fortune1Chance_ = BUILDER
            .comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune I tool. You set chances for Fortune I and Fortune II here, and mod will extrapolate for levels three and onward.")
            .defineInRange("Flint drop chance + bonus for Fortune I", defaultFortune1Chance, 1, 100);;
    private static final ModConfigSpec.IntValue Fortune2Chance_ = BUILDER
            .comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune II tool. If you go over 100%, it's fine. Default is 120 (30% chance without fortune plus 90% for FortuneII), meaning 12 flint pieces from 10 gravel blocks.")
            .defineInRange("Flint drop chance + bonus for Fortune II", defaultFortune2Chance, 1, 100);


    private static final ModConfigSpec.BooleanValue DispenserOverrideToEnable_ = BUILDER
            .comment("Gravel blocks' dispenser behavior is disabled if you don't use Quark or Botania mods. Set this to true to enable gravel being placed as blocks in front of dispenser, even without those two mods.")
            .define("Dispenser override - enable", defaultDispenserOverrideToEnable);
    private static final ModConfigSpec.BooleanValue DispenserOverrideToDisable_ = BUILDER
            .comment("Gravel blocks' dispenser behavior is enabled if you are using Quark or Botania mods. Set this to true to disable gravel being placed as blocks in front of dispenser. The reason for all this is that it is rather bothersome to check other mod's config.")
            .define("Dispenser override - disable", defaultDispenserOverrideToDisable);



    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean DispenserOverrideToEnable;
    public static boolean DispenserOverrideToDisable;
    public static int BaseDropChance;
    public static int Fortune1Chance;
    public static int Fortune2Chance;



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        BaseDropChance = BaseDropChance_.get();
        Fortune1Chance = Fortune1Chance_.get();
        Fortune2Chance = Fortune2Chance_.get();
        DispenserOverrideToEnable = DispenserOverrideToEnable_.get();
        DispenserOverrideToDisable = DispenserOverrideToDisable_.get();
    }

    /////////////////////////////////////////////////////////////////////////

    public static int GetFortuneBonus(int level)
    {
        if (level == 0)
        {
            return 0;
        }
        float x0 = 0, y0 = 0;
        float x1 = 1, y1 = Fortune1Chance;
        float x2 = 2, y2 = Fortune2Chance; // three points, we will extrapolate others
        float x = level;
        float result = ((x-x1)*(x-x2)/(x0-x1)*(x0-x2))*y0 + ((x-x0)*(x-x2)/(x1-x0)*(x1-x2))*y1 + ((x-x0)*(x-x1)/(x2-x0)*(x2-x1))*y2; //x1 0+y1+0
        return Math.round(result);
    }

    public static boolean ShouldDispenseBlocks()
    {
        if (ModList.get().isLoaded("quark") || ModList.get().isLoaded("botania"))
        {
            return ! DispenserOverrideToDisable;
        }
        else
        {
            return DispenserOverrideToEnable;
        }
    }
}
