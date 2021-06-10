package cs3500.model.fileFormat;

import cs3500.model.image.IImage;

/**
 * TODO: JavaDocs
 *
 * @param <IMAGE_REPRESENTATION>
 */
public interface IFileFormat<IMAGE_REPRESENTATION> {

  public IMAGE_REPRESENTATION importImage(String fileName);

  public void exportImage(String fileName, IImage image);
}