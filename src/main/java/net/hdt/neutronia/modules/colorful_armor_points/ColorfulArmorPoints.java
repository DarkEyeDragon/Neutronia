package net.hdt.neutronia.modules.colorful_armor_points;

import net.hdt.neutronia.modules.colorful_armor_points.config.Config;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;

@Mod(modid = ColorfulArmorPoints.MODID, name = "Neutronia: Colorful Armor Points", version = "0.0.1", acceptedMinecraftVersions = "[1.12,1.13)", clientSideOnly = true)
public class ColorfulArmorPoints {

	public static final String MODID = "colorful_armor_points";
	public static Logger logger;
	public static Config config;

	private static final GuiArmor GUI = new GuiArmor();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();

		config = new Config(new File(new File(e.getModConfigurationDirectory(), MODID), MODID + ".cfg"));
		config.load();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre e) {
		if(e.getType() == ElementType.ARMOR) {
			e.setCanceled(true); // Don't want anything else rendering on top
			GUI.draw(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
		}
	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent e) {
		if(Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.getEventKey() == Keyboard.KEY_I && Keyboard.getEventKeyState()) {
			config.load();
		}
	}

	@SubscribeEvent
	public void onRenderTooltip(ItemTooltipEvent e) {
		if(e.getFlags().isAdvanced() && e.getItemStack().getItem() instanceof ItemArmor) {
			final String material = ((ItemArmor)e.getItemStack().getItem()).getArmorMaterial().getName();
			e.getToolTip().add(TextFormatting.DARK_GRAY + "Armor Material: " + material);
		}
	}
}