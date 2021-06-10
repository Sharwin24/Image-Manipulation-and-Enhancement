package CS3500.model.ProgramamticImages;

import CS3500.Utils;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.pixel.IPixel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO
 */
public class Noise implements IProgramImage<IImage> {

  private List<IPixel> pixelChoices;

  /**
   * TODO
   *
   * @param pixelChoices
   * @throws IllegalArgumentException
   */
  public Noise(List<IPixel> pixelChoices)
      throws IllegalArgumentException {
    if (pixelChoices.isEmpty()) {
      throw new IllegalArgumentException("cannot create a noise image with no colors");
    }
    this.pixelChoices = Utils.checkNotNull(pixelChoices, "cannot create a noise image "
        + "with null colors");
  }

  /**
   * TODO
   *
   * @param pixelChoices
   * @throws IllegalArgumentException
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
