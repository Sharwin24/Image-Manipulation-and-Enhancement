package cs3500.model;

/**
 * Extends the {@link IMultiLayerModel} interface, thus promising all of the same behavior, while
 * also providing two new <b>extra credit</b> operations:.
 * <ul>
 *   <li>
 *     The ability to {@link IMultiLayerExtraOperations#mosaic(int)}--mosaic the image
 *     based on a given number of seeds.
 *   </li>
 *   <li>
 *     The ability to {@link IMultiLayerExtraOperations#downscaleLayers(int, int)}--downscale all
 *     layers of the multi-layered image to a new <code>width</code> and <code>height</code>.
 *   </li>
 * </ul>
 */
public interface IMultiLayerExtraOperations extends IMultiLayerModel {

  /**
   * Applies the mosaic operation on the image given the number of seeds.
   * @param numSeeds the number of seeds for the Mosaic.
   * @throws IllegalArgumentException if the number of seeds is less than or equal to 0.
   */
  void mosaic(int numSeeds)
      throws IllegalArgumentException;

  /**
   * Downscales the model's image to the new height and new width.
   * @param newHeight the new height of the downscaled image.
   * @param newWidth the new width of the downscaled image.
   * @throws IllegalArgumentException if the new size is larger or the same as the original.
   */
  void downscaleLayers(int newHeight, int newWidth)
      throws IllegalArgumentException;
}