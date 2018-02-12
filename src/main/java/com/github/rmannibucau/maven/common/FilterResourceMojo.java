package com.github.rmannibucau.maven.common;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

import java.io.File;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(defaultPhase = INITIALIZE, name = "filter")
public class FilterResourceMojo extends BaseFilter {

    @Parameter(required = true, property = "rmannibucau.common.filter.from")
    private File from;

    @Parameter(required = true, property = "rmannibucau.common.filter.to")
    private File to;

    @Override
    public void execute() {
        if (!from.exists()) {
            throw new IllegalArgumentException(from + " doesn't exist");
        }
        doFilter(from, to);
    }
}
