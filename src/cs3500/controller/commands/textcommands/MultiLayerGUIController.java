package cs3500.controller.commands.textcommands;

import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.IMEView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiLayerGUIController extends MultiLayerIMEControllerImpl implements ActionListener {

  /**
   * A Trivial initialization constructor that constructs a {@link MultiLayerIMEControllerImpl} based
   * on a given model to control, the view to render feedback to, and the {@link Readable} and {@link
   * Appendable} to read/write data I/O data from/to (resp.).
   *
   * @param mdl the model to control
   * @param rd  the readable to read input from
   * @param ap  the appendable to write data to
   * @param vw  the view to render feedback and interactions messages to
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  public MultiLayerGUIController(IMultiLayerExtraOperations mdl, Readable rd,
      Appendable ap, IMEView vw) throws IllegalArgumentException {
    super(mdl, rd, ap, vw);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}