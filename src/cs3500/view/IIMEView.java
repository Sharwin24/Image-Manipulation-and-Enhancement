package cs3500.view;

public interface IIMEView {

  String toString();

  void write(String toWrite)
      throws IllegalStateException, IllegalArgumentException;

}
