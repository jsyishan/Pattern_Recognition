/**
 * Created by eugen on 5/19/17.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {

    private LineSegment[] lines;
    private Point[] points;
    private int S;  //  number of lines

    public FastCollinearPoints (Point[] points) {
        //Corner cases
        if (points == null) {
            throw new NullPointerException();
        }

        Point[] ps = points.clone();
        if (ps[0] == null) {
            throw new NullPointerException();
        }

        for (int i = 1; i < ps.length; i++) {
            if (ps[i] == null) {
                throw new NullPointerException();
            }

            for (int j = i; j > 0 && ps[j].compareTo(ps[j - 1]) <= 0; j--) {
                if (ps[j].compareTo(ps[j - 1]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }

                Point tmp = ps[j - 1];
                ps[j - 1] = ps[j];
                ps[j] = tmp;
            }
        }

        this.points = new Point[ps.length];
        for (int i = 0; i < ps.length; i++) {
            this.points[i] = ps[i];
        }

        S = 0;
        search();

    }

    private void search () {

        Point[] ps = new Point[points.length];
        int n = points.length;
        for (int i = 0; i <n; i++) {
            ps[i] = points[i];
        }

        lines = new LineSegment[1];

        for (int i = 0; i < n; i++) {
            Arrays.sort(ps, points[i].slopeOrder());

            int j = 0;
            while (j != ps.length) {
                int head = j;
                int tail = head;

                int k = head + 1;
                for (; k < ps.length && points[i].slopeTo(ps[head]) == points[i].slopeTo(ps[k]); k++) {
                    tail = k;
                }
                j = tail + 1;

                if (tail - head > 1) {
                    int min = head;
                    int max = head;

                    for (k = head + 1; k <= tail; k++) {
                        if (ps[k].compareTo(ps[min]) < 0) {
                            min = k;
                        }
                        if (ps[k].compareTo(ps[max]) > 0) {
                            max = k;
                        }
                    }

                    if (points[i].compareTo(ps[min]) < 0) {
                        if (S == lines.length) {
                            resize(2 * S);
                        }

                        lines[S++] = new LineSegment(points[i], ps[max]);
                    }
                }
            }

        }

        resize(S);
    }

    private void resize (int size) {
        LineSegment[] tmp = new LineSegment[size];
        for (int i = 0; i < S; i++) {
            tmp[i] = lines[i];
        }
        lines = tmp;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
