package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.programmaticimages.BWNoise;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.model.programmaticimages.PureNoise;
import cs3500.model.programmaticimages.RainbowNoise;
import cs3500.view.IMEView;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#setProgrammaticImage(IProgramImage, int, int, int)} call in the {@link
 * IMultiLayerModel}, to be used to implement the <i>command design pattern</i> in the {@link
 * cs3500.controller.IMultiLayerIMEController} class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>programmatic [pi] [w] [h] [u]</code>", in order to set this layer to a
 * <code>programmatic</code> image of a
 * <code>[pi]</code>-- an {@link IProgramImage} function object, represented as text as
 * defined in the table contained in the JavaDoc for
 * {@link ProgrammaticImageCommand#initImagesMap()}
 * below, with a width of <code>[w]</code> pixels, with a height of <code>[h]</code> pixels, with a
 * unit size of <code>[u]</code> pixels, as defined in the JavaDoc for {@link
 * IProgramImage#createProgramImage(int, int, int)}.
 */
public class ProgrammaticImageCommand extends AIMECommand {

  private final Map<String, IProgramImage> imagesMap = this.initImagesMap();

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IMEView vw) {

    try {
      String type = lineScan.next();
      int width = Integer.parseInt(lineScan.next());
      int height = Integer.parseInt(lineScan.next());
      int unit = Integer.parseInt(lineScan.next());

      mdl.setProgrammaticImage(imagesMap.get(type),
          width, height, unit);
    } catch (IllegalArgumentException | NoSuchElementException e) {
      vw.write("invalid arguments passed to programmatic command, try again");
    }
  }

  /**
   * <p>Initializes a one-to-one mapping between textual commands that the user can \
   * supply to specify
   * what type of {@link IProgramImage} they'd like to create, and the
   * actual corresponding concrete object representing that programmtic image, as specified in the
   * table below</p>
   * <p><table style="width:150%">
   * <tr>
   * <th>Textual Command  </th>
   * <th>{@link IProgramImage
   * } Function Object  </th>
   * <th>Description</th>
   * </tr>
   * <tr>
   * <td><code>checkerboard</code></td>
   * <td>{@link Checkerboard
   * }</td>
   * <td>Creates a checkerboard image</td>
   * </tr>
   * <tr>
   * <td><code>purenoise</code></td>
   * <td>{@link PureNoise
   * }</td>
   * <td>Creates a noise image where every pixel's color is randomized</td>
   * </tr>
   * <tr>
   * <td><code>bwnoise</code></td>
   * <td>{@link BWNoise
   * }</td>
   * <td>Creates a black and white noise image, where each pixel is randomly chosen to be either
   * white or black</td>
   * </tr>
   * <tr>
   * <td><code>rainbownoise</code></td>
   * <td>{@link RainbowNoise
   * }</td>
   * <td>Creates a rainbow noise image, where each pixel is randomly chosen to be a color
   * of the rainbow</td>
   * </tr>
   * </table></p>
   *
   * @return the Mapping described above, as a {@link Map}.
   */

  private Map<String, IProgramImage> initImagesMap() {
    Map<String, IProgramImage> imagesMap = new HashMap<>();

    imagesMap.putIfAbsent("checkerboard", new Checkerboard());
    imagesMap.putIfAbsent("purenoise", new PureNoise());
    imagesMap.putIfAbsent("bwnoise", new BWNoise());
    imagesMap.putIfAbsent("rainbownoise", new RainbowNoise());

    return imagesMap;
  }
}