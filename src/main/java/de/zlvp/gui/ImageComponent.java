package de.zlvp.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private Image image;

    public ImageComponent(Image image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        this.image = image.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
