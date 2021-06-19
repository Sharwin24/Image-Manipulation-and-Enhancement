package cs3500.view;

/**
 *
 */
public interface IMEView {

  /**
   * @return
   */
  void renderLayers();

  /**
   * @param toWrite
   * @throws IllegalStateException
   * @throws IllegalArgumentException
   */
  void write(String toWrite)
      throws IllegalStateException, IllegalArgumentException;

}