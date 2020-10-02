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