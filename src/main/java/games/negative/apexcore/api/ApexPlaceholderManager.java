package games.negative.apexcore.api;

import games.negative.apexcore.api.model.ApexPlaceholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents the placeholder manager for ApexCore.
 */
public interface ApexPlaceholderManager {

    /**
     * Registers a placeholder with the given identifier.
     * @param identifier The identifier of the placeholder
     * @param placeholder The placeholder to register
     */
    void registerPlaceholder(@NotNull String identifier, @NotNull ApexPlaceholder placeholder);

    /**
     * Unregisters a placeholder with the given identifier.
     * @param identifier The identifier of the placeholder
     */
    void unregisterPlaceholder(@NotNull String identifier);

    /**
     * Checks if a placeholder with the given identifier is registered.
     * @param identifier The identifier of the placeholder
     * @return True if the placeholder is registered, false otherwise
     */
    boolean isPlaceholderRegistered(@NotNull String identifier);

    /**
     * Gets the placeholder with the given identifier.
     * @param identifier The identifier of the placeholder
     * @return The placeholder with the given identifier
     */
    @Nullable
    ApexPlaceholder getPlaceholder(@NotNull String identifier);

    /**
     * Gets all registered placeholders.
     * @return All registered placeholders
     */
    @NotNull
    Collection<ApexPlaceholder> getRegisteredPlaceholders();
}
