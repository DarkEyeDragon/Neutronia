package net.hdt.neutronia.groups.client.features;

import net.hdt.neutronia.base.groups.Component;
import net.hdt.neutronia.groups.client.render.RenderItemFlashing;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemsFlashBeforeExpiring extends Component {

	public static int minTime;
	
	@Override
	public void setupConfig() {
		minTime = loadPropInt("Time To Start Flashing", "How many ticks should the item have left when it starts flashing. Default is 10 seconds (200).", 200);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void preInitClient(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityItem.class, RenderItemFlashing.factory());
	}
	
}
