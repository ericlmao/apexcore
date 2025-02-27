package games.negative.apexcore.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;

import java.io.IOException;

public class SoundTypeAdapter extends TypeAdapter<Sound> {
    @Override
    public void write(JsonWriter out, Sound value) throws IOException {
        NamespacedKey key = Registry.SOUNDS.getKey(value);
        if (key == null) {
            out.nullValue();
            return;
        }

        out.value(key.getNamespace() + ":" + key.getKey());
    }

    @Override
    public Sound read(JsonReader in) throws IOException {
        String value = in.nextString();
        String[] parts = value.split(":");

        if (parts.length != 2) return null;

        NamespacedKey key = new NamespacedKey(parts[0], parts[1]);
        return Registry.SOUNDS.get(key);
    }
}
