package CS3500.model;

import CS3500.model.ProgrammaticImages.IProgramImage;
import CS3500.model.fileFormat.IFileFormat;
import CS3500.model.operation.IOperation;

/**
 * TODO: JavaDoc comments
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