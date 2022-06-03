package com.globalfkeys;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(GlobalFKeysConfig.CONFIG_GROUP_NAME)
public interface GlobalFKeysConfig extends Config
{
	String CONFIG_GROUP_NAME = "globalfkeys";
	
	@ConfigItem(
		keyName = "combatTab",
		name = "Combat Tab F-Key",
		description = "F-Key to set for the combat tab",
		position = 1
	)
	default FKey combatTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "skillsTab",
		name = "Skills Tab F-Key",
		description = "F-Key to set for the skills tab",
		position = 2
	)
	default FKey skillsTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "questsTab",
		name = "Quests Tab F-Key",
		description = "F-Key to set for the quests tab",
		position = 3
	)
	default FKey questsTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "inventoryTab",
		name = "Inventory Tab F-Key",
		description = "F-Key to set for the inventory tab",
		position = 4
	)
	default FKey inventoryTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "equipmentTab",
		name = "Equipment Tab F-Key",
		description = "F-Key to set for the equipment tab",
		position = 5
	)
	default FKey equipmentTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "prayerTab",
		name = "Prayer Tab F-Key",
		description = "F-Key to set for the prayer tab",
		position = 6
	)
	default FKey prayerTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "magicTab",
		name = "Magic Tab F-Key",
		description = "F-Key to set for the magic tab",
		position = 7
	)
	default FKey magicTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "friendsTab",
		name = "Friends Tab F-Key",
		description = "F-Key to set for the friends tab",
		position = 8
	)
	default FKey friendsTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "accountManagementTab",
		name = "Account Management Tab F-Key",
		description = "F-Key to set for the account management tab",
		position = 9
	)
	default FKey accountManagementTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "logoutTab",
		name = "Logout Tab F-Key",
		description = "F-Key to set for the logout tab",
		position = 10
	)
	default FKey logoutTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "settingsTab",
		name = "Settings Tab F-Key",
		description = "F-Key to set for the settings tab",
		position = 11
	)
	default FKey settingsTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "emotesTab",
		name = "Emotes Tab F-Key",
		description = "F-Key to set for the emotes tab",
		position = 12
	)
	default FKey emotesTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "chatChannelTab",
		name = "Chat-Channel Tab F-Key",
		description = "F-Key to set for the chat-channel tab",
		position = 13
	)
	default FKey chatChannelTab()
	{
		return FKey.NONE;
	}

	@ConfigItem(
		keyName = "musicPlayerTab",
		name = "Music Player Tab F-Key",
		description = "F-Key to set for the music player tab",
		position = 14
	)
	default FKey musicPlayerTab()
	{
		return FKey.NONE;
	}
}
