package com.henripc.ray;

public class Camera {
    private final Point3 origin;
    private final Vector lowerLeftCorner;
    private final Vector horizontal;
    private final Vector vertical;

    public Camera() {
        final double aspectRatio = 16 / 9.0;
        final double viewportHeight = 2;
        final double viewportWidth = aspectRatio * viewportHeight;
        final double focalLength = 1;

        this.origin = new Point3(0, 0, 0);
        this.horizontal = new Vec3(viewportWidth, 0, 0);
        this.vertical = new Vec3(0, viewportHeight, 0);
        this.lowerLeftCorner = Vector.sumOfVectors(this.origin,
                                                   this.horizontal.scalarMultiplication(-1.0 / 2),
                                                   this.vertical.scalarMultiplication(-1.0 / 2),
                                                   (new Vec3(0, 0, focalLength)).scalarMultiplication(-1));
    }

    public Ray getRay(final double u, final double v) {
        return new Ray(this.origin, (Vec3) Vector.sumOfVectors(this.lowerLeftCorner,
                                                               this.horizontal.scalarMultiplication(u),
                                                               this.vertical.scalarMultiplication(v),
                                                               this.origin.scalarMultiplication(-1)));
    }
}
