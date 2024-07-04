package mc.mythofycommands.commands.perm;

import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

/**
 * The type Perm command.
 */
@CommandMeta(description = "Permissions command", rank = Rank.ADMIN)
public class PermissionCommand extends CoreCommand {

    /**
     * Instantiates a new Perm command.
     */
    public PermissionCommand() {
        super("perm");
        registerSubCommand(new PlayerCommand());
        registerSubCommand(new ListCommand());
    }

    @Override
    protected boolean isUsingSubCommandsOnly() {
        return true;
    }
}
