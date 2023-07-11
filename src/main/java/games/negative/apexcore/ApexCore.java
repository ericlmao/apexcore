package games.negative.apexcore;

import games.negative.alumina.AluminaPlugin;
import games.negative.apexcore.core.Locale;

public final class ApexCore extends AluminaPlugin {

    @Override
    public void enable() {
        Locale.init(this);

        handleCommands();
    }

    @Override
    public void disable() {

    }

    private void handleCommands() {

    }
}
