package com.mobiquity.functions;

import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineSorterImplTest {

    @Test
    void should_sort_packages_successfully() {

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

        List<Comparator<DeliveryPackage>> strategies = List.of(
                Comparator.comparing(DeliveryPackage::getWeight),
                Comparator.comparing(DeliveryPackage::getCost, Comparator.reverseOrder())
        );

        LineSorterImpl lineSorter = new LineSorterImpl();
        LineImpl line = lineSorter.apply(
                LineImpl.builder().packages(deliveryPackages).build(),
                strategies
        );

        assertEquals(9, line.getPackages().get(0).getIndex());
        assertEquals(6, line.getPackages().get(1).getIndex());
        assertEquals(2, line.getPackages().get(2).getIndex());
        assertEquals(7, line.getPackages().get(3).getIndex());
        assertEquals(4, line.getPackages().get(4).getIndex());
        assertEquals(5, line.getPackages().get(5).getIndex());
        assertEquals(8, line.getPackages().get(6).getIndex());
        assertEquals(1, line.getPackages().get(7).getIndex());
        assertEquals(3, line.getPackages().get(8).getIndex());

    }
}