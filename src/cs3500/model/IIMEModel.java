package cs3500.model;

import cs3500.model.programmaticImages.IProgramImage;
import cs3500.model.fileFormat.IFileFormat;
import cs3500.model.operation.IOperation;

/**
 * : JavaDoc comments
 *
 * @param <Z>
 */
public interface IIMEModel<Z> {

  /**
   *
   * @throws IllegalArgumentException
   */
  void applyOperations(IOperation... operations) throws IllegalArgumentException;

  void importImage(IFileFormat format, String fileName)
      throws IllegalArgumentException;

  void exportImage(IFileFormat format, String fileName)
      throws IllegalArgumentException;

  void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx)
  throws IllegalArgumentException;
}