package com.globalfkeys;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GlobalFKeysPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GlobalFKeysPlugin.class);
		RuneLite.main(args);
	}
}