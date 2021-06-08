package CS3500.model.channel;

/**
 * Represents one of three different channels in a pixel that uses red, green, and blue light to
 * show a color.
 */
public enum EChannelType {
  RED, GREEN, BLUE;

  /**
   * TODO
   * @return
   * @throws IllegalArgumentException
   */
  public String toString()
  throws IllegalArgumentException{
    switch (this) {
      case RED:
        return "R";
      case GREEN:
        return "G";
      case BLUE:
        return "B";
      default:
        throw new IllegalArgumentException("should not have gotten here. Congrats, you broke our "
            + "code");
    }
  }
}