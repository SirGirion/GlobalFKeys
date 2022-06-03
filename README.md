# Global F-Keys
This plugin allows you to set F-Keys at the RuneLite level rather than the account level. Helpful if you use multiple accounts and don't want to change the settings every time.

A current limitation of this plugin is it won't swallow non-mapped F-Keys. For example, if your account has F1 bound to inventory and you don't configure the plugin with something set to F1, pressing F1 will still be sent to the client.

If you set a config to a particular key and try to bind another tab to that same key, the most recent tab will take precedence. The config panel will not reflect this change until you restart the client, but underneath the hood it will work.

Eg: F1 bound to skills tab, then F1 bound to combat tab. F1 will toggle the combat tab, and skills tab will be reset to NONE
## Future work
- Enable remapping to arbitrary keys
- Re-fresh config panel to highlight mutual exlusive options*

*blocked pending core issue 14713