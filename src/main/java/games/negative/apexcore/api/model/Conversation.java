package games.negative.apexcore.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record Conversation(@NotNull UUID recipient, long updated) {
}
