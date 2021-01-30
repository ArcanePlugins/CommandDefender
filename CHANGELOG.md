# CommandDefender Changelog

## v1.0.0-ALPHA
* Initial version.



## v1.0.1-ALPHA
* Changes to permission checking.
* Now checking `commanddefender.bypass.*` permission, was mistakenly left out last version.



## v1.0.2-ALPHA
* Fixed operators not being able to access commands



## v1.0.3-ALPHA
* Fixed placeholders in main command message not being replaced.



## v1.0.4-ALPHA
* Fixed command suggestions not being updated.



## v1.0.5-ALPHA
* Removed the command suggestion manager.



## v1.0.6
* Fixed updater resource id.
* No longer in alpha stage.



## v1.1.0
* **You no longer need MicroLib in your plugins folder to run this plugin.** It is embedded inside the .jar file now. **You must uninstall MicroLib v1 from your plugins folder, or errors will occur.**
* Updated MicroLib dependency to v2.0.0.
* Removed the startup banner from your console logs :)
* Few code improvements revolving around the dependency update.



## v1.2.0
* Now filters 1.13+'s command suggestion system.
  * Removes blocked commands from the suggestions list.
  * If enabled, removes commands with colons from the suggestions list (e.g. `/bukkit:help`)
  
  
  
## v1.2.1
* Added `api-version` to inbuilt `plugin.yml`, which fixes a startup warning.
* Fixed individual bypass permissions requiring `/` in command labels.
* Fixed case sensitivity.
* Updated dependencies (MicroLib 2.0.0 -> 2.1.1, Spigot API 1.16.3 -> 1.16.4).
* Fixed update checker.



## v1.2.2
* Note: Not Tested!
* Now utilising QuickTimer from MicroLib
* Now utilising YamlConfigFile from MicroLib



## v1.2.3
* Fixed startup error, thanks to person in discussions section for reporting it



## v1.2.4
Notes:
* This update was **not tested**.
* A big thank you to PoZiomeK and Hugo5551 for their excellent communication and assistance for these issues :)

Changelog:
* Attempted to warn user that the update checker doesn't work in <1.11 servers rather than spit errors
* Attempted to suppress error that occurs with <1.13 servers when running the reload subcommand.



## v1.2.5
### Notes:
* This update was **not tested**.

### Changelog:
* Adapted the same code affected in v1.2.4.



## v1.2.6
### Notes:
* This update was **not tested**.
* No file changes.

### Changelog:
* Third attempt at getting the plugin working on <1.13 servers
* Changes to the code here and there



## v1.2.7
### Notes:
* This update was not tested.
* No file changes this update.

### Changelog:
* Fixed reload subcommand not working on <1.13 servers.
* Minor code changes.



## v1.2.8
### Notes:
* This update was not tested.
* No file changes this update.

### Changelog:
* Hopefully fixed the update checker throwing an error on <1.11 servers, instead it should show a warning to disable it.
* Very minor change to a message in messages.yml, no reset necessary.

***

## v1.3.0
### Notes:
* **WARNING** This update was not tested thoroughly. If you have security concerns then I would advise you run your own testing or stick with the previous version (unsupported as of now). I apologise for this as I am currently very limited on time.
* No file changes this update. The settings file has new functionality although no changes are needed.

### Changelog:
**Blockable Command Arguments!**
* Perhaps the most requested feature of this plugin is the ability to block certain arguments of commands (e.g. 'base' in '/home base').
* This system has been designed to fit as many use cases as possible. Check out the Wiki to see its possibilities!
* This system is a little tricky to use so please [contact me on my Discord](https://discord.io/arcaneplugins) if you have any queries or issues.

**Bug Fixes**
* Fixed the command message being colorized in the deny message.

***

## v1.4.0
### Notes:
* This update was not tested.
* A recommended yet optional file change has occured to settings.yml although the same file version remains. I understand people have already invested lots of time into their configs so I don't want to make you all reset it :)

### Changelog:
**Overrideable Commands**
* You can now force-block commands such as `/island* *` whilst in whitelist mode.
* Thanks to keith for reporting this lacking feature to me. :)

***

