package cs3500.model;

import cs3500.model.image.IImage;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.operation.IOperation;

/**
 * A basic interface for an Image Manipulation and Enhancement Model. Allows the user to import and
 * export images, apply some number of {@link IOperation}s to the image, by passing them as function
 * objects, and to set the working image to a programmatic image parameterized by its width height
 * and unit size in pixels (see {@link IIMEModel#setProgrammaticImage(IProgramImage, int, int,
 * int)}.
 */
public interface IIMEModel {

  /**
   * Applies one or more operations to the current image, in the order given to the method.
   *
   * @param operations the operations to apply, in order of application.
   * @throws IllegalArgumentException if any of the operations are invalid or null.
   */
  void applyOperations(IOperation... operations) throws IllegalArgumentException;

  /**
   * Loads in an image into the model.
   *
   * @param image the image to load in to the model.
   * @throws IllegalArgumentException if the image is null.
   */
  void load(IImage image) throws IllegalArgumentException;

  /**
   * Sets the current image to a programmatic image with the given parameters.
   *
   * @param imgToSet   the type of programmatic image to set to.
   * @param widthPx    the width of the image.
   * @param heightPx   the height of the image.
   * @param unitSizePx the unit size of the image.
   * @throws IllegalArgumentException if the progammatic image type is invalid or null.
   */
  void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx)
      throws IllegalArgumentException;

  /**
   * Observer method to return the image being worked on.
   *
   * @return the image being worked on.
   */
  IImage getImage();

  /**
   * Returns a copy of this model.
   *
   * @return an {@link IIMEModel} that is a copy of this one.
   */
  IIMEModel copy();
}