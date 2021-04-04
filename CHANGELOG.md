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

## v1.4.1

### Notes:

* This update was tested.
* If you are running the subcommands system, remove any `*` characters suffixing any args you have listed. e.g.
  change `/command notch*` to `/command notch`.
* I apologise for the frequent updates, this should be the last for a while :)

### Changelog:

* Removed the necessity to have `*` after each argument in a command that you want to list. CommandDefender now only
  counts the last argument you have listed.
* Decent changes to the 'isListed' code making it run as intended.

***

## v2.0.0 (b22)

### Notes:

* **Significant changes were made to `settings.yml` and `messages.yml`. You MUST update these files, or you will be
  GUARANTEED to be slammed with the plugin almost completely malfunctioning.**
* This update was partly tested. I do not have enough time to fully test it. If you are sceptical about your commands'
  security, I would recommend you test this version before you deploy it.

### Changelog:

#### Now priority-list based! (lokka30)

* *Finally*, you can simultaneously allow and block commands by using priority-based lists!
* As suggested by @DozerCraft, you can also specify deny messages for each group if you wish to override the default
  one.
* This system has overhauled a lot of the code previously in CommandDefender, which is why

#### Multi-line messages! (lokka30)

* All messages now have multi-line support!

#### Force-block and force-allow permissions! (lokka30)

* For those who wanted to block and allow commands based on permissions, that's now possible!

#### And more...

* I am testing a new versioning system. I will use 'build' versioning whilst coding (hence b22), and 'x.x.x' versioning
  for releases to make it easy for server owners.
* Unfortunately I didn't write a changelog whilst developing this update, so a bunch of minor things were done to the
  plugin but I didn't note them down.

***

## v2.0.1-b23

### Notes:

* This update was **tested**.
* File changes: **none**

### Changelog:

* Fixed a bug where 'block-colons' also blocked colons in arguments of a command rather than only the actual command
  itself

***

## v2.1.0 b24

### Notes:

* This update was **not tested**.
* File changes: **none**.

### Changelog:

* Fixed tab complete index for CommandDefender command (stumper66)
* Updated MicroLib dep (lokka30)
  * Added support for 1.16+ hex colors!
  * Updated QuickTimer usage
* Improved return for tab complete for CommandDefender command where no tab completions are available. (stumper66)
* Attempted fix update checker possible error (lokka30)