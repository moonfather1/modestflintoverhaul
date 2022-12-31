package moonfather.modestflintoverhaul;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.Pair;

public class OptionsHolder
{
	public static class Common
	{
		private static final int defaultBaseDropChance = 30;
		private static final int defaultFortune1Chance = 40;
		private static final int defaultFortune2Chance = 90;
		public final ConfigValue<Integer> BaseDropChance;
		public final ConfigValue<Integer> Fortune1Chance;
		public final ConfigValue<Integer> Fortune2Chance;

		private static final boolean defaultDispenserOverrideToDisable = false;
		private static final boolean defaultDispenserOverrideToEnable = false;
		public final ConfigValue<Boolean> DispenserOverrideToEnable;
		public final ConfigValue<Boolean> DispenserOverrideToDisable;

		public Common(ForgeConfigSpec.Builder builder)
		{
			builder.push("Fortune");
			this.Fortune1Chance = builder.comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune I tool. You set chances for Fortune I and Fortune II here, and mod will extrapolate for levels three and onward.")
					.defineInRange("Flint drop chance + bonus for Fortune I", defaultFortune1Chance, 1, 100);
			this.Fortune2Chance = builder.comment("This is a chance (in percents) that is added to a base chance that a piece of flint will drop from gravel block when using Fortune II tool. If you go over 100%, it's fine. Default is 120 (30% chance without fortune plus 90% for FortuneII), meaning 12 flint pieces from 10 gravel blocks.")
					.defineInRange("Flint drop chance + bonus for Fortune II", defaultFortune2Chance, 1, 100);
			builder.pop();
			builder.push("Main");
			this.BaseDropChance = builder.comment("This is a chance (in percents) that a piece of flint will drop from gravel block. 30 or 40 is recommended because you can't get more from gravel blocks that drop.")
					.defineInRange("Flint drop chance without Fortune", defaultBaseDropChance, 1, 100);
			builder.pop();
			builder.push("Dispenser");
			this.DispenserOverrideToEnable = builder.comment("Gravel blocks' dispenser behavior is disabled if you don't use Quark or Botania mods. Set this to true to enable gravel being placed as blocks in front of dispenser, even without those two mods.")
					.define("Dispenser override - enable", defaultDispenserOverrideToEnable);
			this.DispenserOverrideToDisable = builder.comment("Gravel blocks' dispenser behavior is enabled if you are using Quark or Botania mods. Set this to true to disable gravel being placed as blocks in front of dispenser. The reason for all this is that it is rather bothersome to check other mod's config.")
					.define("Dispenser override - disable", defaultDispenserOverrideToDisable);
			builder.pop();
		}
	}

	///////////////////////////////////////////////////

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	public static int GetFortuneBonus(int level)
	{
		if (level == 0)
		{
			return 0;
		}
		float x0 = 0, y0 = 0;
		float x1 = 1, y1 = OptionsHolder.COMMON.Fortune1Chance.get();
		float x2 = 2, y2 = OptionsHolder.COMMON.Fortune2Chance.get(); // three points, we will extrapolate others
		float x = level;
		float result = ((x-x1)*(x-x2)/(x0-x1)*(x0-x2))*y0 + ((x-x0)*(x-x2)/(x1-x0)*(x1-x2))*y1 + ((x-x0)*(x-x1)/(x2-x0)*(x2-x1))*y2; //x1 0+y1+0
		return Math.round(result);
	}

	public static boolean ShouldDispenseBlocks()
	{
		if (ModList.get().isLoaded("quark") || ModList.get().isLoaded("botania"))
		{
			return ! OptionsHolder.COMMON.DispenserOverrideToDisable.get();
		}
		else
		{
			return OptionsHolder.COMMON.DispenserOverrideToEnable.get();
		}
	}

	static //constructor
	{
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}
