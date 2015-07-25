package com.me4502.modularframework.module.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.me4502.modularframework.module.ModuleWrapper;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ModuleInjector extends AbstractModule {

    public final ModuleWrapper moduleWrapper;

    public ModuleInjector(ModuleWrapper moduleWrapper) {
        this.moduleWrapper = moduleWrapper;
    }

    @Override
    protected void configure() {
        bind(ConfigurationNode.class).annotatedWith(ModuleConfiguration.class).toProvider(ConfigurationProvider.class);
    }

    private class ConfigurationProvider implements Provider<ConfigurationNode> {

        @Override
        public ConfigurationNode get() {
            ConfigurationNode configNode = null;

            if(moduleWrapper.getOwner().getConfigurationDirectory() != null) {
                try {
                    File config = new File(moduleWrapper.getOwner().getConfigurationDirectory(), moduleWrapper.getAnnotation().moduleName());
                    ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setFile(config).build();
                    configNode = configLoader.load();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return configNode;
        }
    }
}