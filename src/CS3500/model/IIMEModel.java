package CS3500.model;

import CS3500.model.channel.IChannel;
import CS3500.model.fileFormat.IFileFormat;
import CS3500.model.image.IImage;
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

}