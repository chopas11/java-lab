
public class Point3d extends Point2d {

    private double zCoord;

    public Point3d(double x, double y, double z) {
        super(x, y);
        zCoord = z;
    }

    public Point3d() {

        this(0, 0, 0);
    }

    public double getZ() {
        return this.zCoord;
    }

    public void setZ(double val) {
        this.zCoord = val;
    }

    public boolean equals(Point3d coords) {

        if (super.getX() == coords.getX() && super.getY() == coords.getY() && this.getZ() == coords.getZ())
            return true;
        else
            return false;
    }

    public double distanceTo(Point3d coords) {
        return Math.sqrt(
                Math.pow(Math.abs(super.getX() - coords.getX()), 2)
                        + Math.pow(Math.abs(super.getY() - coords.getY()), 2)
                        + Math.pow(Math.abs(this.getZ() - coords.getZ()), 2));
    }

    public static double computeArea(Point3d point1, Point3d point2, Point3d point3) {

        double a = point1.distanceTo(point2);
        double b = point1.distanceTo(point3);
        double c = point2.distanceTo(point3);
        double p = (a + b + c) / 2;

        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
