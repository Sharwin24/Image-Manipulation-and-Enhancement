package cs3500.model;

/**
 * A mock model to test inputs from the controller.
 */
public interface IMockMultiLayerModel extends IMultiLayerModel {

  /**
   * Returns a log that has kept track of I/O interactions formthe controller.
   *
   * @return a string consisting of all of hte I/O interactions between this mock model and the
   *         controller
   */
  String getLog();
}
