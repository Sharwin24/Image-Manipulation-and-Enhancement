package CS3500.model;

public interface IImage {

  /**
   * TODO
   * @param channel
   * @return
   * @throws IllegalArgumentException
   */
  IMatrix<Integer> extractChannel(EChannelType channel)
      throws IllegalArgumentException;


}
