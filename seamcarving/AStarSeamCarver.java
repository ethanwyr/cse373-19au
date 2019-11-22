package seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    public int[] findHorizontalSeam() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    public int[] findVerticalSeam() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }
}
