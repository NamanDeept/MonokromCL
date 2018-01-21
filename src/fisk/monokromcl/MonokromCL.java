package fisk.monokromcl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/*
    A command line tool to convert colour images to monochrome dithered images.
 */
public class MonokromCL {

  @Parameter(names = {"-debug"})
  public static boolean debug = false;

  @Parameter(names = {"-source", "-s"}, converter = FileConverter.class)
  private File sourceFile;

  @Parameter(names = {"-target", "-t"}, converter = FileConverter.class)
  private File destinationFile = null;

  @Parameter(names = {"-dither", "-d"})
  private String dittherType = null;

  @Parameter(names = {"-threshold", "-th"})
  private int threshold = -1;

  @Parameter(names = {"-svg"})
  private boolean exportSVG = false;

  @Parameter(names = {"-help", "?"}, help = true)
  private boolean help;

  public static void main(String... args) {
    MonokromCL main = new MonokromCL();
    JCommander.newBuilder()
        .addObject(main)
        .build()
        .parse(args);
    main.run(args);
  }

  public void run(String... args) {
    Out.print("");
    Out.print("MonokromCL");

    if (help || args == null || args.length == 0) {
      showHelp();
    } else {
      Log.d(String.format("debug: %s", debug));
      Log.d(String.format("type: %s", dittherType));

      boolean validType = false;
      for (String type : Dithering.getNames()) {
        if (type.equals(dittherType)) {
          validType = true;
        }
      }

      if (!validType) {
        Out.print(String.format("Invalid dither type: %s", dittherType));
        showHelp();
        System.exit(1);
      }

      if (sourceFile.exists()) {
        Out.print(String.format("Source: %s", sourceFile.getAbsolutePath()));
        readImage();
      } else {
        Out.print(String.format("Source: %s not found", sourceFile.getAbsolutePath()));
        System.exit(1);
      }
    }
  }

  private void readImage() {
    try {
      BufferedImage sourceImage = ImageIO.read(sourceFile);
      Log.d(sourceImage.toString());
      Out.print("Processing image...");
      if (threshold == -1) {
        threshold = 128;
      }
      BufferedImage dithered = Dithering.dither(dittherType, sourceImage, threshold);
      if (dithered == null) {
        Out.fatalError("Could not process image - that's all we know.");
      }

      if(exportSVG){
        File outputSVG = null;
        if (destinationFile != null) {
          String destinationPath = destinationFile.getAbsolutePath();
          String suffix = destinationPath.substring(destinationPath.lastIndexOf(".") + 1, destinationPath.length());
          if (!suffix.toLowerCase().equals("svg")) {
            Out.fatalError("When exporting SVG target file must end with .svg");
          }else{
            outputSVG = destinationFile;
          }
        }else{
          String sourcePath = sourceFile.getAbsolutePath();
          sourcePath = sourcePath.substring(0, sourcePath.lastIndexOf("."));

          String newPath = String.format("%s_svg_%d.%s", sourcePath, System.currentTimeMillis(), "svg");
          Out.print(String.format("No target file supplied, using %s", newPath));
          outputSVG = new File(newPath);
        }

        ImageToSVG.process(dithered, outputSVG, () -> Out.print("SVG exported"));
      }else if (destinationFile != null) {
        Out.print("Saving dithered image to " + destinationFile.getAbsolutePath());
        String destinationPath = destinationFile.getAbsolutePath();
        String suffix = destinationPath.substring(destinationPath.lastIndexOf(".") + 1, destinationPath.length());
        ImageIO.write(dithered, suffix, destinationFile);
      } else {
        String sourcePath = sourceFile.getAbsolutePath();
        String suffix = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length());
        sourcePath = sourcePath.substring(0, sourcePath.lastIndexOf("."));

        String newPath = String.format("%s_dithered_%d.%s", sourcePath, System.currentTimeMillis(), suffix);
        Out.print(String.format("No target file supplied, using %s", newPath));
        File outputfile = new File(newPath);
        ImageIO.write(dithered, suffix, outputfile);
      }
    } catch (IOException e) {
      e.printStackTrace();
      Out.fatalError(e);
    }
  }

  private void showHelp() {
    Out.print("");
    Out.print("Usage:");
    Out.print("-debug - turn on debug log output");
    Out.print("-d, -dither - dither algorithm (see below)");
    Out.print("-s, -source - path to source image");
    Out.print("-t, -target - path to save file");
    Out.print("-th, -threshold - default is 128");
    Out.print("-svg - output to SVG");
    Out.print("?, -help - display this help");
    Out.print("");
    Out.print("Available dither types:");
    for (String type : Dithering.getNames()) {
      Out.print("  " + type);
    }
    Out.print("");
  }
}
