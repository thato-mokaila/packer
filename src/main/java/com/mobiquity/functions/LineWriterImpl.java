package com.mobiquity.functions;

import com.mobiquity.models.LineImpl;

import java.util.function.Function;

/**
 * Line writer implementation class. Writes output results to String.
 *
 * @author thato
 */
public class LineWriterImpl implements Function<LineImpl, String> {

    /**
     * Returns a calculated list of packages to be shipped in a comma separated
     * line. A '-' depicts non qualifying packages for line being processed.
     *
     * @param source Line implementation containing source and
     *             possibly mapped packages
     * @return String with calculated results
     */
    @Override
    public String apply(LineImpl source) {
        return source.getResult();
    }
}
