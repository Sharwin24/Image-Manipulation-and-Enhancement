package CS3500.model.ProgramamticImages;

import CS3500.model.image.IImage;

/**
 * Todo: JavaDocs
 */
public interface IProgramImage<Z> {

  /**
   * TODO
   *
   * @param widthPx
   * @param heightPx
   * @param unitPx
   * @return
   * @throws IllegalArgumentException
   */
  Z createProgramImage(int widthPx, int heightPx, int unitPx)
      throws IllegalArgumentException;
}