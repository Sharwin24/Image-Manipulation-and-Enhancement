package cs3500.view;

import cs3500.Utility;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.controller.commands.guicommands.BWNoiseCommand;
import cs3500.controller.commands.guicommands.CheckerBoardCommand;
import cs3500.controller.commands.guicommands.CurrentLayerCommand;
import cs3500.controller.commands.guicommands.CurrentLayerWithIndexCommand;
import cs3500.controller.commands.guicommands.CustomNoiseCommand;
import cs3500.controller.commands.guicommands.DeleteLayerCommand;
import cs3500.controller.commands.guicommands.DeleteLayerWithIndexCommand;
import cs3500.controller.commands.guicommands.DownScaleCommand;
import cs3500.controller.commands.guicommands.ExportAllCommand;
import cs3500.controller.commands.guicommands.ExportOneCommand;
import cs3500.controller.commands.guicommands.GUIMosaicCommand;
import cs3500.controller.commands.guicommands.IGUICommand;
import cs3500.controller.commands.guicommands.ImportAllCommand;
import cs3500.controller.commands.guicommands.ImportImageToAllLayers;
import cs3500.controller.commands.guicommands.ImportOneCommand;
import cs3500.controller.commands.guicommands.LoadScriptCommand;
import cs3500.controller.commands.guicommands.NewLayerCommand;
import cs3500.controller.commands.guicommands.OperationCommand;
import cs3500.controller.commands.guicommands.PureNoiseCommand;
import cs3500.controller.commands.guicommands.RainbowNoiseCommand;
import cs3500.controller.commands.guicommands.RedoCommand;
import cs3500.controller.commands.guicommands.RunScriptCommand;
import cs3500.controller.commands.guicommands.SwapLayersCommand;
import cs3500.controller.commands.guicommands.ThemeCommand;
import cs3500.controller.commands.guicommands.UndoCommand;
import cs3500.controller.commands.guicommands.ViewGitHubCommand;
import cs3500.controller.commands.guicommands.VisibleLayerCommand;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * The interactive GUI frame for the Image Manipulation and Enhancement program. Supports all of the
 * functionality of the {@link IMultiLayerModel} interface, exposing all functionality through a
 * menu ribbon as well as convenient buttons. Also supports interactive scripting from within the
 * GUI itself.
 */
public class GUIView extends JFrame implements IMEView, ActionListener, ItemListener {

  private static final String WORKING_DIR = System.getProperty("user.dir");
  // the dimensions of a typical computer screen
  private static final int SCREEN_WIDTH = 1200;
  private static final int SCREEN_HEIGHT = 900;

  // themes
  static final GUITheme LIGHT_THEME = new GUITheme(Color.WHITE, Color.LIGHT_GRAY,
      Color.GRAY);
  private static final GUITheme DARK_THEME = new GUITheme(Color.BLACK, Color.DARK_GRAY,
      Color.ORANGE);
  private static final GUITheme MATRIX_THEME = new GUITheme(Color.BLACK, Color.GREEN, Color.GREEN);
  private static final GUITheme RETRO_THEME = new GUITheme(Color.BLUE, Color.RED,
      new Color(177, 156, 217));

  // URLS
  public static final String GITHUB_URL = "https://github.com/Sharwin24/Image-Manipulation-"
      + "and-Enhancement.git";

  // to represent the model--the images to be manipulated
  private final IMultiLayerExtraOperations model = new MultiLayerModelImpl();


  // to represent the scriptable controller embedded in the GUI
  private IMultiLayerIMEController scrptCtrlr;

  /**
   * Gets the Controller.
   *
   * @return a IMultiLayerIMEController.
   */
  public IMultiLayerIMEController getScrptCtrlr() {
    return this.scrptCtrlr;
  }

  /**
   * Sets the Controller to the given one.
   *
   * @param scrptCtrlr the controller to set the current one to.
   * @throws IllegalArgumentException if the input is null.
   */
  public void setScrptCtrlr(IMultiLayerIMEController scrptCtrlr) {
    if (scrptCtrlr == null) {
      throw new IllegalArgumentException("Argument is null");
    }
    this.scrptCtrlr = scrptCtrlr;
  }

  // to store the GUI elements
  private JPanel mainPanel;

  /**
   * Gets the main panel.
   *
   * @return a JPanel.
   */
  public JPanel getMainPanel() {
    return this.mainPanel;
  }

