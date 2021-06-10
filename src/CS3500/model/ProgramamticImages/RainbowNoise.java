package CS3500.model.ProgramamticImages;

import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO
 */
public class RainbowNoise extends Noise {

  /**
   * TODO
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
