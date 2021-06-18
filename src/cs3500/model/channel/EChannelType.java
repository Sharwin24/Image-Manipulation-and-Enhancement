package cs3500.model.channel;

/**
 * Represents one of three different channels in a pixel that uses red, green, and blue light to
 * show a color.
 */
public enum EChannelType {
  RED('R'), GREEN('G'), BLUE('B');

  private final char symbol;

  private EChannelType(char symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the textual representation of the ChannelType.
   *
   * @return a String for the text representation.
   * @throws IllegalArgumentException if the type is invalid.
   */
  public String toString()
      throws IllegalArgumentException {
    return Character.toString(this.symbol);
  }
}