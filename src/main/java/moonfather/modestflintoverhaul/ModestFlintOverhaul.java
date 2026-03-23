package moonfather.modestflintoverhaul;

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
		// todo: neoforge - translate tags.   unstupidify config.
		// todo: neoforge - one MI recipe has no conditions
		// todo: fabric - should CreativeModeTabEvents.modifyOutputEvent be in client part?

		//config, tooltips, recipes
	}
}