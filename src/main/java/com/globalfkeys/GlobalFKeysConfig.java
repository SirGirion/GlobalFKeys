package com.globalfkeys;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.ModifierlessKeybind;

@ConfigGroup(GlobalFKeysConfig.CONFIG_GROUP_NAME)
public interface GlobalFKeysConfig extends Config
{
	String CONFIG_GROUP_NAME = "globalKeybinds";
	
	@ConfigItem(
		keyName = "combatTabKey",
		name = "Combat Tab Key",
		description = "Key to set for the combat tab",
		position = 1
	)
	default Keybind combatTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "skillsTabKey",
		name = "Skills Tab Key",
		description = "Key to set for the skills tab",
		position = 2
	)
	default Keybind skillsTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "questsTabKey",
		name = "Quests Tab Key",
		description = "Key to set for the quests tab",
		position = 3
	)
	default Keybind questsTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "inventoryTabKey",
		name = "Inventory Tab Key",
		description = "Key to set for the inventory tab",
		position = 4
	)
	default Keybind inventoryTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "equipmentTabKey",
		name = "Equipment Tab Key",
		description = "Key to set for the equipment tab",
		position = 5
	)
	default Keybind equipmentTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "prayerTabKey",
		name = "Prayer Tab Key",
		description = "Key to set for the prayer tab",
		position = 6
	)
	default Keybind prayerTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "magicTabKey",
		name = "Magic Tab Key",
		description = "Key to set for the magic tab",
		position = 7
	)
	default Keybind magicTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "friendsTabKey",
		name = "Friends Tab Key",
		description = "Key to set for the friends tab",
		position = 8
	)
	default Keybind friendsTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "accountManagementTabKey",
		name = "Account Management Tab Key",
		description = "Key to set for the account management tab",
		position = 9
	)
	default Keybind accountManagementTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "logoutTabKey",
		name = "Logout Tab Key",
		description = "Key to set for the logout tab",
		position = 10
	)
	default Keybind logoutTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "settingsTabKey",
		name = "Settings Tab Key",
		description = "Key to set for the settings tab",
		position = 11
	)
	default Keybind settingsTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "emotesTabKey",
		name = "Emotes Tab Key",
		description = "Key to set for the emotes tab",
		position = 12
	)
	default Keybind emotesTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "chatChannelTabKey",
		name = "Chat-Channel Tab Key",
		description = "Key to set for the chat-channel tab",
		position = 13
	)
	default Keybind chatChannelTab()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "musicPlayerTabKey",
		name = "Music Player Tab Key",
		description = "Key to set for the music player tab",
		position = 14
	)
	default Keybind musicPlayerTab()
	{
		return Keybind.NOT_SET;
	}
}
