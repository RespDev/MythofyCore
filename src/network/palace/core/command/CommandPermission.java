package network.palace.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mc.mythofy.mythofycommands.rank.Rank;

/**
 * The interface Command permission.
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermission {
    /**
     * Rank rank.
     *
     * @return the rank
     */
    Rank rank();
}
