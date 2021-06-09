package CS3500.model.fileFormat;

import CS3500.model.image.IImage;

/**
 * TODO: JavaDocs
 */
public interface IFileFormat {

  public IImage importImage(String fileName);

  public void exportImage(String fileName);
}
