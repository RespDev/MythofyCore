package network.palace.core.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import mc.mythofy.mythofycommands.MythofyCommands;

/**
 * The type Core event.
 */
public class CoreEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Call the event.
     */
    public void call() {
        MythofyCommands.callEvent(this);
    }
}
