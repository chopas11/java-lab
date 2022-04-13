public class Lab1 {
    public static void main(String... args) {

        Point3d coords1 = new Point3d(Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]));
        Point3d coords2 = new Point3d(Double.valueOf(args[3]), Double.valueOf(args[4]), Double.valueOf(args[5]));
        Point3d coords3 = new Point3d(Double.valueOf(args[6]), Double.valueOf(args[7]), Double.valueOf(args[8]));
        // System.out.println(coords3.getX());
        if (coords1.equals(coords2) || coords1.equals(coords3) ||
                coords2.equals(coords3)) {
            System.out.println("Matching coordinates");
        } else
            System.out.println(Point3d.computeArea(coords1, coords2, coords3));

    }

}
