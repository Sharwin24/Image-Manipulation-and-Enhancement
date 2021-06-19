import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.Utility;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.MockMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests for methods in the {@link cs3500.controller.MultiLayerIMEControllerImpl} class.
 */
public class MultiLayerIMEControllerImplTest {

  /**
   * Tests for the "undo" command.
   */
  @Test
  public void testUndoWhenCantUndo() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to undo: Nothing to Undo\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to undo: Nothing to Undo\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to undo: Nothing to Undo\n", "undo \n undo \n undo"
    ));
  }

  @Test
  public void testUndoWhenCanUndo() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "applying operations: [Sepia]\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "successfully undone\n",
        "programmatic purenoise 123 123 12\n apply sepia\n undo"
    ));
  }

  @Test
  public void testUndoAfterBadInput() {
    assertTrue( // ignored input
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n",
            "bla undo"
        )
    );
  }

  /**
   * Tests for the "redo" command.
   */
  @Test
  public void testRedoWhenCantRedo() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to redo: Nothing to Redo\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to redo: Nothing to Redo\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Failed to redo: Nothing to Redo\n", "redo \n redo \n redo"
    ));
  }

  @Test
  public void testRedoWhenCanRedo() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "applying operations: [Sepia]\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "successfully undone\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "successfully redone\n",
        "programmatic purenoise 123 123 12\n apply sepia\n undo\n redo"
    ));
  }

  @Test
  public void testRedoAfterBadInput() {
    assertTrue( // ignored input
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n",
            "bla redo"
        )
    );
  }



  /**
   * Tests for the "save" command.
   */
  @Test
  public void testSave() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "saved current image to image history\n",
            "save"
        )
    );
  }

  @Test
  public void testSaveAfterBadInput() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n",
            "bla save"
        )
    );
  }



  /**
   * Tests for the "import" command.
   */
  @Test
  public void testImportLayersNoFileFound() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "",
            "import JPEG layers notAFile.txt"
        )
    );
  }

  @Test
  public void testImportLayersFileFound() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file layers...\n" // FIXME
                + "\n"
                + "failed to import from JPEG file at path layers: Failed to read Image\n",
            "import JPEG layers res/exampleExportedLayersFile.txt"
        )
    );
  }


  @Test
  public void testImportJPEGCommandSuccessfulUsedPathName() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n",
        "import JPEG res/rover.jpg" // exists
    ));
  }


  @Test
  public void testImportPPMCommandSuccessfulUsedPathName() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from PPM file res/rover.ppm...\n"
            + "\n"
            + "successfully imported!\n",
        "import PPM res/rover.ppm" // exists
    ));
  }


  @Test
  public void testImportPNGCommandSuccessfulUsedPathName() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from PNG file res/rover.png...\n"
            + "\n"
            + "successfully imported!\n",
        "import PNG res/rover.png" // exists
    ));
  }


  @Test
  public void testImportCommandUnsuccessfulDidntUsedPathName() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file rover.jpg...\n"
            + "\n"
            + "failed to import from JPEG file at path rover.jpg: Failed to read Image\n",
        "import JPEG rover.jpg" // does not exist
    ));
  }

  @Test
  public void testImportCommandUnsuccessfulForgotExtension() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover...\n"
            + "\n"
            + "failed to import from JPEG file at path res/rover: Failed to read Image\n",
        "import JPEG res/rover"
    ));
  }

  @Test
  public void testImportCommandUnsuccessfulMismatchedFileType() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from PNG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n",
        "import PNG res/rover.jpg"
    ));
  }

  @Test
  public void testImportCommandUnsuccessfulNonExistentFileType() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "invalid file format: \"FOO\"\n",
        "import FOO res/rover.jpg"
    ));
  }



  @Test
  public void testImportCommandUnsuccessfulNoFileFormatGiven() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n",
        "import res/rover.jpg"
    ));
  }

  @Test
  public void testImportCommandUnsuccessfulNoPathGiven() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n",
        "import JPEG"
    ));
  }



  @Test
  public void testImportCommandSuccessfulUsedPathName() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n",
        "import JPEG res/rover.jpg"
    ));
  }




  /**
   * Tests for the "export" command.
   */
  @Test
  public void testExportJPEGCommandSuccessful() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "exporting to JPEG file res/rover...\n"
            + "\n"
            + "should've exported by now\n",
        "import JPEG res/rover.jpg\n export JPEG res/rover"
    ));
  }

  @Test
  public void testExportAllLayers() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "",
            ""
        )
    );
  }

  @Test
  public void testExportPPMCommandSuccessful() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "exporting to PPM file res/rover...\n"
            + "\n"
            + "should've exported by now\n",
        "import JPEG res/rover.jpg\n export PPM res/rover"
    ));
  }


  @Test
  public void testExportPNGCommandSuccessful() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "exporting to PNG file res/rover...\n"
            + "\n"
            + "should've exported by now\n",
        "import JPEG res/rover.jpg\n export PNG res/rover"
    ));
  }

  @Test
  public void testExportCommandSuccessfulWhenForgotExtension() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "exporting to JPEG file res/rover...\n"
            + "\n"
            + "should've exported by now\n",
        "import JPEG res/rover.jpg\n export JPEG res/rover"
    ));
  }

  @Test
  public void testExportCommandUnsuccessfulNonExistentFileType() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "invalid file format: \"FOO\"\n",
        "import JPEG res/rover.jpg\n export FOO res/rover"
    ));
  }



  @Test
  public void testExportCommandUnsuccessfulNoFileFormatGiven() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n",
        "import JPEG res/rover.jpg\n export res/rover"
    ));
  }

  @Test
  public void testExportCommandUnsuccessfulNoPathGiven() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "importing from JPEG file res/rover.jpg...\n"
            + "\n"
            + "successfully imported!\n"
            + "\n"
            + " | Visibility: true\n",
        "import JPEG res/rover.jpg\n export JPEG"
    ));
  }


  /**
   * Tests for the "apply" command.
   */
  @Test
  public void testApplyAllCommandsSuccessfully() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n",
            "import JPEG res/rover.jpg\n apply sepia greyscale blur sharpen"
        )
    );
  }

  @Test
  public void testApplyAllCommandsSuccessfullySkippingInvalidCommands() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "did not recognize operation \"foo\"\n"
                + "\n"
                + "did not recognize operation \"bar\"\n"
                + "\n"
                + "did not recognize operation \"baz\"\n"
                + "\n"
                + "did not recognize operation \"quux\"\n"
                + "\n"
                + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n",
            "import JPEG res/rover.jpg\n apply foo sepia bar greyscale baz blur quux "
                + "sharpen"
        )
    );
  }

  @Test
  public void testApplyAllCommandsSuccessfullyButStopsWhenSeesRepeatApplyOnSameLine() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "did not recognize operation \"apply\"\n"
                + "\n"
                + "applying operations: [Sepia, Greyscale, ImageBlur, Sharpening]\n",
            "import JPEG res/rover.jpg\n apply sepia greyscale apply blur sharpen"
        )
    );
  }

  @Test
  public void testApplyAllCommandsSuccessfullyButContinuesWhenSeesRepeatApplyOnNextLine() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "applying operations: [Sepia, Greyscale]\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "applying operations: [ImageBlur, Sharpening]\n",
            "import JPEG res/rover.jpg\n apply sepia greyscale\n apply blur sharpen"
        )
    );
  }

  @Test
  public void testApplyNoArgs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "applying operations: []\n"
                + "\n"
                + "could not apply operations: Invalid operations\n",
            "import JPEG res/rover.jpg\n apply"
        )
    );
  }

  @Test
  public void testApplyInvalidArgs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "importing from JPEG file res/rover.jpg...\n"
                + "\n"
                + "successfully imported!\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "did not recognize operation \"asdjfo;uiasedhr\"\n"
                + "\n"
                + "did not recognize operation \"234123423\"\n"
                + "\n"
                + "applying operations: []\n"
                + "\n"
                + "could not apply operations: Invalid operations\n",
            "import JPEG res/rover.jpg\n apply asdjfo;uiasedhr  234123423"
        )
    );
  }


  /**
   * Tests for the "visibility" command.
   */
  @Test
  public void testToggleVisibilityBadInput() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "could not toggle visibility of layer \"asdfg\": "
                + "layers must be an integer in valid range of the number of present layers\n",
            "visibility asdfg"
        )
    );
  }

  @Test
  public void testToggleVisibilityBadLayerNumber() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "toggling visibility of layer -1\n"
                + "\n"
                + "could not toggle visibility of layer -1: Layer Index out of bounds\n",
            "visibility -1"
        )
    );
  }

  @Test
  public void testToggleVisibilityValidLayer() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "toggling visibility of layer 0\n"
                + "\n"
                + "toggled!\n",
            "visibility 0"
        )
    );
  }

  /**
   * Tests for the "current" command.
   */
  @Test
  public void testCurrentInvalidLayerName() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "invalid layer number: klasdfj: provide a number\n",
            "current klasdfj"
        )
    );
  }

  @Test
  public void testCurrentCurrentLayerName() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "setting the current working layer to layer #0\n",
            "current 0"
        )
    );
  }

  @Test
  public void testCurrentInvalidLayerNumber() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "setting the current working layer to layer #8\n"
                + "\n"
                + "invalid layer number: 8, out of bounds for layers 0-0\n",
            "current 8"
        )
    );
  }



  /**
   * Tests for the "delete" command.
   */

  @Test
  public void testDeleteInvalidLayer() {
    assertTrue(this.utilityTestViewOutputFromController(
        "\n"
            + "Welcome to Image Manipulation and Enhancement! \n"
            + "Please consult the USEME file for information on how to specify commands\n"
            + "\n"
            + " | Visibility: true\n"
            + "\n"
            + "Illegal index bla. Must be an integer in the inclusive range [0,1]\n",
        "delete bla"
    ));
  }

  @Test
  public void testDeleteOnlyLayer() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "deleting layer 0\n"
                + "\n"
                + "could not delete layer 0: Cannot delete last layer\n",
            "delete 0"
        )
    );
  }

  @Test
  public void testDeleteOtherLayer() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "deleting layer 1\n",
            "new \n delete 1"
        )
    );
  }

  @Test
  public void testDeleteInvalidLayerNumber() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "deleting layer 1000\n"
                + "\n"
                + "could not delete layer 1000: Layer Index out of bounds\n",
            "delete 1000"
        )
    );
  }


  /**
   * Tests for the "swap" command.
   */
  @Test
  public void testSwapLayerWithItself() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "swapping layers 1 and 1\n"
                + "\n"
                + "could not swap layers at indices 1 and 1: Layer Indexes invalid\n",
            "new \n swap 1 1"
        )
    );
  }

  @Test
  public void testSwapLayerWithOther() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "swapping layers 0 and 1\n"
                + "\n"
                + "swapped!\n",
            "new \n swap 0 1"
        )
    );
  }

  @Test
  public void testSwapLayersOnlyOneInput() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n",
            "swap 0"
        )
    );
  }

  @Test
  public void testSwapLayerTooManyInputsThirdOnwardGetsIgnored() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "swapping layers 0 and 1\n"
                + "\n"
                + "swapped!\n",
            "new \n swap 0 1 1 1 0 10 100 "
        )
    );
  }

  /**
   * Tests for the "new" command.
   */
  @Test
  public void testNewLayerBeforeDeleting() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n",
            "new"
        )
    );
  }

  @Test
  public void testNewLayerAfterDeleting() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "adding new layer at index 2\n"
                + "\n"
                + "added!\n"
                + "\n"
                + " | Visibility: true | Visibility: true | Visibility: true\n"
                + "\n"
                + "deleting layer 1\n"
                + "\n"
                + " | Visibility: true | Visibility: true\n"
                + "\n"
                + "adding new layer at index 2\n"
                + "\n"
                + "added!\n",
            "new \n new \n delete 1 \n new"
        )
    );
  }



  /**
   * Tests for the "programmtic" command.
   */
  @Test
  public void testProgrammaticCheckerboardValidInputs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n",
            "new"
        )
    );
  }

  @Test
  public void testProgrammaticPureNoiseValidInputs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n",
            "new"
        )
    );
  }

  @Test
  public void testProgrammaticBWNoiseValidInputs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n",
            "new"
        )
    );
  }

  @Test
  public void testProgrammaticRainbowNoiseValidInputs() {
    assertTrue(
        this.utilityTestViewOutputFromController(
            "\n"
                + "Welcome to Image Manipulation and Enhancement! \n"
                + "Please consult the USEME file for information on how to specify commands\n"
                + "\n"
                + " | Visibility: true\n"
                + "\n"
                + "adding new layer at index 1\n"
                + "\n"
                + "added!\n",
            "new"
        )
    );
  }

  @Test
  public void testProgrammaticImageCommandNotEnoughParameters() {

  }






  /**
   * Utility method to help abstract testing for the correct feedback being logged by the controller
   * and mock.
   * @param expected The expected I/O output messages.
   * @param inputToTest mock input from the controller.
   * @return whether the expected output is the real output.
   */
  private boolean utilityTestViewOutputFromController(String expected, String inputToTest) {
    Utility.checkNotNull(expected, "expected message cannot be null");
    Utility.checkNotNull(inputToTest, "checked message cannot be null");
    Appendable out = new StringBuilder();
    Readable in = new StringReader(inputToTest);

    IMultiLayerIMEController ctrlr =
        MultiLayerIMEControllerImpl.controllerBuilder().model(new MultiLayerModelImpl())
        .appendable(out).readable(in).buildController();

    ctrlr.run();

    assertEquals(expected, out.toString());

    return expected.equals(out.toString());
  }
}
