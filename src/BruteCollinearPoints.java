import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by eugen on 5/19/17.
 */
public class BruteCollinearPoints {


    private LineSegment[] lines;
    private int P;  //  number of points
    private int S;  // number of segments
    private Point[] points;

    public BruteCollinearPoints(Point[] points) {
        //Corner cases
        if (points == null) {
            throw new NullPointerException();
        }

        Point[] ps = points.clone();
        if (ps[0] == null) {
            throw new NullPointerException();
        }
        for(int i = 1; i < ps.length; i++) {
            if (ps[i] == null) {
                throw new NullPointerException();
            }
            for (int j = i; j > 0 && ps[j].compareTo(ps[j - 1]) <= 0; j--) {
                if (ps[j].compareTo(ps[j - 1]) == 0) {
                    throw new IllegalArgumentException("The argument contains repeated point.");
                }

                Point tmp = ps[j - 1];
                ps[j - 1] = ps[j];
                ps[j] = tmp;
            }
        }

        P = ps.length;
        S = 0;
        this.points = new Point[P];
        for (int i = 0; i < P; i++) {
            this.points[i] = ps[i];
        }
        search();
    }

    private void search() {

    }

    private void resize(int size) {
        Point[] tmp = new Point[size];
        for (int i = 0; i < P; i++) {
            tmp[i] = points[i];
        }
        points = tmp;
    }


    public int numberOfSegments() {
        return S;
    }

    public LineSegment[] segments() {
        return lines.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
