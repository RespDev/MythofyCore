package network.palace.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mc.mythofy.mythofycommands.rank.Rank;

/**
 * The interface Command meta.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    /**
     * Description string.
     *
     * @return the string
     */
    String description() default "";

    /**
     * Aliases string [ ].
     *
     * @return the string [ ]
     */
    String[] aliases() default {};

    /**
     * Usage string.
     *
     * @return the string
     */
    String usage() default "";

    /**
     * Rank rank.
     *
     * @return the rank
     */
    Rank rank() default Rank.MEMBER;

}
