package cs3500.model.fileformat;

import cs3500.model.image.IImage;
import java.io.File;

/**
 * A function object representing a PPM/NetPBM file format, with the ability to import a ppm image
 * and store it in some sort of image representation {@code I}, as well as the ability to export
 * some type of image {@code I} to a PPM file stored in the res directory.
 */
public interface IFileFormat {

  /**
   * Imports an image from the {@code relativePath} from the current working directory and returns
   * it as some type of image {@code I}.
   *
   * @param relativePath the relative path from the working (project) directory to the file.
   * @return the imported image, stored as some representation of an image of type {@code I}.
   * @throws IllegalArgumentException if <ul>
   *                                  <li>the {@code relativePath} is {@code null}.</li>
   *                                  <li>the {@code relativePath} is the empty String</li>
   *                                  <li>the specified file is not in the correct format</li>
   *                                  <li>the {@code relativePath} doesn't point to a file of the
   *                                  correct type</li>
   *                                  <li>the {@code relativePath} doesn't exist</li>
   *                                  <li>the {@code relativePath} points to a corrupted file</li>
   *                                  </ul>
   */
  IImage importImage(String relativePath)
      throws IllegalArgumentException;

  /**
   * Exports the given {@code image}, represented by the parameterized type {@code I} to a given
   * {@code relativePath} from the working (project) directory, creating a file there and saving it.
   * Adds the given file type (a function object implementing this interface) to the end of the path
   * if not specified. I.e. if the {@code relativePath} is specified to be {@code Foo}, and this
   * method is called by a function object of concrete subtype {@code Bar}, representing a
   * theoretical file format named ".bar" then the path name will be specified as {@code Foo.bar}.
   * Similarly, if the file extension is specified, say {@code Foo.bar} in the same situation, then
   * the concrete implementation will ensure that the extension name is correct and name the file
   * {@code Foo.bar.bar}.
   *
   * @param relativePath the relative path from the working (project) directory to the file in which
   *                     to store the exported {@code image}.
   * @param image        the image to export, parameterized by some type {@code I}.
   * @return the {@link File} that was exported (used primarily for testing purposes).
   * @throws IllegalArgumentException If <ul>
   *                                  <li>the path is {@code null}</li>
   *                                  <li>the path is the empty String</li>
   *                                  <li>the {@code image} to export is {@code null}</li>
   *                                  </ul>
   */
  File exportImage(String relativePath, IImage image)
      throws IllegalArgumentException;

  /**
   * Creates a directory with the given name at the given relative path.
   *
   * @param directoryName the name of the directory.
   * @param relativePath  the path to the folder to put the directory in.
   * @throws IllegalArgumentException if either arguments are null.
   */
  void createDirectory(String directoryName, String relativePath) throws IllegalArgumentException;
}