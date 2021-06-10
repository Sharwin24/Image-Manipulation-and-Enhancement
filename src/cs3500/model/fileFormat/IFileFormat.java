package cs3500.model.fileFormat;

import cs3500.model.image.IImage;

/**
 * : JavaDocs
 *
 * @param <IMAGE_REPRESENTATION>
 */
public interface IFileFormat<IMAGE_REPRESENTATION> {

  /**
   * Todo:
   * @param relativePath
   * @return
   */
  IMAGE_REPRESENTATION importImage(String relativePath);

  /**
   * Todo
   * @param relativePath
   * @param image
   */
  void exportImage(String relativePath, IImage image);
}