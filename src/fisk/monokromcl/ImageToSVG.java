package fisk.monokromcl;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageToSVG {

  private static final String SVG_HEADER_TEMPLATE =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"+
          "\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\" " +
          "\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n" +
          "<svg xmlns=\"http://www.w3.org/2000/svg\" " +
          "xmlns:xlink=\"http://www.w3.org/1999/xlink\" " +
          "viewBox=\"0 0 %d %d\" " +
          "xml:space=\"preserve\" " +
          "width=\"%dpx\" " +
          "height=\"%dpx\">\n" +
          "<g>\n";

  public static void process(BufferedImage source, File outputFile, ImageToSVGListener listener) throws IOException {
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(outputFile.getAbsolutePath()));
    } catch (IOException e) {
      e.printStackTrace();
      Out.fatalError("Could not save file:\n" + e.toString());
      return;
    }

    int width = source.getWidth();
    int height = source.getHeight();

    writer.write(String.format(SVG_HEADER_TEMPLATE, width, height, width, height));

    int lineStartX = -1;

    for (int y = 0 ; y < height ; y++){
      for(int x = 0 ; x < width ; x++){

        if(x <5){
          continue;
        }

        int pixel = source.getRGB(x, y);

        //If pixel is black
        if((pixel & 0x00FFFFFF) == 0) {
          if(lineStartX != -1){
            //we're in a line
            if (x < width - 2) {
              continue;
            }else{
              String line = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />\n";
              writer.write(String.format(line, lineStartX, y, x+1, y));
              lineStartX = -1;
            }
          }
          //Get next pixel:
          if (x < width - 1) {
            int nextPixel = source.getRGB(x + 1, y);
            if((nextPixel & 0x00FFFFFF) == 0) {
              //Next pixel is black - so we're drawing a line
              lineStartX = x;
            }else{
              drawDot(writer, x, y);
            }
          }else{
            //The end of the line...
            drawDot(writer, x, y);
          }
        }else{
          //Pixel is white:
          if(lineStartX != -1) {
            String line = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />\n";
            writer.write(String.format(line, lineStartX, y, x+1, y));
            lineStartX = -1;
          }
        }
      }
    }


    writer.write("\n</g>\n</svg>");
    writer.close();
    listener.svgExported();

  }

  private static void drawDot(BufferedWriter writer, int x, int y) throws IOException {
    String line = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />\n";
    writer.write(String.format(line, x, y, x+1, y));
  }

  public interface ImageToSVGListener{
    void svgExported();
  }
}
