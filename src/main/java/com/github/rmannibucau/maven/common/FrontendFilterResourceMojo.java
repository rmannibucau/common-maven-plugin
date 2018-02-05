package com.github.rmannibucau.maven.common;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

import java.io.File;
import java.util.stream.Stream;

import org.apache.maven.plugins.annotations.Mojo;

@Mojo(defaultPhase = INITIALIZE, name = "frontend-filter")
public class FrontendFilterResourceMojo extends BaseFilter {

    @Override
    public void execute() {
        Stream.of(new File(project.getBasedir(), "src/main/frontend/package-template.json"),
                new File(project.getBasedir(), "package-template.json")).filter(File::exists)
                .forEach(template -> doFilter(template, new File(template, template.getName().replace("-template", ""))));
    }
}
