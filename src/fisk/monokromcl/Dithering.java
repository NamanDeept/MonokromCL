package fisk.monokromcl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * Gray Scale Dithering
 *
 * @author safeallah ramezanzadeh (safecomp)
 *
 *
 * http://safecomp.ir
 *
 * My Tutorial about Dithering : http://www.safecomp.ir/fa/node/167
 *
 *
 *  Adapted for Java by JFisher (fiskdebug)
 *
 */
public class Dithering {

  public enum Method{
    ordered2By2Bayer,
    ordered3By3Bayer,
    ordered4By4Bayer,
    ordered8By8Bayer,
    floydSteinberg,
    jarvisJudiceNinke,
    sierra,
    twoRowSierra,
    sierraLite,
    atkinson,
    stucki,
    burkes,
    falseFloydSteinberg,
    simpleLeftToRightErrorDiffusion,
    randomDithering,
    simpleThreshold
  }

  private static int threshold = 128;

  public static String[] getNames() {
    return Arrays.stream(Method.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }

  public static BufferedImage dither(String method, BufferedImage source, int _threshold){
    threshold = _threshold;

    Out.print(String.format("Dither type: %s threshold: %d", method, threshold));

    switch(Method.valueOf(method)){
      case ordered2By2Bayer:
        return ordered2By2Bayer(source);
      case ordered3By3Bayer:
        return ordered3By3Bayer(source);
      case ordered4By4Bayer:
        return ordered4By4Bayer(source);
      case ordered8By8Bayer:
        return ordered8By8Bayer(source);
      case floydSteinberg:
        return floydSteinberg(source);
      case jarvisJudiceNinke:
        return jarvisJudiceNinke(source);
      case sierra:
        return sierra(source);
      case twoRowSierra:
        return twoRowSierra(source);
      case sierraLite:
        return sierraLite(source);
      case atkinson:
        return atkinson(source);
      case stucki:
        return stucki(source);
      case burkes:
        return burkes(source);
      case falseFloydSteinberg:
        return falseFloydSteinberg(source);
      case simpleLeftToRightErrorDiffusion:
        return simpleLeftToRightErrorDiffusion(source);
      case randomDithering:
        return randomDithering(source);
      case simpleThreshold:
        return simpleThreshold(source);
      default:
        return null;

    }
  }

  public static BufferedImage dither(Method method, BufferedImage source, int _threshold){
    threshold = _threshold;

    switch(method){
      case ordered2By2Bayer:
        return ordered2By2Bayer(source);
      case ordered3By3Bayer:
        return ordered3By3Bayer(source);
      case ordered4By4Bayer:
        return ordered4By4Bayer(source);
      case ordered8By8Bayer:
        return ordered8By8Bayer(source);
      case floydSteinberg:
        return floydSteinberg(source);
      case jarvisJudiceNinke:
        return jarvisJudiceNinke(source);
      case sierra:
        return sierra(source);
      case twoRowSierra:
        return twoRowSierra(source);
      case sierraLite:
        return sierraLite(source);
      case atkinson:
        return atkinson(source);
      case stucki:
        return stucki(source);
      case burkes:
        return burkes(source);
      case falseFloydSteinberg:
        return falseFloydSteinberg(source);
      case simpleLeftToRightErrorDiffusion:
        return simpleLeftToRightErrorDiffusion(source);
      case randomDithering:
        return randomDithering(source);
      case simpleThreshold:
        return simpleThreshold(source);
      default:
        return null;

    }
  }




  /*
   * 2 by 2 Bayer Ordered Dithering
   *
   * 1 3
   * 4 2
   *
   * (1/5)
   *
   */
  public static BufferedImage ordered2By2Bayer(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int matrix[][] = {
        { 1, 3 },
        { 4, 2 },
    };

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        gray = gray + (gray * matrix[x % 2][y % 2]) / 5;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }
  /*
   * 3 by 3 Bayer Ordered Dithering
   *
   *  3 7 4
   *  6 1 9
   *  2 8 5
   *
   *  (1/10)
   */
  public static BufferedImage ordered3By3Bayer(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int matrix[][] = {
        { 3, 7, 4},
        { 6, 1, 9 },
        { 2, 8, 5 },
    };

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        gray = gray + (gray * matrix[x % 3][y % 3]) / 10;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }


  /*
   * 4 by 4 Bayer Ordered Dithering
   *
   *  1 9 3 11
   *  13 5 15 7
   *  4 12 2 10
   *  16 8 14 6
   *  (1/17)
   */
  public static BufferedImage ordered4By4Bayer(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int matrix[][] = {
        { 1, 9, 3, 11 },
        { 13, 5, 15, 7 },
        { 4, 12, 2, 10 },
        { 16, 8, 14, 6 } };

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        gray = gray + (gray * matrix[x % 4][y % 4]) / 17;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }


  /*
   * 8 by 8 Bayer Ordered Dithering
   *
   *  1 49 13 61 4 52 16 64
   *  33 17 45 29 36 20 48 32
   *  9 57 5 53 12 60 8 56
   *  41 25 37 21 44 28 40 24
   *  3 51 15 63 2 50 14 62
   *  35 19 47 31 34 18 46 30
   *  11 59 7 55 10 58 6 54
   *  43 27 39 23 42 26 38 22
   *
   *  (1/65)
   */
  public static BufferedImage ordered8By8Bayer(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int matrix[][] = {
        { 1 ,49 ,13 ,61 ,4 ,52 ,16 ,64 },
        { 33 ,17 ,45 ,29 ,36 ,20 ,48 ,32 },
        { 9 ,57 ,5 ,53 ,12 ,60 ,8 ,56 },
        { 41 ,25 ,37 ,21 ,44 ,28 ,40 ,24},
        {3 ,51 ,15 ,63 ,2 ,50 ,14 ,62},
        {35 ,19 ,47 ,31 ,34 ,18 ,46 ,30},
        {11 ,59 ,7 ,55 ,10 ,58 ,6 ,54},
        {43 ,27 ,39 ,23 ,42 ,26 ,38 ,22}
    };

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        gray = gray + (gray * matrix[x % 8][y % 8]) / 65;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }


  /*
   * Floyd-Steinberg Dithering
   *
   *   X 7
   * 3 5 1
   *
   * (1/16)
   */
  public static BufferedImage floydSteinberg(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 1; y++) {
      for (int x = 1; x < width - 1; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }
        errors[x + 1][y] += (7 * error) / 16;
        errors[x - 1][y + 1] += (3 * error) / 16;
        errors[x][y + 1] += (5 * error) / 16;
        errors[x + 1][y + 1] += (1* error) / 16;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Jarvis, Judice , Ninke Dithering
   *
   *     X 7 5
   * 3 5 7 5 3
   * 1 3 5 3 1
   *
   * (1/48)
   */
  public static BufferedImage jarvisJudiceNinke(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 2; y++) {
      for (int x = 2; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (7 * error) / 48;
        errors[x + 2][y] += (5 * error) / 48;

        errors[x - 2][y + 1] += (3 * error) / 48;
        errors[x - 1][y + 1] += (5 * error) / 48;
        errors[x][y + 1] += (7 * error) / 48;
        errors[x + 1][y + 1] += (5 * error) / 48;
        errors[x + 2][y + 1] += (3 * error) / 48;

        errors[x - 2][y + 2] += (1 * error) / 48;
        errors[x - 1][y + 2] += (3 * error) / 48;
        errors[x][y + 2] += (5 * error) / 48;
        errors[x + 1][y + 2] += (3 * error) / 48;
        errors[x + 2][y + 2] += (1 * error) / 48;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Sierra Dithering
   *
   *     X 5 3
   * 2 4 5 4 2
   *   2 3 2
   *
   * (1/32)
   */
  public static BufferedImage sierra(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 2; y++) {
      for (int x = 2; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (5 * error) / 32;
        errors[x + 2][y] += (3 * error) / 32;

        errors[x - 2][y + 1] += (2 * error) / 32;
        errors[x - 1][y + 1] += (4 * error) / 32;
        errors[x][y + 1] += (5 * error) / 32;
        errors[x + 1][y + 1] += (4 * error) / 32;
        errors[x + 2][y + 1] += (2 * error) / 32;

        errors[x - 1][y + 2] += (2 * error) / 32;
        errors[x][y + 2] += (3 * error) / 32;
        errors[x + 1][y + 2] += (2 * error) / 32;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Two-Row Sierra Dithering
   *
   *     X 4 3
   * 1 2 3 2 1
   *
   * (1/16)
   */
  public static BufferedImage twoRowSierra(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 1; y++) {
      for (int x = 2; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (4 * error) / 16;
        errors[x + 2][y] += (3 * error) / 16;

        errors[x - 2][y + 1] += (1 * error) / 16;
        errors[x - 1][y + 1] += (2 * error) / 16;
        errors[x][y + 1] += (3 * error) / 16;
        errors[x + 1][y + 1] += (2 * error) / 16;
        errors[x + 2][y + 1] += (1 * error) / 16;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Sierra Lite Dithering
   *
   *   X 2
   * 1 1
   *
   * (1/4)
   */
  public static BufferedImage sierraLite(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];

    for (int y = 0; y < height - 1; y = y+4) {
      for (int x = 1; x < width - 1; x = x+ 4) {
        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (2 * error) / 4;

        errors[x - 1][y + 1] += (1 * error) / 4;
        errors[x][y + 1] += (1 * error) / 4;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Atkinson Dithering
   *
   *   X 1 1
   * 1 1 1
   *   1
   *
   * (1/8)
   */
  public static BufferedImage atkinson(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 2; y++) {
      for (int x = 1; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += error / 8;
        errors[x + 2][y] += error / 8;

        errors[x - 1][y + 1] += error / 8;
        errors[x][y + 1] += error / 8;
        errors[x + 1][y + 1] += error / 8;

        errors[x][y + 2] += error / 8;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Stucki Dithering
   *
   *     X 8 4
   * 2 4 8 4 2
   * 1 2 4 2 1
   *
   * (1/42)
   */
  public static BufferedImage stucki(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 2; y++) {
      for (int x = 2; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (8 * error) / 42;
        errors[x + 2][y] += (4 * error) / 42;

        errors[x - 2][y + 1] += (2 * error) / 42;
        errors[x - 1][y + 1] += (4 * error) / 42;
        errors[x][y + 1] += (8 * error) / 42;
        errors[x + 1][y + 1] += (4 * error) / 42;
        errors[x + 2][y + 1] += (2 * error) / 42;

        errors[x - 2][y + 2] += (1 * error) / 42;
        errors[x - 1][y + 2] += (2 * error) / 42;
        errors[x][y + 2] += (4 * error) / 42;
        errors[x + 1][y + 2] += (2 * error) / 42;
        errors[x + 2][y + 2] += (1 * error) / 42;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * Burkes Dithering
   *
   *     X 8 4
   * 2 4 8 4 2
   *
   * (1/32)
   */
  public static BufferedImage burkes(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 1; y++) {
      for (int x = 2; x < width - 2; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }

        errors[x + 1][y] += (8 * error) / 32;
        errors[x + 2][y] += (4 * error) / 32;

        errors[x - 2][y + 1] += (2 * error) / 32;
        errors[x - 1][y + 1] += (4 * error) / 32;
        errors[x][y + 1] += (8 * error) / 32;
        errors[x + 1][y + 1] += (4 * error) / 32;
        errors[x + 2][y + 1] += (2 * error) / 32;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  /*
   * False Floyd-Steinberg Dithering
   *
   * X 3
   * 3 2
   *
   * (1/8)
   */
  public static BufferedImage falseFloydSteinberg(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    int error = 0;
    int errors[][] = new int[width][height];
    for (int y = 0; y < height - 1; y++) {
      for (int x = 1; x < width - 1; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        if (gray + errors[x][y] < threshold) {
          error = gray + errors[x][y];
          gray = 0;
        } else {
          error = gray + errors[x][y] - 255;
          gray = 255;
        }
        errors[x + 1][y] += (3 * error) / 8;
        errors[x][y + 1] += (3 * error) / 8;
        errors[x + 1][y + 1] += (2 * error) / 8;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  public static BufferedImage simpleLeftToRightErrorDiffusion(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {
      int error = 0;

      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;
        int delta;

        if (gray + error < threshold) {
          delta = gray;
          gray = 0;
        } else {
          delta = gray - 255;
          gray = 255;
        }

        if (Math.abs(delta) < 10)
          delta = 0;

        error += delta;

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  public static BufferedImage randomDithering(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();
    for (int y = 0; y < height; y++) {

      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        int threshold = (int) (Math.random() * 1000) % 256;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }

  public static BufferedImage simpleThreshold(BufferedImage src) {
    BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

    int alpha, red;
    int pixel;
    int gray;

    int width = src.getWidth();
    int height = src.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        pixel = src.getRGB(x, y);

        Color withAlpha = new Color(pixel, true);
        alpha = withAlpha.getAlpha();
        red = withAlpha.getRed();;

        gray = red;

        if (gray < threshold) {
          gray = 0;
        } else {
          gray = 255;
        }

        Color outColor = new Color(gray, gray, gray, alpha);
        out.setRGB(x, y, outColor.getRGB());
      }
    }

    return out;
  }
}