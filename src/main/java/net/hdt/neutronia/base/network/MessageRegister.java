package net.hdt.neutronia.base.network;

import net.hdt.neutronia.base.network.message.*;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {

	public static void init() {
		NetworkHandler.register(MessageChangeConfig.class, Side.CLIENT);
		NetworkHandler.register(MessageUpdateAfk.class, Side.SERVER);
	}
	
}
