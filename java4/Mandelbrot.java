import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATIONS = 2000;

    // метод позволяет генератору фракталов определить наиболее «интересную» область
    // комплексной плоскости для конкретного фрактала.
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    // Метод реализует итеративную функцию для фрактала Мандельброта.
    public int numIterations(double x, double y) {

        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;

        while (iteration < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4) {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }

        if (iteration == MAX_ITERATIONS) {
            return -1;
        }

        return iteration;
    }

}