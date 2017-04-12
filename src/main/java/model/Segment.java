package model;

/**
 * Created by theo on 12/04/17.
 */
public class Segment {
    private Point ptStart, ptEnd;
    private int color;

    public Segment() {
        ptStart = new Point(0,0);
        ptEnd = new Point(0,0);
    }

    public Point getPtStart() {
        return ptStart;
    }

    public void setPtStart(Point ptStart) {
        this.ptStart = ptStart;
    }

    public Point getPtEnd() {
        return ptEnd;
    }

    public void setPtEnd(Point ptEnd) {
        this.ptEnd = ptEnd;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

