/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.paineis;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.JDesktopPane;

/**
 *
 * @author sidnei.vieira
 */
public class PainelDesktop extends JDesktopPane {

    private BufferedImage original;
    private BufferedImage scaled;

    public PainelDesktop(BufferedImage img) {
        original = img;
        scaled = original;
    }

    @Override
    public Dimension getPreferredSize() {
        return original == null ? new Dimension(200, 200) : new Dimension(original.getWidth(), original.getHeight());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        generateScaledInstance();
    }

    protected void generateScaledInstance() {
        if (original != null) {

            scaled = getScaledInstanceToFill(original, getSize());

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (scaled != null) {
            int x = (getWidth() - scaled.getWidth()) / 2;
            int y = (getHeight() - scaled.getHeight()) / 2;
            g2d.drawImage(scaled, x, y, this);
        }
        g2d.dispose();
    }

    public BufferedImage getScaledInstanceToFill(BufferedImage img, Dimension size) {
        float scaleFactor = getScaleFactorToFill(img, size);
        return getScaledInstance(img, scaleFactor);
    }

    public float getScaleFactorToFill(BufferedImage img, Dimension size) {
        float scale = 1f;
        if (img != null) {
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();
            scale = getScaleFactorToFill(new Dimension(imageWidth, imageHeight), size);
        }
        return scale;
    }

    public float getScaleFactorToFill(Dimension original, Dimension toFit) {
        float scale = 1f;
        if (original != null && toFit != null) {
            float dScaleWidth = getScaleFactor(original.width, toFit.width);
            float dScaleHeight = getScaleFactor(original.height, toFit.height);
            scale = Math.max(dScaleHeight, dScaleWidth);
        }
        return scale;
    }

    public float getScaleFactor(int iMasterSize, int iTargetSize) {
        float scale = 1;
        if (iMasterSize > iTargetSize) {
            scale = (float) iTargetSize / (float) iMasterSize;
        } else {
            scale = (float) iTargetSize / (float) iMasterSize;
        }
        return scale;
    }

    public BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor) {
        BufferedImage imgBuffer = null;
        imgBuffer = getScaledInstance(img, dScaleFactor, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
        return imgBuffer;
    }

    protected BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor, Object hint, boolean higherQuality) {

        BufferedImage scaled = img;
        if (dScaleFactor != 1.0) {
            if (dScaleFactor > 1.0) {
                scaled = getScaledUpInstance(img, dScaleFactor, hint, higherQuality);
            } else if (dScaleFactor > 0.0) {
                scaled = getScaledDownInstance(img, dScaleFactor, hint, higherQuality);
            }
        }

        return scaled;

    }

    protected BufferedImage getScaledDownInstance(BufferedImage img, double dScaleFactor, Object hint, boolean higherQuality) {

        int targetWidth = (int) Math.round(img.getWidth() * dScaleFactor);
        int targetHeight = (int) Math.round(img.getHeight() * dScaleFactor);

        int type = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage) img;

        if (targetHeight > 0 || targetWidth > 0) {
            int w, h;
            if (higherQuality) {
                w = img.getWidth();
                h = img.getHeight();
            } else {
                w = targetWidth;
                h = targetHeight;
            }

            do {
                if (higherQuality && w > targetWidth) {
                    w /= 2;
                    if (w < targetWidth) {
                        w = targetWidth;
                    }
                }

                if (higherQuality && h > targetHeight) {
                    h /= 2;
                    if (h < targetHeight) {
                        h = targetHeight;
                    }
                }

                BufferedImage tmp = new BufferedImage(Math.max(w, 1), Math.max(h, 1), type);
                Graphics2D g2 = tmp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
                g2.drawImage(ret, 0, 0, w, h, null);
                g2.dispose();

                ret = tmp;
            } while (w != targetWidth || h != targetHeight);
        } else {
            ret = new BufferedImage(1, 1, type);
        }
        return ret;
    }

    protected BufferedImage getScaledUpInstance(BufferedImage img,
            double dScaleFactor,
            Object hint,
            boolean higherQuality) {

        int targetWidth = (int) Math.round(img.getWidth() * dScaleFactor);
        int targetHeight = (int) Math.round(img.getHeight() * dScaleFactor);

        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {

            w = img.getWidth();
            h = img.getHeight();

        } else {

            w = targetWidth;
            h = targetHeight;

        }

        do {

            if (higherQuality && w < targetWidth) {

                w *= 2;
                if (w > targetWidth) {

                    w = targetWidth;

                }

            }

            if (higherQuality && h < targetHeight) {

                h *= 2;
                if (h > targetHeight) {

                    h = targetHeight;

                }

            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
            tmp = null;

        } while (w != targetWidth || h != targetHeight);

        return ret;

    }

}
