package cs3500.controller;

import cs3500.Utils;
import cs3500.controller.commands.IIMECommand;
import cs3500.controller.commands.RedoCommand;
import cs3500.controller.commands.SaveCommand;
import cs3500.controller.commands.UndoCommand;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.image.IImage;
import cs3500.view.IIMEView;
import cs3500.view.TextualIMEView;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MultiLayerIMEControllerImpl implements IMultiLayerIMEController<IImage> {

  private final IMultiLayerModel mdl;
  private final Readable rd;
  private final Appendable ap;
  private final IIMEView vw;
  private final Map<String, IIMECommand> cmds = new HashMap<>();

  /**
   * TODO: Use builder to clean these up
   */
  public MultiLayerIMEControllerImpl() {
    this.mdl = new MultiLayerModelImpl();
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.vw = new TextualIMEView();

    this.initCommands(cmds);
  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl)
  throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);

    this.initCommands(cmds);

  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl, Readable rd)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = Utils.checkNotNull(rd, "cannot create a IME controller with a "
        + "null Readable");
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);

    this.initCommands(cmds);


  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = Utils.checkNotNull(rd, "cannot create a IME controller with a "
        + "null Readable");
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);

    this.initCommands(cmds);


  }



  @Override
  public void run(IMultiLayerModel<IImage> mdl)
  throws IllegalArgumentException, IllegalStateException {
    Utils.checkNotNull(mdl, "cannot run the controller on a null model");

    Scanner s = new Scanner(rd);

    while(s.hasNextLine()) {
      String line = s.nextLine();

      Scanner lineScan = new Scanner(line);
      while (lineScan.hasNext()) {
        String inp = lineScan.next();
        if (cmds.containsKey(inp)) {
          vw.write("executing command " + inp);
          cmds.get(inp).execute(lineScan, mdl, vw);
        }
      }
    }
    System.out.println("done");


  }

  private void initCommands(Map<String, IIMECommand> emptyCmds) {
    emptyCmds.putIfAbsent("undo", new UndoCommand());
    emptyCmds.putIfAbsent("redo", new RedoCommand());
    emptyCmds.putIfAbsent("save", new SaveCommand());
    emptyCmds.putIfAbsent("import", new ImportCommand());
//    emptyCmds.putIfAbsent("export", new ExportCommand());
//    emptyCmds.putIfAbsent("apply", new ApplyCommand());
//    emptyCmds.putIfAbsent("set", new SetCommand());
//    emptyCmds.putIfAbsent("toggle", new ToggleCommand());
//    emptyCmds.putIfAbsent("layer", new LayerCommand());
//    emptyCmds.putIfAbsent("create", new CreateCommand());
  }
}
