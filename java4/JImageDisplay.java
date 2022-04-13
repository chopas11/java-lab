import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

// Свой компонент для отображениея графики
class JImageDisplay extends JComponent {

    // Поле для объекта изображения
    private BufferedImage displayImage;

    public JImageDisplay(int width, int height) {
        // Объект с новым изображением
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Размеры изображения
        Dimension imageDimension = new Dimension(width, height);
        // Включить компонент в пользовательский интерфейс
        super.setPreferredSize(imageDimension);

    }

    @Override
    public void paintComponent(Graphics g) {
        // Метод суперкласса
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);
    }

    public void clearImage() {
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        displayImage.setRGB(x, y, rgbColor);
    }
}