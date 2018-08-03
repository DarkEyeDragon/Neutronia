package net.hdt.neutronia.base.pulsar.config;

import net.hdt.neutronia.base.pulsar.pulse.PulseMeta;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Interface for config handlers.
 *
 * @author Arkan <arkan@drakon.io>
 */
@ParametersAreNonnullByDefault
public interface IConfiguration {

    /**
     * Perform any configuration loading required.
     */
    void load();

    /**
     * Gets whether the given module is enabled in the config.
     *
     * @param meta The pulse metadata.
     * @return Whether the module is enabled.
     */
    boolean isModuleEnabled(PulseMeta meta);

    /**
     * Flush configuration to disk/database/whatever.
     */
    void flush();

}
