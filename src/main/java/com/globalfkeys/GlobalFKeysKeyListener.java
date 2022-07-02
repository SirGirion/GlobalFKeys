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
 */package com.globalfkeys;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.inject.Inject;
import net.runelite.client.config.Keybind;
import net.runelite.client.input.KeyListener;

public class GlobalFKeysKeyListener implements KeyListener
{
	@Inject
	private GlobalFKeysPlugin plugin;
	@Inject
	private GlobalFKeysConfig config;

    private final Map<Integer, Integer> modified = new HashMap<>();
    private final Set<Character> blockedChars = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e)
    {
        char keyChar = e.getKeyChar();
        if (keyChar != KeyEvent.CHAR_UNDEFINED && blockedChars.contains(keyChar))
        {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int mappedKeyCode = KeyEvent.VK_UNDEFINED;
		final int keyCode = e.getKeyCode();

		// Don't remap escapes when modal is open or world map is open
		if (keyCode == KeyEvent.VK_ESCAPE && (plugin.isShouldNotRemapEscape() || plugin.isWorldMapOpen()))
		{
			return;
		}

		if (!plugin.chatboxFocused())
		{
			return;
		}

		if (!plugin.isDialogOpen())
		{
			if (config.combatTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.COMBAT_TAB_BINDING).getKeyEvent();
			}
			else if (config.skillsTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.SKILLS_TAB_BINDING).getKeyEvent();
			}
			else if (config.questsTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.QUESTS_TAB_BINDING).getKeyEvent();
			}
			else if (config.inventoryTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.INVENTORY_TAB_BINDING).getKeyEvent();
			}
			else if (config.equipmentTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.EQUIPMENT_TAB_BINDING).getKeyEvent();
			}
			else if (config.prayerTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.PRAYER_TAB_BINDING).getKeyEvent();
			}
			else if (config.magicTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.MAGIC_TAB_BINDING).getKeyEvent();
			}
			else if (config.friendsTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.FRIENDS_TAB_BINDING).getKeyEvent();
			}
			else if (config.accountManagementTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.ACCOUNT_MANAGEMENT_TAB_BINDING).getKeyEvent();
			}
			else if (config.logoutTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.LOGOUT_BINDING).getKeyEvent();
			}
			else if (config.settingsTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.SETTINGS_TAB_BINDING).getKeyEvent();
			}
			else if (config.emotesTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.EMOTE_TAB_BINDING).getKeyEvent();
			}
			else if (config.chatChannelTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.CHAT_CHANNEL_TAB_BINDING).getKeyEvent();
			}
			else if (config.musicPlayerTab().matches(e))
			{
				mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.MUSIC_PLAYER_TAB_BINDING).getKeyEvent();
			}
		}

        if (mappedKeyCode != KeyEvent.VK_UNDEFINED && mappedKeyCode != keyCode)
		{
			// If options dialog is open, and we remapped to Fkey, send actual keycode instead.
			if (plugin.isDialogOpen() && plugin.isOptionsDialogOpen())
			{
				mappedKeyCode = keyCode;
			}
            final char keyChar = e.getKeyChar();
            modified.put(keyCode, mappedKeyCode);
            e.setKeyCode(mappedKeyCode);
            // arrow keys and fkeys do not have a character
            e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
            if (keyChar != KeyEvent.CHAR_UNDEFINED)
            {
                // If this key event has a valid key char then a key typed event may be received next,
                // we must block it
                blockedChars.add(keyChar);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        final int keyCode = e.getKeyCode();
        final char keyChar = e.getKeyChar();

        if (keyChar != KeyEvent.CHAR_UNDEFINED)
        {
            blockedChars.remove(keyChar);
        }

        final Integer mappedKeyCode = modified.remove(keyCode);
        if (mappedKeyCode != null)
        {
            e.setKeyCode(mappedKeyCode);
            e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
        }
    }
}
