package cs3500.model.programmaticimages;

import cs3500.Utils;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.pixel.IPixel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A programmatically created noise image, where there is some {@link List} of possible colors,
 * represented as {@link IPixel}s that can be selected at random, filling the dimensions specified
 * in {@link Noise#createProgramImage(int, int, int)}, and creating a new {@link IImage} as such.
 * Note that the {@code unitPx}--the unit size of this image is ignored, since pixels are selected
 * at random.
 */
public class Noise implements IProgramImage<IImage> {

  private List<IPixel> pixelChoices;

  /**
   * Creates a {@link Noise} object with some list of colors that it can include when
   * programmatically creating an image.
   *
   * @param pixelChoices the possible colors in this {@link Noise} image, represented as {@link
   *                     IPixel}s.
   * @throws IllegalArgumentException if the provided list is {@code null} or if any of its contents
   *                                  are {@code null}, or if the provided list is empty.
   */
  public Noise(List<IPixel> pixelChoices)
      throws IllegalArgumentException {
    if (pixelChoices.isEmpty()) {
      throw new IllegalArgumentException("cannot create a noise image with no colors");
    }
    Utils.checkNotNullListContents(pixelChoices, "cannot set a null color for a noise "
        + "image");
    this.pixelChoices = Utils.checkNotNull(pixelChoices, "cannot create a noise image "
        + "with null colors");

  }

  /**
   * Creates a {@link Noise} object with a variable number of colors that it can include when
   * programmatically creating an image.
   *
   * @param pixelChoices the possible colors in this {@link Noise} image, represented as {@link
   *                     IPixel}s.
   * @throws IllegalArgumentException if any of the provided {@link IPixel}s are {@code null}
   *                                  are {@code null}, or if none were provided.
   */
  public Noise(IPixel... pixelChoices)
      throws IllegalArgumentException {
    if (pixelChoices.length == 0) {
      throw new IllegalArgumentException("cannot create a noise image with no colors");
    }

    List<IPixel> pixelList = new ArrayList<>();

    for (IPixel px : pixelChoices) {
      pixelList.add(Utils.checkNotNull(px, "cannot create a noise image with a null pixel"));
    }

    this.pixelChoices = pixelList;
  }


  @Override
  public IImage createProgramImage(int widthPx, int heightPx, int unitPx)
      throws IllegalArgumentException {
    Utils.checkIntBetween(widthPx, 0, Integer.MAX_VALUE);
    Utils.checkIntBetween(heightPx, 0, Integer.MAX_VALUE);

    List<List<IPixel>> pixels = new ArrayList<>();
    for (int i = 0; i < heightPx; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < widthPx; j++) {
        Random rand = new Random();
        thisRow.add(pixelChoices.get(rand.nextInt(pixelChoices.size())));
      }
      pixels.add(thisRow);
    }

    return new ImageImpl(pixels);
  }
}