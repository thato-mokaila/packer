package com.mobiquity.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Representation of a each line in a file
 */
@Builder @Data
public class LineImpl {
    /** Holds the maximum weight for each line in the file */
    private Double maxWeight;
    /** Holds a list of packages for each line in the file */
    private List<DeliveryPackage> packages;
    /** Holds the calculated results as a line string */
    private String result;
}
