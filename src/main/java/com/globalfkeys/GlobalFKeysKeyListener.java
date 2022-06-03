package com.globalfkeys;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
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

		if (config.combatTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.COMBAT_TAB_BINDING).getKeyEvent();
		}
		else if (config.skillsTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.SKILLS_TAB_BINDING).getKeyEvent();
		}
		else if (config.questsTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.QUESTS_TAB_BINDING).getKeyEvent();
		}
		else if (config.inventoryTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.INVENTORY_TAB_BINDING).getKeyEvent();
		}
		else if (config.equipmentTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.EQUIPMENT_TAB_BINDING).getKeyEvent();
		}
		else if (config.prayerTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.PRAYER_TAB_BINDING).getKeyEvent();
		}
		else if (config.magicTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.MAGIC_TAB_BINDING).getKeyEvent();
		}
		else if (config.friendsTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.FRIENDS_TAB_BINDING).getKeyEvent();
		}
		else if (config.accountManagementTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.ACCOUNT_MANAGEMENT_TAB_BINDING).getKeyEvent();
		}
		else if (config.logoutTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.LOGOUT_BINDING).getKeyEvent();
		}
		else if (config.settingsTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.SETTINGS_TAB_BINDING).getKeyEvent();
		}
		else if (config.emotesTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.EMOTE_TAB_BINDING).getKeyEvent();
		}
		else if (config.chatChannelTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.CHAT_CHANNEL_TAB_BINDING).getKeyEvent();
		}
		else if (config.musicPlayerTab().getKeyEvent() == keyCode)
		{
			mappedKeyCode = plugin.getFkeyVarbitToKey().get(GlobalFKeysPlugin.MUSIC_PLAYER_TAB_BINDING).getKeyEvent();
		}

        if (mappedKeyCode != KeyEvent.VK_UNDEFINED && mappedKeyCode != keyCode)
        {
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
