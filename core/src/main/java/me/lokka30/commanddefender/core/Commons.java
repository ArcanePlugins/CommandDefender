package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.action.type.PlaySound;
import me.lokka30.commanddefender.core.filter.set.action.type.SendMessage;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.type.FromPlugins;
import me.lokka30.commanddefender.core.filter.set.condition.type.HasColonInFirstArg;
import me.lokka30.commanddefender.core.filter.set.condition.type.List;
import me.lokka30.commanddefender.core.filter.set.condition.type.RegexList;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.ActionPredicateOverride;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.BypassPermission;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.Context;

import java.util.HashSet;
import java.util.Set;

public class Commons {

    private static Core core;
    
    public static Core core() { return core; }
    
    public static void setCore(final Core core) {
        assert Commons.core() == null;
        Commons.core = core;
    }

    public static final HashSet<ConditionHandler> conditionHandlers = new HashSet<>(Set.of(
            new FromPlugins(),
            new HasColonInFirstArg(),
            new List(),
            new RegexList()
    ));

    public static final HashSet<ActionHandler> actionHandlers = new HashSet<>(Set.of(
            new PlaySound(),
            new SendMessage()
    ));

    public static final HashSet<OptionHandler> optionHandlers = new HashSet<>(Set.of(
            /* Post-process */
            new ActionPredicateOverride(),

            /* Pre-process */
            new BypassPermission(),
            new Context()
    ));

    public static final String DEFAULT_PREFIX = "&b&lCommandDefender:&7 ";

    public static final String DEBUG_LOGGING_PREFIX = "&8[&3Debug &8// &3%s&8]:&7 ";

}
