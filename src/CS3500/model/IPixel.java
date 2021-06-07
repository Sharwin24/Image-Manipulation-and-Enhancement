package CS3500.model;

public interface IPixel {

  /**
   * TODO
   *
   * @param type
   * @return
   * @throws IllegalArgumentException
   */
  public int getIntensity(EChannelType type)
      throws IllegalArgumentException;

}
