package cs3500.view;

import cs3500.Utils;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;

/**
 * TODO
 */
public class TextualIMEView implements IIMEView {

  private final IMultiLayerModel<?> mdl;
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
  public TextualIMEView(IMultiLayerModel<?> mdl)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a textual IME view from a "
        + "null model");
    this.ap = System.out;
  }

  /**
   * TODO
   *
   * @param mdl
   * @param ap
   */
  public TextualIMEView(IMultiLayerModel<?> mdl, Appendable ap)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a textual IME view from a "
        + "null model");
    this.ap = Utils.checkNotNull(ap, "cannot create a textual IME view from a "
        + "null Appendable");
  }

  @Override
  public String toString() {
    return ""; // TODO
  }

  @Override
  public void write(String toWrite) {
    // TODO
  }
}
