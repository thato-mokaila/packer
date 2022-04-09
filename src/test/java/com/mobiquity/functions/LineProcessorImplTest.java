package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineProcessorImplTest {

    @Test
    void should_return_only_qualifying_packages() {

        List<DeliveryPackage> deliveryPackages = new ArrayList<>(){{
            add(DeliveryPackage.builder().index(1).cost(29.0).weight(85.31).build());
            add(DeliveryPackage.builder().index(2).cost(74.0).weight(14.55).build());
            add(DeliveryPackage.builder().index(3).cost(16.0).weight(3.98).build());
            add(DeliveryPackage.builder().index(4).cost(55.0).weight(26.24).build());
            add(DeliveryPackage.builder().index(5).cost(52.0).weight(63.69).build());
            add(DeliveryPackage.builder().index(6).cost(75.0).weight(76.25).build());
            add(DeliveryPackage.builder().index(7).cost(74.0).weight(60.02).build());
            add(DeliveryPackage.builder().index(8).cost(35.0).weight(93.18).build());
            add(DeliveryPackage.builder().index(9).cost(78.0).weight(89.95).build());
        }};

        // Custom sort strategies
        List<Comparator<DeliveryPackage>> strategies = List.of(
                Comparator.comparing(DeliveryPackage::getWeight),
                Comparator.comparing(DeliveryPackage::getCost, Comparator.reverseOrder())
        );

        LineImpl sorted = new LineSorterImpl().apply(
                LineImpl.builder().maxWeight(75.0).packages(deliveryPackages).build(), strategies
        );
        LineImpl results = new LineProcessorImpl().apply(sorted);

        assertEquals("2,7", results.getResult());
    }
}