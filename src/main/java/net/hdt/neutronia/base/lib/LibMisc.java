package net.hdt.neutronia.base.lib;

import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public final class LibMisc {

	// Mod Constants
	public static final String MOD_ID = "neutronia";
	public static final String MOD_NAME = "Neutronia";
	public static final String VERSION = "0.3.0";
	public static final String DEPENDENCIES = "required-before:huskylib2;";
	public static final String PREFIX_MOD = MOD_ID + ":";

	// Proxy Constants
	public static final String PROXY_COMMON = "net.hdt.neutronia.base.proxy.CommonProxy";
	public static final String PROXY_CLIENT = "net.hdt.neutronia.base.proxy.ClientProxy";
	public static final String GUI_FACTORY = "net.hdt.neutronia.base.client.gui.GuiFactory";

	public static final List<String> OREDICT_DYES = Arrays.asList("dyeBlack",
			"dyeRed",
			"dyeGreen",
			"dyeBrown",
			"dyeBlue",
			"dyePurple",
			"dyeCyan",
			"dyeLightGray",
			"dyeGray",
			"dyePink",
			"dyeLime",
			"dyeYellow",
			"dyeLightBlue",
			"dyeMagenta",
			"dyeOrange",
			"dyeWhite");

	public static final ResourceLocation GENERAL_ICONS_RESOURCE = new ResourceLocation(MOD_ID, "textures/misc/general_icons.png");

	public static final String MOD_WEBSITE = "https://github.com/HuskysDevelopmentTeam/Neutronia";
}