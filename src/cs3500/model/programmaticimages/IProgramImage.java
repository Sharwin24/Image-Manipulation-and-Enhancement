package cs3500.model.programmaticimages;

import cs3500.model.image.IImage;

/**
 * <p>This interface acts as a function object with signature {@code int, int, int -> Z},
 * where {@code Z} is an abstract representation of an image.</p>
 * <p>Represents an image that can be programmatically created. I.e. an image that can be created
 * in easily automatible fashion by specifying parametrs for its width, height, and the size of a
 * unit in the image, if needed.</p>
 */
public interface IProgramImage{

  /**
   * <p>Creates a new programmatic image based on the specified width in pixels, height in pixels,
   * and unit size in pixels, where the unit size describes the size of a smaller piece of this
   * image that is repeated throughout the image multiple times. For example, in a {@link
   * Checkerboard} image, the {@code unitPx} represents the size of one square in the
   * checkerboard.</p>
   *
   * <p>If the width and height are not sufficient to represent the image, then they may be edited
   * to "fit" the entire image better</p>
   *
   * @param widthPx  the width of the resultant image in pixels
   * @param heightPx the size of the resultant image in pixels.
   * @param unitPx   the size of a smaller subunit of a programmatically created image as described
   *                 above.
   * @return the programmatic image with the specified dimensions.
   * @throws IllegalArgumentException if the width height or unit size are negative, or if the unit
   *                                  size is sufficiently large enough to not fit inside the
   *                                  specified dimensions of width and height.
   */
  IImage createProgramImage(int widthPx, int heightPx, int unitPx)
      throws IllegalArgumentException;
}