  // to store the menu ribbon elements
  private JMenuBar menuRibbon;

  /**
   * Gets the Menu Ribbon.
   *
   * @return a JMenuBar.
   */
  public JMenuBar getMenuRibbon() {
    return this.menuRibbon;
  }

  // Image panel architecture
  private JScrollPane imageScrollPanel;
  private final JLabel imgLabel = new JLabel("");

  /**
   * Gets the Image Scroll Panel.
   *
   * @return a JScrollPane.
   */
  public JScrollPane getImageScrollPanel() {
    return imageScrollPanel;
  }

  // to handle text in the console
  public JPanel consolePanel;
  private JTextArea consoleTxt;

  /**
   * Gets the Console Panel
   *
   * @return a JPanel.
   */
  public JPanel getConsolePanel() {
    return consolePanel;
  }

  // Handle Dialog Panel
  private JPanel inputDialogPanel;

  /**
   * Gets the Input Dialog Panel.
   *
   * @return a JPanel.
   */
  public JPanel getInputDialogPanel() {
    return this.inputDialogPanel;
  }

  // Handle Color Chooser
  private JPanel colorChooserDisplay = new JPanel();

  /**
   * Gets the Color Chooser Display.
   *
   * @return a JPanel.
   */
  public JPanel getColorChooserDisplay() {
    return this.colorChooserDisplay;
  }

  // Handle Layers
  private final JPanel layerAreaPanel = new JPanel();
  private final JPanel layerButtonsPanel = new JPanel();
  private JPanel layersPanel = new JPanel();
  private final List<JPanel> allLayers = new ArrayList<>();

  /**
   * Gets the layers panel.
   *
   * @return a JPanel.
   */
  public JPanel getLayersPanel() {
    return this.layersPanel;
  }

  /**
   * Gets the list of Layer Panels.
   *
   * @return a List of JPanels.
   */
  public List<JPanel> getAllLayers() {
    return this.allLayers;
  }

  // Handle Script Area
  private JTextArea scriptArea;

  /**
   * Gets the Script area.
   *
   * @return a JTextArea.
   */
  public JTextArea getScriptArea() {
    return this.scriptArea;
  }

  // Initializing actions map and theme
  private final Map<String, IGUICommand> actionsMap = this.initActionsMap();
  private final GUITheme defaultTheme = LIGHT_THEME;

  /**
   * Sets up the visual components of the GUI in all their glory. Sets the default theme of the GUI
   * to {@link this#defaultTheme}. Adds all action listeners to interactive elements so that they
   * can trigger events.
   */
  public GUIView() {
    super();
    setTitle("Image Manipulation and Enhancement");
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    // to store interactively-scripted commands
    Readable scriptIn = new StringReader("");
    // to store output from the view
    Appendable out = new StringBuilder();
    // to represent the embedded text view
    IMEView txtView = new TextualIMEView(model, out);
    this.scrptCtrlr =
        MultiLayerIMEControllerImpl.controllerBuilder()
            .model(model)
            .readable(scriptIn)
            .appendable(out)
            .view(txtView)
            .buildController();

    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BorderLayout());

    // setup layers panel
    this.setLayerButtonsPanel();
    this.layersPanel();
    // setup console panel
    this.console();
    // setup script area panel
    this.scriptArea();
    // setup image area panel.
    this.imageArea();
    // setup the menu bar
    this.menuRibbon();

