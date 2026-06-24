package moonfather.modestflintoverhaul;

import moonfather.modestflintoverhaul.changes.GravelDispenseBehavior;
import moonfather.modestflintoverhaul.changes.PiglinBarterDropModifier;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModestFlintOverhaul implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MODID);

	@Override
	public void onInitialize()
	{
		ItemsAndBlocks.init();
		GravelDispenseBehavior.init();
		// todo: neoforge - translate tags.   unstupidify config.
		// todo: neoforge - one MI recipe has no conditions
		// todo: fabric - move CreativeModeTabEvents.modifyOutputEvent into client init ?
		PiglinBarterDropModifier.init();
	}
}