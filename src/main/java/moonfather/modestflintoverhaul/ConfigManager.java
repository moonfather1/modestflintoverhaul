package moonfather.modestflintoverhaul;

import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Constants.MODID)
public class ConfigManager
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final int defaultBaseDropChance = 30;
    private static final int defaultFortune1Chance = 40;
    private static final int defaultFortune2Chance = 90;

    private static final boolean defaultDispenserOverrideToDisable = false;
    private static final boolean defaultDispenserOverrideToEnable = false;

    private static final ModConfigSpec.IntValue baseDropChance = BUILDER
            .comment("This is a chance (in percents) that a piece of flint will drop from gravel block. 30 or 40 is recommended because you can't get more from gravel blocks that drop.")
            .defineInRange("Flint drop chance without Fortune", defaultBaseDropChance, 1, 100);
    private static final ModConfigSpec.IntValue fortune1Chance = BUILDER
            .comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune I tool. You set chances for Fortune I and Fortune II here, and mod will extrapolate for levels three and onward.")
            .defineInRange("Flint drop chance - bonus for Fortune I", defaultFortune1Chance, 1, 100);;
    private static final ModConfigSpec.IntValue fortune2Chance = BUILDER
            .comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune II tool. If you go over 100%, it's fine. Default is 120 (30% chance without fortune plus 90% for FortuneII), meaning 12 flint pieces from 10 gravel blocks.")
            .defineInRange("Flint drop chance - bonus for Fortune II", defaultFortune2Chance, 1, 100);


    private static final ModConfigSpec.BooleanValue dispenserOverrideToEnable = BUILDER
            .comment("Gravel blocks' dispenser behavior is disabled if you don't use Quark or Botania mods. Set this to true to enable gravel being placed as blocks in front of dispenser, even without those two mods.")
            .define("Dispenser override - enable", defaultDispenserOverrideToEnable);
    private static final ModConfigSpec.BooleanValue dispenserOverrideToDisable = BUILDER
            .comment("Gravel blocks' dispenser behavior is enabled if you are using Quark or Botania mods. Set this to true to disable gravel being placed as blocks in front of dispenser. The reason for all this is that it is rather bothersome to check other mod's config.")
            .define("Dispenser override - disable", defaultDispenserOverrideToDisable);



    static final ModConfigSpec SPEC = BUILDER.build();

    /////////////////////////////////////////////////////////////////////////

    public static int getBaseDropChance()
    {
        return baseDropChance.get();
    }

    public static int getFortuneBonus(int level)
    {
        if (level == 0)
        {
            return 0;
        }
        float x0 = 0, y0 = 0;
        float x1 = 1, y1 = fortune1Chance.get();
        float x2 = 2, y2 = fortune2Chance.get(); // three points, we will extrapolate others
        float x = level;
        float result = ((x-x1)*(x-x2)/(x0-x1)*(x0-x2))*y0 + ((x-x0)*(x-x2)/(x1-x0)*(x1-x2))*y1 + ((x-x0)*(x-x1)/(x2-x0)*(x2-x1))*y2; //x1 0+y1+0
        return Math.round(result);
    }

    public static boolean shouldDispenseBlocks()
    {
        if (ModList.get().isLoaded("quark") || ModList.get().isLoaded("botania"))
        {
            return ! dispenserOverrideToDisable.get();
        }
        else
        {
            return dispenserOverrideToEnable.get();
        }
    }
}
