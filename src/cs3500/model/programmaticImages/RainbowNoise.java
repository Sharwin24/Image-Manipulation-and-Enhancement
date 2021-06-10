package cs3500.model.programmaticImages;

import cs3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A {@link Noise} image consisting of the colors of the rainbow. Used as a convenience to
 * create a noise image without specifying a select set of colors to use in {@link Noise}.
 */
public class RainbowNoise extends Noise {

  /**
   * Initialize the colors that this {@link Noise} image can select from to the colors of the
   * rainbow.
   */
  public RainbowNoise() {
    super(new ArrayList<>(Arrays.asList(
        PixelImpl.RED,
        PixelImpl.GREEN,
        PixelImpl.BLUE,
        PixelImpl.YELLOW,
        PixelImpl.MAGENTA,
        PixelImpl.CYAN,
        PixelImpl.ORANGE,
        PixelImpl.INDIGO,
        PixelImpl.VIOLET,
        PixelImpl.BLACK,
        PixelImpl.WHITE)));
  }
}