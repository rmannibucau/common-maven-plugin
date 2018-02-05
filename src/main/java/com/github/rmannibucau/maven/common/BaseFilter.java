package com.github.rmannibucau.maven.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.utils.io.FileUtils;

public abstract class BaseFilter extends AbstractMojo {

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    protected MavenSession session;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    private volatile StrSubstitutor strSubstitutor;

    protected void doFilter(final File from, final File to) {
        if (strSubstitutor == null) {
            synchronized (this) {
                if (strSubstitutor == null) {
                    strSubstitutor = new StrSubstitutor(new HashMap<String, Object>() {

                        {
                            putAll(Map.class.cast(project.getProperties()));
                            putAll(Map.class.cast(session.getSystemProperties()));
                            putAll(Map.class.cast(session.getUserProperties()));
                        }
                    });
                }
            }
        }
        try {
            final String content = FileUtils.fileRead(from);
            FileUtils.forceMkdir(to.getParentFile());
            final String filtered = strSubstitutor.replace(content);
            try (final Writer writer = new BufferedWriter(new FileWriter(to))) {
                writer.write(filtered);
            }
            getLog().info("Created " + to.getAbsolutePath());
        } catch (final IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
