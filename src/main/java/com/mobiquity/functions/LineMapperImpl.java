package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Line mapping implementation class. Maps delivery items from
 * a line in a file into types {@link DeliveryPackage} for
 * further processing downstream.
 *
 * @author thato
 */
public class LineMapperImpl implements LineProcessor<String, LineImpl> {

    private final Logger logger = LogManager.getLogger(LineMapperImpl.class);

    /**
     * Maps delivery items from a line in a file into types {@link DeliveryPackage}
     * @param input Line in file
     * @return a {@link LineImpl} with {@link DeliveryPackage} for each comma separated item in the line
     */
    @Override
    public LineImpl apply(String input) {
        List<DeliveryPackage> deliveryPackages = Arrays.stream(StringUtils.substringsBetween(getLineDeliveryItems(input), "(", ")").clone())
                .sequential()
                .map(p -> {
                    String[] packageSpecArray = p.split(",");
                    return DeliveryPackage.builder()
                            .index(Integer.parseInt(packageSpecArray[0].trim()))
                            .weight(Double.parseDouble(packageSpecArray[1]))
                            .cost(Double.parseDouble(packageSpecArray[2].substring(1))).build();
                })
                .limit(15)
                .collect(Collectors.toList());

        logger.info("Mapped {} packages", deliveryPackages.size());
        return LineImpl.builder()
                .maxWeight(getLineMaxWeight(input))
                .packages(deliveryPackages).build();
    }
}
