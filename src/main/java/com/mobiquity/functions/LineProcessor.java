package com.mobiquity.functions;

import com.mobiquity.exception.APIException;
import com.mobiquity.models.DeliveryPackage;

import java.util.function.Function;

/**
 * Line processing interface. Maps delivery items from
 * a line in a file into types {@link DeliveryPackage}, and determines
 * qualifying packages that can be delivered.
 *
 * @author thato
 */
@FunctionalInterface
public interface LineProcessor<T, R> extends Function<T, R> {

    /**
     * Extracts the delivery maximum weight from a file line (left side of the colon).
     * @param fileLine Line in file
     * @return returns the line (with all delivery items for that line) as a string
     */
    default double getLineMaxWeight(String fileLine) {
        if (!(fileLine != null && fileLine.length() > 2 && fileLine.contains(":")))
            throw new APIException(String.format("Invalid line read from file %s", fileLine));
        return Double.parseDouble(fileLine.split(":")[0].trim());
    }

    /**
     * Reads the delivery packages from a file line (right side of the colon).
     * @param fileLine Line in file
     * @return returns the line (with all delivery items for that line) as a string
     */
    default String getLineDeliveryItems(String fileLine) {

        assert fileLine != null && fileLine.length() > 2 && fileLine.contains(":");
        return fileLine.split(":")[1].trim();
    }

}
