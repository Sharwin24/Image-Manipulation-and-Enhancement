package cs3500.model;

public interface IMultiLayerExtraOperations extends IMultiLayerModel{
  /**
   * Applies the mosaic operation on the image given the number of seeds.
   * @param numSeeds the number of seeds for the Mosaic.
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