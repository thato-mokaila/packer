package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Line sorting implementation class. Sorts the packages within
 * a single line in a file.
 *
 * @author thato
 */
public class LineSorterImpl implements Function<LineImpl, LineImpl> {

    /**
     * Iterates through all mapped delivery packages and from the provided
     * line and sorts the items for processing.
     *
     * To efficiently process packages by lowest weight and highest cost, we
     * sort the list by weight ASC and then sort it again by cost DESC. Sorting
     * by weight ASC is necessary to allow the selection of the first lesser
     * item should the cost of both items be the same.
     * @param source Line in file
     */
    @Override
    public LineImpl apply(LineImpl source) {
        List<DeliveryPackage> sorted = source.getPackages().stream()
                .sorted(Comparator.comparing(DeliveryPackage::getWeight))
                .sorted(Comparator.comparing(DeliveryPackage::getCost, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return LineImpl.builder().maxWeight(source.getMaxWeight()).packages(sorted).build();
    }

}
