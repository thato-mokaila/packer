package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Line sorting implementation class. Sorts the packages within
 * a single line in a file.
 *
 * @author thato
 */
public class LineSorterImpl implements BiFunction<LineImpl, List<Comparator<DeliveryPackage>>, LineImpl> {

    /**
     * Iterates through all mapped delivery packages and from the provided
     * line and sorts the items for processing.
     *
     * To efficiently process packages by lowest weight and highest cost, we
     * sort the list by weight ASC and then sort it again by cost DESC. Sorting
     * by weight ASC is necessary to allow the selection of the first lesser
     * item should the cost of both items be the same.
     * @param source Line in file
     * @param strategies Sorting strategies
     */
    @Override
    public LineImpl apply(LineImpl source, List<Comparator<DeliveryPackage>> strategies) {
        List<DeliveryPackage> packages = source.getPackages();
        for (Comparator<DeliveryPackage> c : strategies) {
            packages.sort(c);
        }
        return LineImpl.builder().maxWeight(source.getMaxWeight()).packages(packages).build();
    }
}
