package cs3500.view;

import cs3500.Utility;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.layer.ILayer;
import java.io.IOException;

/**
 * Class to represent A textual View for the IME.
 */
public class TextualIMEView implements IMEView {

  private final IMultiLayerModel mdl;
  private final Appendable ap;

  /**
   * Constructs a TextualIMEView with default model and appendable.
   */
  public TextualIMEView() {
    this.mdl = new MultiLayerModelImpl();
    this.ap = System.out;
  }

  /**
   * Constructs a TextualIMEView with a given model and default appendable.
   *
   * @param mdl the model for the view to use.
   */
  public TextualIMEView(IMultiLayerModel mdl)
      throws IllegalArgumentException {
    this.mdl = Utility.checkNotNull(mdl, "cannot create a textual IME view from a "
        + "null model");
    this.ap = System.out;
  }

  /**
   * Constructs a TextualIMEView with a given model and given appendable.
   *
   * @param mdl the model for the view to use.
   * @param ap  the appendable for the view to use.
   */
  public TextualIMEView(IMultiLayerModel mdl, Appendable ap)
      throws IllegalArgumentException {
    this.mdl = Utility.checkNotNull(mdl, "cannot create a textual IME view from a "
        + "null model");
    this.ap = Utility.checkNotNull(ap, "cannot create a textual IME view from a "
        + "null Appendable");
  }

  @Override
  public void renderLayers() {
    StringBuilder renderedLayers = new StringBuilder();
    int layerCtr = 0;
    for (ILayer layer : mdl.getLayers()) {
      renderedLayers.append("LAYER ").append(layerCtr).append(layer.toString()).append("\n");
      layerCtr++;
    }
    this.write(renderedLayers.toString());
  }

  @Override
  public void write(String toWrite)
      throws IllegalStateException, IllegalArgumentException {
    try {
      ap.append(Utility.paddedPrint(Utility.checkNotNull(toWrite, "cannot write a "
          + "null message")));
    } catch (IOException e) {
      throw new IllegalStateException("transmitting the message: \"" + toWrite + "\" to " +
          ap + " failed. Check the Appendable object you're using.");
    }
  }
}