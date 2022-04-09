package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Line processing implementation class. Maps delivery items from
 * a line in a file into types {@link DeliveryPackage}, and determines
 * qualifying packages that can be delivered.
 *
 * @author thato
 */
public class LineProcessorImpl implements Function<LineImpl, LineImpl> {

    private final Logger logger = LogManager.getLogger(LineProcessorImpl.class);

    /**
     * Maximum package weight
     * @return int value representing maximum weight possible
     */
    private double getMaxPackageWeight() {
        return 100;
    }

    /**
     * Item maximum weight and cost
     * @return Integer value representing item weight and cost
     */
    private double getItemMaxWeightAndCost() {
        return 100;
    }

    /**
     * Maps delivery items from a line in a file into types {@link DeliveryPackage}
     * @param source {@link LineImpl} mapped from source file
     * @return a {@link LineImpl} with {@link DeliveryPackage} for each comma separated
     *           item in the line that qualified for delivery
     */
    @Override
    public LineImpl apply(LineImpl source) {

        // Qualifying package's indexes stored here
        List<String> _indexes = new ArrayList<>();

        // Track total packages weight
        double totalWeight = 0;

        int count = 0;
        while (totalWeight < source.getMaxWeight() && count < source.getPackages().size()) {
            DeliveryPackage p = source.getPackages().get(count);
            if ((p.getWeight() + totalWeight) <= source.getMaxWeight()) {
                totalWeight += p.getWeight();
                // Ensure weight is less that the overall weight
                if (totalWeight < getMaxPackageWeight()) {
                    _indexes.add(String.valueOf(p.getIndex()));
                }
            }
            count++;
        }

        logger.info("Processed {} packages for delivery", _indexes.size());
        return LineImpl.builder()
                .maxWeight(source.getMaxWeight())
                .packages(source.getPackages())
                .result(_indexes.size() > 0 ? String.join(",", _indexes) : "-")
                .build();
    }
}