    //~~~~~~~~~~~~~~~~~~~~~~~~~//
    add(mainPanel);
    new ThemeCommand(model, this, defaultTheme).execute();
  }

  /**
   * Initializes the dropdown menu bar at the top of the gui.
   */
  private void menuRibbon() {
    this.menuRibbon = new JMenuBar();

    // the file menu
    // the architecture for the file menu
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    fileMenu.getAccessibleContext().setAccessibleDescription("This menu handles file I/O");

    // the import submenu
    JMenu importSubmenu = new JMenu("Import...");
    importSubmenu.setMnemonic(KeyEvent.VK_I);
    JMenuItem importOneImageItem = new JMenuItem("Import one image");
    importOneImageItem.setMnemonic(KeyEvent.VK_O);
    importOneImageItem.setActionCommand("import one");
    importOneImageItem.addActionListener(this);
    importSubmenu.add(importOneImageItem);

    JMenuItem importAllImagesItem = new JMenuItem("Import a multi-layered image");
    importAllImagesItem.setMnemonic(KeyEvent.VK_M);
    importAllImagesItem.setActionCommand("import all");
    importAllImagesItem.addActionListener(this);
    importSubmenu.add(importAllImagesItem);

    JMenuItem importOneImageToAllLayersItem = new JMenuItem("Import an image to all layers");
    importOneImageToAllLayersItem.setActionCommand("import image to all layers");
    importOneImageToAllLayersItem.addActionListener(this);
    importSubmenu.add(importOneImageToAllLayersItem);

    fileMenu.add(importSubmenu);

    // the export submenu
    JMenu exportSubmenu = new JMenu("Export...");
    exportSubmenu.setMnemonic(KeyEvent.VK_E);
    JMenuItem exportOneImageItem = new JMenuItem("export one layer of an image");
    exportOneImageItem.setMnemonic(KeyEvent.VK_O);
    exportOneImageItem.setActionCommand("export one");
    exportOneImageItem.addActionListener(this);
    exportSubmenu.add(exportOneImageItem);

    JMenuItem exportAllImagesItem = new JMenuItem("export a multi-layered image");
    exportAllImagesItem.setMnemonic(KeyEvent.VK_M);
    exportAllImagesItem.setActionCommand("export all");
    exportAllImagesItem.addActionListener(this);
    exportSubmenu.add(exportAllImagesItem);

    fileMenu.add(exportSubmenu);

    menuRibbon.add(fileMenu);

    // the edit menu
    // the architecture for the edit menu
    JMenu editMenu = new JMenu("Edit");
    editMenu.setMnemonic(KeyEvent.VK_E);
    editMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides low-level functionality to manipulate images");

    JMenuItem undoItem = new JMenuItem("Undo");
    undoItem.setMnemonic(KeyEvent.VK_U);
    undoItem.setActionCommand("undo");
    undoItem.addActionListener(this);
    editMenu.add(undoItem);

    JMenuItem redoItem = new JMenuItem("Redo");
    redoItem.setMnemonic(KeyEvent.VK_R);
    redoItem.setActionCommand("redo");
    redoItem.addActionListener(this);
    editMenu.add(redoItem);

    JMenuItem newLayerItem = new JMenuItem("New layer");
    newLayerItem.setMnemonic(KeyEvent.VK_N);
    newLayerItem.setActionCommand("new");
    newLayerItem.addActionListener(this);
    editMenu.add(newLayerItem);

    JMenuItem setCurrentLayerItem = new JMenuItem("Set current layer");
    setCurrentLayerItem.setMnemonic(KeyEvent.VK_C);
    setCurrentLayerItem.setActionCommand("set current layer");
    setCurrentLayerItem.addActionListener(this);
    editMenu.add(setCurrentLayerItem);

    JMenuItem deleteItem = new JMenuItem("Delete layer");
    deleteItem.setMnemonic(KeyEvent.VK_D);
    deleteItem.setActionCommand("delete");
    deleteItem.addActionListener(this);
    editMenu.add(deleteItem);

    menuRibbon.add(editMenu);

    // the transformations menu
    // the architecture for the transformations menu
    JMenu transformationsMenu = new JMenu("Transformations");
    transformationsMenu.setMnemonic(KeyEvent.VK_T);
    transformationsMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides the functionality to apply one of the following transformations to the"
            + " current layer");

    JMenuItem sepiaItem = new JMenuItem("Sepia");
    sepiaItem.setMnemonic(KeyEvent.VK_E);
    sepiaItem.setActionCommand("sepia");
    sepiaItem.addActionListener(this);
    transformationsMenu.add(sepiaItem);

    JMenuItem greyScaleItem = new JMenuItem("Greyscale");
    greyScaleItem.setMnemonic(KeyEvent.VK_G);
    greyScaleItem.setActionCommand("greyscale");
    greyScaleItem.addActionListener(this);
    transformationsMenu.add(greyScaleItem);

    JMenuItem blurItem = new JMenuItem("Blur");
    blurItem.setMnemonic(KeyEvent.VK_B);
    blurItem.setActionCommand("blur");
    blurItem.addActionListener(this);
    transformationsMenu.add(blurItem);

    JMenuItem sharpenItem = new JMenuItem("Sharpen");
    sharpenItem.setMnemonic(KeyEvent.VK_H);
    sharpenItem.setActionCommand("sharpen");
    sharpenItem.addActionListener(this);
    transformationsMenu.add(sharpenItem);

    JMenuItem mosaicItem = new JMenuItem("Mosaic...");
    mosaicItem.setMnemonic(KeyEvent.VK_M);
    mosaicItem.setActionCommand("mosaic");
    mosaicItem.addActionListener(this);
    transformationsMenu.add(mosaicItem);

    inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());

    JMenuItem downscaleItem = new JMenuItem("Downscale...");
    downscaleItem.setMnemonic(KeyEvent.VK_D);
    downscaleItem.setActionCommand("downscale");
    downscaleItem.addActionListener(this);
    transformationsMenu.add(downscaleItem);

    menuRibbon.add(transformationsMenu);

    // the programmatic images menu
    // the architecture for the programmatic images menu
    JMenu programmaticImagesMenu = new JMenu("Programmatic Images");
    programmaticImagesMenu.setMnemonic(KeyEvent.VK_P);
    programmaticImagesMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides the functionality to set the current layer to one of a "
            + "few programmatically created images.");

    JMenuItem checkerBoardItem = new JMenuItem("Checkerboard...");
    checkerBoardItem.setMnemonic(KeyEvent.VK_C);
    checkerBoardItem.setActionCommand("checkerboard");
    checkerBoardItem.addActionListener(this);
    programmaticImagesMenu.add(checkerBoardItem);

    // the noise images submenu
    JMenu noiseSubMenu = new JMenu("Noise Images");
    noiseSubMenu.setMnemonic(KeyEvent.VK_N);
    noiseSubMenu.getAccessibleContext().setAccessibleDescription(
        "Creates a noise image wherein each pixel is randomly selected from an assortment of "
            + "colors");

    JMenuItem pureNoiseItem = new JMenuItem("Pure noise");
    pureNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel's color is completely randomized.");
    pureNoiseItem.setMnemonic(KeyEvent.VK_P);
    pureNoiseItem.setActionCommand("pure noise");
    pureNoiseItem.addActionListener(this);
    noiseSubMenu.add(pureNoiseItem);

    JMenuItem bwNoiseItem = new JMenuItem("Black and white noise...");
    bwNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is either black or white");
    bwNoiseItem.setMnemonic(KeyEvent.VK_B);
    bwNoiseItem.setActionCommand("bw noise");
    bwNoiseItem.addActionListener(this);
    noiseSubMenu.add(bwNoiseItem);

    JMenuItem rainbowNoiseItem = new JMenuItem("Rainbow noise...");
    rainbowNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is one of the colors of the rainbow: "
            + "red, orange, yellow, green, blue, indigo, or violet.");
    rainbowNoiseItem.setMnemonic(KeyEvent.VK_R);
    rainbowNoiseItem.setActionCommand("rainbow noise");
    rainbowNoiseItem.addActionListener(this);
    noiseSubMenu.add(rainbowNoiseItem);

    JMenuItem customNoiseItem = new JMenuItem("Custom...");
    customNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is randomly selected from a list of colors that "
            + "is specified via a color picker.");
    customNoiseItem.setMnemonic(KeyEvent.VK_C);
    customNoiseItem.setActionCommand("custom noise");
    customNoiseItem.addActionListener(this);
    noiseSubMenu.add(customNoiseItem);
    programmaticImagesMenu.add(noiseSubMenu);

    menuRibbon.add(programmaticImagesMenu);

    // theme menu
    // the architecture for changing the theme
    JMenu themeMenu = new JMenu("Theme");
    themeMenu.setMnemonic(KeyEvent.VK_M);
    themeMenu.getAccessibleContext().setAccessibleDescription("Change the theme of the GUI");

    JMenuItem lightThemeItem = new JMenuItem("Light");
    lightThemeItem.setMnemonic(KeyEvent.VK_L);
    lightThemeItem.setActionCommand("light theme");
    lightThemeItem.addActionListener(this);
    themeMenu.add(lightThemeItem);

    JMenuItem darkThemeItem = new JMenuItem("Dark");
    lightThemeItem.setMnemonic(KeyEvent.VK_D);
    darkThemeItem.setActionCommand("dark theme");
    darkThemeItem.addActionListener(this);
    themeMenu.add(darkThemeItem);

    JMenuItem matrixThemeItem = new JMenuItem("Matrix");
    matrixThemeItem.setMnemonic(KeyEvent.VK_M);
    matrixThemeItem.setActionCommand("matrix theme");
    matrixThemeItem.addActionListener(this);
    themeMenu.add(matrixThemeItem);

    JMenuItem retroThemeItem = new JMenuItem("Retro");
    retroThemeItem.setMnemonic(KeyEvent.VK_R);
    retroThemeItem.setActionCommand("retro theme");
    retroThemeItem.addActionListener(this);
    themeMenu.add(retroThemeItem);

    menuRibbon.add(themeMenu);

    // help menu
    // the architecture for the help menu
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);

    JMenuItem githubItem = new JMenuItem("View GitHub source");
    githubItem.setMnemonic(KeyEvent.VK_G);
    githubItem.setActionCommand("github");
    githubItem.addActionListener(this);
    helpMenu.add(githubItem);

    menuRibbon.add(helpMenu);

    // finally,
    this.mainPanel.add(menuRibbon, BorderLayout.PAGE_START);
  }

  /**
   * Initializes the swap and new layer buttons.
   */
  private void setLayerButtonsPanel() {
    layerButtonsPanel.setLayout(new FlowLayout());
    JButton swapBtn = new JButton("Swap Layers");
    swapBtn.setActionCommand("swap");
    swapBtn.addActionListener(this);
    layerButtonsPanel.add(swapBtn);
    JButton newBtn = new JButton("New layer");
    newBtn.setActionCommand("new");
    newBtn.addActionListener(this);
    layerButtonsPanel.add(newBtn);
    // Adding Area panel
    layerAreaPanel.setLayout(new BoxLayout(layerAreaPanel, BoxLayout.Y_AXIS));
    layerAreaPanel.setBorder(BorderFactory.createTitledBorder("LAYERS"));
    layerAreaPanel.add(layerButtonsPanel);
  }


  /**
   * Initializes the panel for the layers.
   */
  private void layersPanel() {
    // setting up the layers panel:
    renderLayers();
    layerAreaPanel.add(layersPanel);
    mainPanel.add(layerAreaPanel, BorderLayout.LINE_START);
  }

  /**
   * Initializes the panel for the console.
   */
  private void console() {
    // setting up the console
    consolePanel = new JPanel();
    consoleTxt = new JTextArea();
    consolePanel.add(consoleTxt);
    consoleTxt.setEditable(false);
    JScrollPane scrollableConsole = new JScrollPane(consolePanel);
    scrollableConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollableConsole.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT / 10));
    mainPanel.add(scrollableConsole, BorderLayout.PAGE_END);
  }

  /**
   * Initializes the panel for the script.
   */
  private void scriptArea() {
    // setting up the script area
    JPanel scriptPanel = new JPanel();
    scriptPanel.setLayout(new BoxLayout(scriptPanel, BoxLayout.Y_AXIS));
    scriptArea = new JTextArea();
    scriptArea.setPreferredSize(new Dimension(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 2));
    scriptArea.setBorder(BorderFactory.createTitledBorder("Type script here"));

    scriptPanel.add(scriptArea);

    JPanel scriptBtnsPanel = new JPanel();
    scriptBtnsPanel.setLayout(new FlowLayout());

    JButton runScriptBtn = new JButton("Run script");
    runScriptBtn.addActionListener(this);
    runScriptBtn.setActionCommand("run script");
    scriptBtnsPanel.add(runScriptBtn);

    JButton loadScriptBtn = new JButton("Load script");
    loadScriptBtn.addActionListener(this);
    loadScriptBtn.setActionCommand("load script");
    scriptBtnsPanel.add(loadScriptBtn);

    scriptPanel.add(scriptBtnsPanel, BoxLayout.Y_AXIS);

    mainPanel.add(scriptPanel, BorderLayout.LINE_END);

  }

  /**
   * Initializes the panel for the main image to appear.
   */
  private void imageArea() {

    this.imageScrollPanel = new JScrollPane(this.imgLabel);
    imageScrollPanel.setPreferredSize(new Dimension(700, 700));

    mainPanel.add(imageScrollPanel, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (!(actionsMap.containsKey(e.getActionCommand()))) {
      return; // throw new IllegalArgumentException("command not recognized");
    }
    actionsMap.get(e.getActionCommand()).execute();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (!(actionsMap.containsKey(((JCheckBox) e.getItemSelectable()).getActionCommand()))) {
      return;
    }
    actionsMap.get(((JCheckBox) e.getItemSelectable()).getActionCommand()).execute();
  }


  @Override
  public void renderLayers() {
    allLayers.clear();
    for (int i = 0; i < model.getLayers().size(); i++) {
      allLayers.add(createLayerRow(i, !model.getLayers().get(i).isInvisible()));
    }
    layersPanel.removeAll();
    for (JPanel row : allLayers) {
      layersPanel.add(row);
    }
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.pack();
  }


  /**
   * Creates a new layer row given the parameters for the layer.
   *
   * @param layerNum the number of the layer.
   * @param visible  if the layer is visible or not.
   * @return a {@link JPanel} with the new layer.
   * @throws IllegalArgumentException if the layer number is invalid.
   */
  public JPanel createLayerRow(int layerNum, boolean visible) {
    // Layer Button
    JPanel thisRow = new JPanel();
    thisRow.setLayout(new FlowLayout());
    JButton layerBtn = new JButton("Layer | " + layerNum);
    layerBtn.setOpaque(false);
    layerBtn.setBackground(this.defaultTheme.getPrimary());
    actionsMap.putIfAbsent("currentLayerWithIndex " + layerNum,
        new CurrentLayerWithIndexCommand(this.model, this, layerNum));
    layerBtn.setActionCommand("currentLayerWithIndex " + layerNum);
    layerBtn.addActionListener(this);
    // Visibility Checkbox
    JCheckBox layerCB = new JCheckBox("visible?", true);
    layerCB.setBackground(this.defaultTheme.getPrimary());
    layerCB.setOpaque(false);
    actionsMap
        .putIfAbsent("visible " + layerNum, new VisibleLayerCommand(this.model, this, layerNum));
    layerCB.setActionCommand("visible " + layerNum);
    layerCB.addItemListener(this);
    // Delete Button
    JButton deleteBtn = new JButton("Delete");
    deleteBtn.setOpaque(false);
    deleteBtn.setBackground(this.defaultTheme.getPrimary());
    actionsMap.putIfAbsent("delete " + layerNum, new DeleteLayerWithIndexCommand(this.model, this,
        layerNum));
    deleteBtn.setActionCommand("delete " + layerNum);
    deleteBtn.addActionListener(this);

    thisRow.add(layerBtn);
    thisRow.add(layerCB);
    thisRow.add(deleteBtn);
    thisRow.setOpaque(false);
    this.allLayers.add(thisRow);
    return thisRow;

  }

  /**
   * Returns a Hashmap of the string commands to the command objects. Helps to implement the command
   * pattern for all listeners of the supported events in the GUI frame.
   *
   * @return a {@link HashMap} of the commands from a string to their function objects that execute
   * the promised action.
   */
  public Map<String, IGUICommand> initActionsMap() {

    Map<String, IGUICommand> actionsMap = new HashMap<>();
    GUIView frame = this;
    actionsMap.putIfAbsent("new", new NewLayerCommand(model, this));
    actionsMap.putIfAbsent("mosaic", new GUIMosaicCommand(model, this));
    actionsMap.putIfAbsent("import one", new ImportOneCommand(model, this));
    actionsMap.putIfAbsent("sepia", new OperationCommand(model, this, new Sepia()));
    actionsMap.putIfAbsent("greyscale", new OperationCommand(model, this, new Greyscale()));
    actionsMap.putIfAbsent("sharpen", new OperationCommand(model, this, new Sharpening()));
    actionsMap.putIfAbsent("blur", new OperationCommand(model, this, new ImageBlur()));
    actionsMap.putIfAbsent("downscale", new DownScaleCommand(model, this));
    actionsMap.putIfAbsent("export one", new ExportOneCommand(model, this));
    actionsMap.putIfAbsent("set current layer", new CurrentLayerCommand(model, frame));
    actionsMap.putIfAbsent("checkerboard", new CheckerBoardCommand(model, frame));
    actionsMap.putIfAbsent("delete", new DeleteLayerCommand(model, this));
    actionsMap.putIfAbsent("bw noise", new BWNoiseCommand(model, this));
    actionsMap.putIfAbsent("rainbow noise", new RainbowNoiseCommand(model, this));
    actionsMap.putIfAbsent("pure noise", new PureNoiseCommand(model, this));
    actionsMap.putIfAbsent("custom noise", new CustomNoiseCommand(model, this));
    actionsMap.putIfAbsent("undo", new UndoCommand(model, this));
    actionsMap.putIfAbsent("redo", new RedoCommand(model, this));
    actionsMap.putIfAbsent("run script", new RunScriptCommand(model, this));
    actionsMap.putIfAbsent("load script", new LoadScriptCommand(model, this));
    actionsMap.putIfAbsent("light theme", new ThemeCommand(model, this, LIGHT_THEME));
    actionsMap.putIfAbsent("dark theme", new ThemeCommand(model, this, DARK_THEME));
    actionsMap.putIfAbsent("matrix theme", new ThemeCommand(model, this, MATRIX_THEME));
    actionsMap.putIfAbsent("retro theme", new ThemeCommand(model, this, RETRO_THEME));
    actionsMap.putIfAbsent("swap", new SwapLayersCommand(model, this));
    actionsMap.putIfAbsent("github", new ViewGitHubCommand(model, this));
    actionsMap.putIfAbsent("import all", new ImportAllCommand(model, this));
    actionsMap.putIfAbsent("export all", new ExportAllCommand(model, this));
    actionsMap.putIfAbsent("import image to all layers", new ImportImageToAllLayers(model, this));

    return actionsMap;
  }


  /**
   * Returns a HashMap of the format objects to their string representations.
   *
   * @return a {@link HashMap} of the format strings and objects.
   */
  public static Map<String, IFileFormat> initFormatsMap() {
    final Map<String, IFileFormat> formatsMap = new HashMap<>();

    formatsMap.putIfAbsent("jpg", new JPEGFile());
    formatsMap.putIfAbsent("jpeg", new JPEGFile());
    formatsMap.putIfAbsent("ppm", new PPMFile());
    formatsMap.putIfAbsent("png", new PNGFile());

    return formatsMap;

  }

  @Override
  public void write(String toWrite) throws IllegalStateException, IllegalArgumentException {

    if (toWrite == null) {
      throw new IllegalArgumentException("Argument is null");
    }
    consoleTxt.append(toWrite);
    consoleTxt.append("\n");
  }

  /**
   * Returns the file extension for the given fileName.
   *
   * @param fileName the file name to get the extension of.
   * @return a string with the file extension including the dot.
   */
  public static String getFileExtension(String fileName)
      throws IllegalArgumentException {
    return fileName.substring(fileName.lastIndexOf('.'));
  }

  /**
   * Sets the image in the GUI to the current one.
   */
  public void setImage() {
    try {
      if (!model.getCurrentLayer().isInvisible()) {
        imgLabel.setIcon(new ImageIcon(model.getImage().getBufferedImage()));
      } else {
        imgLabel.setIcon(new ImageIcon());
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Displays a popup error message with the given message and title.
   *
   * @param dialogMsg the message text.
   * @param title     the title of the popup.
   */
  public void errorPopup(String dialogMsg, String title)
      throws IllegalArgumentException {
    JOptionPane.showMessageDialog(GUIView.this,
        Utility.checkNotNull(dialogMsg, "cannot display a popup window"
            + " with a null dialog message"),
        Utility.checkNotNull(title, "cannot display a popup window"
            + " with a null title"),
        JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a popup prompting the user for input, and saves the user response.
   *
   * @param prompt the prompt for the user to respond to.
   * @return the user's input as a String.
   * @throws IllegalArgumentException if the given input is invalid.
   */
  public String getDialogInput(String prompt)
      throws IllegalArgumentException {
    return JOptionPane.showInputDialog(Utility.checkNotNull(prompt, "cannot create a "
        + "dialog box with no prompt"));
  }

  /**
   * Converts a Java Color object into an {@link IPixel} object.
   *
   * @param c the color object.
   * @return an {@link IPixel} object with the same value as the given color object.
   */
  public static IPixel colorToIPixel(Color c) {
    return new PixelImpl(c.getRed(), c.getGreen(), c.getBlue());
  }

}