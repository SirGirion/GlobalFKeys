/*
 * Copyright (c) 2022, SirGirion <seallproducts@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Abexlry <abexlry@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
import net.runelite.api.VarClientInt;
import net.runelite.api.WidgetNode;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetModalMode;
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
	// such that a value of 12 means that tab is bound to F12
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
	private boolean shouldNotRemapEscape;

	@Getter(AccessLevel.PACKAGE)
	private final Map<Integer, FKey> fkeyVarbitToKey = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(inputListener);
		configManager.getConfigurationKeys(GlobalFKeysConfig.CONFIG_GROUP_NAME)
			.forEach(key ->
			{
				// New config values end in -Key, unset old ones for people
				if (!key.endsWith("Key"))
				{
					final String rawKey = key.split("\\.", 2)[1];
					configManager.unsetConfiguration(GlobalFKeysConfig.CONFIG_GROUP_NAME, rawKey);
				}
			});
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(inputListener);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		VARBITS.forEach(varbit ->
		{
			final int varbitVal = client.getVarbitValue(varbit);
			final FKey existingValue = fkeyVarbitToKey.get(varbit);
			final FKey newValue = FKey.VARBIT_TO_FKEY.get(varbitVal);
			if (existingValue == null || existingValue != newValue)
			{
				fkeyVarbitToKey.put(varbit, newValue);
				log.debug("Storing FKey value {} for varbit {}", varbitVal, varbit);
			}
		});
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged configChanged)
	{
		// If a user sets a keybinding, we need to check every other config
		// and set them to None if they are the same key
		// TODO: Revisit if panel refresh ever gets supported
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

	@Subscribe
	private void onGameTick(GameTick gameTick)
	{
		boolean modalOpen = false;
		for (WidgetNode node : client.getComponentTable())
		{
			if (node.getModalMode() != WidgetModalMode.NON_MODAL)
			{
				modalOpen = true;
			}
		}
		shouldNotRemapEscape = modalOpen;
	}

	@Provides
	GlobalFKeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GlobalFKeysConfig.class);
	}
}
