package CS3500.model.fileFormat;

import CS3500.model.image.IImage;

/**
 * TODO: JavaDocs
 *
 * @param <IMAGE_REPRESENTATION>
 */
public interface IFileFormat<IMAGE_REPRESENTATION> {

  public IMAGE_REPRESENTATION importImage(String relativePath);

  public void exportImage(String relativePath, IImage image);
}
