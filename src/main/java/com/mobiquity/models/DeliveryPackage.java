package com.mobiquity.models;

import lombok.Builder;
import lombok.Data;

/**
 * Representation of a delivery package
 */
@Builder
@Data
public class DeliveryPackage {
    /** Package index value */
    private int index;
    /** Package weight */
    private Double weight;
    /** Package cost */
    private Double cost;

}
