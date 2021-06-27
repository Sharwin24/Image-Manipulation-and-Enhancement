package cs3500.controller;

import cs3500.model.IMultiLayerExtraOperations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class MultiLayerGUIController implements IMultiLayerIMEController, ActionListener {

  private IMultiLayerExtraOperations model;
  private JFrame frame;

  /**
   * A Trivial initialization constructor that constructs a {@link MultiLayerIMEControllerImpl}
   * based on a given model to control, the view to render feedback to, and the {@link Readable} and
   * {@link Appendable} to read/write data I/O data from/to (resp.).
   *
   * @param model the model to control
   * @param frame The frame of the GUI.
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  public MultiLayerGUIController(IMultiLayerExtraOperations model, JFrame frame)
      throws IllegalArgumentException {
    this.model = model;
    this.frame = frame;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void run() {

  }
}