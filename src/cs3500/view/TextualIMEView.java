package cs3500.view;

import cs3500.Utility;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.layer.ILayer;
import java.io.IOException;

/**
 * TODO
 */
public class TextualIMEView implements IMEView {

  private final IMultiLayerModel mdl;
  private final Appendable ap;

  /**
   * TODO
   */
  public TextualIMEView() {
    this.mdl = new MultiLayerModelImpl();
    this.ap = System.out;
  }

  /**
   * TODO
   *
   * @param mdl
   */
  public TextualIMEView(IMultiLayerModel mdl)
      throws IllegalArgumentException {
    this.mdl = Utility.checkNotNull(mdl, "cannot create a textual IME view from a "
        + "null model");
    this.ap = System.out;
  }

  /**
   * TODO
   *
   * @param mdl
   * @param ap
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
//    String renderedLayers = "";
//    int layerCtr = 0;
//    for (ILayer lyr : mdl.getLayers()) {
//      renderedLayers += "LAYER " + layerCtr + ": " +
//          lyr.toString() + "\n";
//      layerCtr++;
//    }
//    this.write(renderedLayers);
    String renderedLayers = "";
    int layerCtr = 0;
    for (ILayer layer : mdl.getLayers()) {
      renderedLayers += "LAYER " + layerCtr + layer.toString() + "\n";
      layerCtr++;
    }
    this.write(renderedLayers);
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