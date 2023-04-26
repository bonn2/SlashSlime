package net.bonn2.slashslime;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class SlashSlimeLoader implements PluginLoader {

    // Dynamically load libraries
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        // Paper repo used for bStats
        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public/").build());
        // jitpack repo used for ConfigLib
        resolver.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io/").build());
        // Add dependencies
        resolver.addDependency(new Dependency(new DefaultArtifact("org.bstats:bstats-bukkit:3.0.2"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.github.Exlll.ConfigLib:configlib-paper:4.2.0"), null));
        // Add dependencies to classpath
        classpathBuilder.addLibrary(resolver);
    }
}
