package games.negative.apexcore.task;

import com.google.common.collect.Lists;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.Conversation;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConversationExpireTask extends BukkitRunnable {

    private static final long CONVERSATION_EXPIRE_TIME = 60000L;

    private final ApexAPI api;

    public ConversationExpireTask(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void run() {
        Map<UUID, Conversation> conversations = api.conversations();

        List<UUID> toRemove = Lists.newArrayList();

        for (Map.Entry<UUID, Conversation> entry : conversations.entrySet()) {
            UUID uuid = entry.getKey();
            Conversation conversation = entry.getValue();

            if (Math.abs(System.currentTimeMillis() - conversation.updated()) >= CONVERSATION_EXPIRE_TIME) {
                toRemove.add(uuid);
            }
        }

        toRemove.forEach(api::removeConversation);
    }
}
