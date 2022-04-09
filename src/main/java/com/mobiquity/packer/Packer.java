package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.functions.*;
import com.mobiquity.models.DeliveryPackage;
import com.mobiquity.models.LineImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Packer class for processing delivery packages
 *
 * @author thato
 */
public class Packer {

  private static final Logger logger = LogManager.getLogger(Packer.class);

  /**
   * Private constructor. No instances please.
   */
  private Packer() {
  }

  /**
   * Public API to read an input file containing delivery packages. For
   * each line in the file the method determines which package is to be
   * delivered. The basic requirement is to prioritise packages by less
   * weight and highest cost.
   *
   * @param filePath Source file
   * @return String output of the file processing
   */
  public static String pack(String filePath) {
    Packer.validatePath(filePath);
    try {
      return Packer.processFile(filePath);
    } catch (Exception e) {
      logger.error("Failed to process input file {}", filePath, e);
      throw new APIException(e.getMessage(), e);
    }
  }

  /**
   * Processes the given files using different {@link FunctionalInterface}s.
   *
   * @param filePath Source file
   * @return Output of the file processing
   */
  private static String processFile(String filePath) {
    try  {
       List<LineImpl> results = Files.readAllLines(Paths.get(filePath))
               .stream()
               .map(Packer::mapDeliveryItems)
               .map(Packer::sortPackages)
               .map(Packer::processQualifyingPackages)
               .collect(Collectors.toList());
       return results.stream()
               .map(Packer::getResults)
               .collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new APIException(String.format("Provided input file path does not exists %s", e.getMessage()));
    }
  }

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
  private static LineImpl sortPackages(LineImpl source) {
    return new LineSorterImpl().apply(source);
  }

  /**
   * Maps delivery items from a line in a file into types {@link DeliveryPackage}
   * @param source {@link LineImpl} mapped from source file
   * @return a {@link LineImpl} with {@link DeliveryPackage} for each comma separated
   *           item in the line that qualified for delivery
   */
  private static LineImpl processQualifyingPackages(LineImpl source) {
    return new LineProcessorImpl().apply(source);
  }

  /**
   * Returns a calculated list of packages to be shipped in a comma separated
   * line. A '-' depicts non qualifying packages for line being processed.
   *
   * @param source Line implementation containing source and
   *             possibly mapped packages
   * @return String with calculated results
   */
  private static String getResults(LineImpl source) {
    return new LineWriterImpl().apply(source);
  }

  /**
   * Maps delivery items from a line in a file into types {@link DeliveryPackage}
   * @param lineItem Line in file
   * @return a {@link LineImpl} with {@link DeliveryPackage} for each comma separated item in the line
   */
  private static LineImpl mapDeliveryItems(String lineItem) {
    return new LineMapperImpl().apply(lineItem);
  }

  /**
   * Validates if provided file path exists. Fail otherwise
   * @param filePath Path to check existence for
   * @throws APIException Exception thrown for invalid path
   */
  private static void validatePath(String filePath) {

    if (!Files.exists(Paths.get(filePath))) {
      throw new APIException(String.format("Provided input file resource does not exists %s", filePath));
    }
  }

}
