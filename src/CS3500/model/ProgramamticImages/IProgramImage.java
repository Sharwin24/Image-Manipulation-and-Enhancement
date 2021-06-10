package CS3500.model.ProgramamticImages;

import CS3500.model.image.IImage;

/**
 * Todo: JavaDocs
 */
public interface IProgramImage<Z> {

  /**
   * TODO
   *
   * @param widthSizePx
   * @param heightSizePx
   * @param unitSizePx
   * @return
   * @throws IllegalArgumentException
   */
  Z createProgramImage(int widthSizePx, int heightSizePx, int unitSizePx)
      throws IllegalArgumentException;
}