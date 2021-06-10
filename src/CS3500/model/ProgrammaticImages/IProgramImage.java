package CS3500.model.ProgrammaticImages;

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