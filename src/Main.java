import com.henripc.ray.Color;

public class Main {
    public static void main(String[] args) throws Exception {
        // Image
        final int imageWidth = 256;
        final int imageHeight = 256;

        // Render
        System.out.println("P3\n" + imageWidth + " " + imageHeight + "\n255");

        for (int j = imageHeight - 1; j >= 0; j--) {
            System.err.println("\rScanlines remaining: " + j);
            for (int i = 0; i < imageWidth; i++) {
                final Color pixelColor = new Color((double) i / (imageWidth - 1), (double) j / (imageHeight - 1), 0.25);
                Color.writeColor(pixelColor);
            }
        }

        System.err.println("\nDone.");
    }
}
