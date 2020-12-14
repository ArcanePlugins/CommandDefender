# CommandDefender Changelog

### v1.0.0-ALPHA
* Initial version.

### v1.0.1-ALPHA
* Changes to permission checking.
* Now checking `commanddefender.bypass.*` permission, was mistakenly left out last version.

### v1.0.2-ALPHA
* Fixed operators not being able to access commands

### v1.0.3-ALPHA
* Fixed placeholders in main command message not being replaced.

### v1.0.4-ALPHA
* Fixed command suggestions not being updated.

### v1.0.5-ALPHA
* Removed the command suggestion manager.

### v1.0.6
* Fixed updater resource id.
* No longer in alpha stage.

### v1.1.0
* **You no longer need MicroLib in your plugins folder to run this plugin.** It is embedded inside the .jar file now. **You must uninstall MicroLib v1 from your plugins folder, or errors will occur.**
* Updated MicroLib dependency to v2.0.0.
* Removed the startup banner from your console logs :)
* Few code improvements revolving around the dependency update.

### v1.2.0
* Now filters 1.13+'s command suggestion system.
  * Removes blocked commands from the suggestions list.
  * If enabled, removes commands with colons from the suggestions list (e.g. `/bukkit:help`)
  
### v1.2.1
* Added `api-version` to inbuilt `plugin.yml`, which fixes a startup warning.
* Fixed individual bypass permissions requiring `/` in command labels.
* Fixed case sensitivity.
* Updated dependencies (MicroLib 2.0.0 -> 2.1.1, Spigot API 1.16.3 -> 1.16.4).
* Fixed update checker.

### v1.2.2
* Note: Not Tested!
* Now utilising QuickTimer from MicroLib
* Now utilising YamlConfigFile from MicroLib

### v1.2.3
* Fixed startup error, thanks to person in discussions section for reporting it

### v1.2.4
Notes:
* This update was **not tested**.
* A big thank you to PoZiomeK and Hugo5551 for their excellent communication and assistance for these issues :)

Changelog:
* Attempted to warn user that the update checker doesn't work in <1.11 servers rather than spit errors
* Attempted to suppress error that occurs with <1.13 servers when running the reload subcommand.