package com.globalfkeys;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Global F Keys"
)
public class GlobalFKeysPlugin extends Plugin
{
	// Following varbits get the F-Key the player has set in settings; value is numeric
	static final int COMBAT_TAB_BINDING = 4675;
	static final int SKILLS_TAB_BINDING = 4676;
	static final int QUESTS_TAB_BINDING = 4677;
	static final int INVENTORY_TAB_BINDING = 4678;
	static final int EQUIPMENT_TAB_BINDING = 4679;
	static final int PRAYER_TAB_BINDING = 4680;
	static final int MAGIC_TAB_BINDING = 4682;
	static final int FRIENDS_TAB_BINDING = 4684;
	static final int ACCOUNT_MANAGEMENT_TAB_BINDING = 6517;
	static final int LOGOUT_BINDING = 4689;
	static final int SETTINGS_TAB_BINDING = 4686;
	static final int EMOTE_TAB_BINDING = 4687;
	static final int CHAT_CHANNEL_TAB_BINDING = 4683;
	static final int MUSIC_PLAYER_TAB_BINDING = 4688;

	private static final Set<Integer> VARBITS = ImmutableSet.of(
		COMBAT_TAB_BINDING, SKILLS_TAB_BINDING, QUESTS_TAB_BINDING,
		INVENTORY_TAB_BINDING, EQUIPMENT_TAB_BINDING, PRAYER_TAB_BINDING,
		MAGIC_TAB_BINDING, FRIENDS_TAB_BINDING, ACCOUNT_MANAGEMENT_TAB_BINDING,
		LOGOUT_BINDING, SETTINGS_TAB_BINDING, EMOTE_TAB_BINDING,
		CHAT_CHANNEL_TAB_BINDING, MUSIC_PLAYER_TAB_BINDING
	);

	@Inject
	private Client client;

	@Inject
	private GlobalFKeysConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private GlobalFKeysKeyListener inputListener;

	@Inject
	private ConfigManager configManager;

	@Getter(AccessLevel.PACKAGE)
	private final Map<Integer, FKey> fkeyVarbitToKey = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(inputListener);
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.registerKeyListener(inputListener);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		VARBITS.forEach(varbit ->
		{
			final int varbitVal = client.getVarbitValue(varbit);
			log.debug("Storing FKey value {} for varbit {}", varbitVal, varbit);
			fkeyVarbitToKey.put(varbit, FKey.VARBIT_TO_FKEY.get(varbitVal));
		});
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged configChanged)
	{
		// If a user sets a keybinding, we need to check every other config
		// and set them to None if they are the same key
		// TODO: Revist if panel refresh ever gets supported
		if (configChanged.getGroup().equals(GlobalFKeysConfig.CONFIG_GROUP_NAME))
		{
			log.debug("Checking for mutual exclusivity on config changed: {}", configChanged);
			final String fkeyValue = configChanged.getNewValue();
			for (String key : configManager.getConfigurationKeys(GlobalFKeysConfig.CONFIG_GROUP_NAME))
			{
				final String rawKey = key.split("\\.", 2)[1];
				if (rawKey.equals(configChanged.getKey()))
				{
					continue;
				}

				final String configValue = configManager.getConfiguration(GlobalFKeysConfig.CONFIG_GROUP_NAME, rawKey);
				if (fkeyValue.equals(configValue))
				{
					// Set value back to None
					configManager.setConfiguration(GlobalFKeysConfig.CONFIG_GROUP_NAME, rawKey, FKey.NONE);
					log.debug("Setting config for {} to NONE", rawKey);
				}
			}
		}
	}

	@Provides
	GlobalFKeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GlobalFKeysConfig.class);
	}
}
