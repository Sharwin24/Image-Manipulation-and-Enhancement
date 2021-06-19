package cs3500.view;

import java.util.List;

/**
 * To display feedback to the user when controlling the model. Also provides a useful textual
 * representation of the state of the model with information about which layer(s) is/are being
 * worked with.
 */
public interface IMEView {

  /**
   * Renders the {@link cs3500.model.layer.ILayer}s that the user is working on, in the format
   * <code>LAYER [i] | Visibility = [v]</code>,
   * where <code>i</code> is the index of the layer, starting at one going from left to right with
   * respect to the {@link List} that the layers are stored in, and where [v] is a {@code boolean}
   * denoting if this layer is visible.
   */
  void renderLayers();

  /**
   * Writes the given message {@code toWrite} to the view.
   *
   * @param toWrite the message to write to the view.
   * @throws IllegalStateException if writing the message fails (usually an I/O issue).
   * @throws IllegalArgumentException if the message to write is <code>null</code>.
   */
  void write(String toWrite)
      throws IllegalStateException, IllegalArgumentException;

}