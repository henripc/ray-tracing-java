public class Main {
    public static void main(String[] args) throws Exception {
        // Image
        final int imageWidth = 256;
        final int imageHeight = 256;

        // Render
        System.out.println("P3\n" + imageWidth + " " + imageHeight + "\n255\n");

        for (int j = imageHeight - 1; j >= 0; j--) {
            for (int i = 0; i < imageWidth; i++) {
                double r = (double) i / (imageWidth - 1);
                double g = (double) j / (imageHeight - 1);
                double b = 0.25;

                int ir = (int) (255.999 * r);
                int ig = (int) (255.999 * g);
                int ib = (int) (255.999 * b);

                System.out.println(ir + " " + ig + " " + ib + "\n");
            }
        }
    }
}
