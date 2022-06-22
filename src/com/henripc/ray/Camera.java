package com.henripc.ray;

public class Camera {
    private final Point3 origin;
    private final Vector lowerLeftCorner;
    private final Vector horizontal;
    private final Vector vertical;

    public Camera(final Point3 lookFrom, final Point3 lookAt, final Vec3 vUp,
                  final double vFov, final double aspectRatio) {    // vFov vertical field-of-view in degrees
        final double theta = RtWeekend.degreesToRadians(vFov);
        final double h = Math.tan(theta / 2);
        final double viewportHeight = 2 * h;
        final double viewportWidth = aspectRatio * viewportHeight;
        
        final Vector w = Vector.unitVector(Vector.sumOfVectors(lookFrom, lookAt.scalarMultiplication(-1)));
        final Vector u = Vector.unitVector(Vector.cross(vUp, w));
        final Vector v = Vector.cross(w, u);

        this.origin = lookFrom;
        this.horizontal = u.scalarMultiplication(viewportWidth);
        this.vertical = v.scalarMultiplication(viewportHeight);
        this.lowerLeftCorner = Vector.sumOfVectors(this.origin,
                                                   this.horizontal.scalarMultiplication(-1.0 / 2),
                                                   this.vertical.scalarMultiplication(-1.0 / 2),
                                                   w.scalarMultiplication(-1));
    }

    public Ray getRay(final double s, final double t) {
        return new Ray(this.origin, Vector.sumOfVectors(this.lowerLeftCorner,
                                                        this.horizontal.scalarMultiplication(s),
                                                        this.vertical.scalarMultiplication(t),
                                                        this.origin.scalarMultiplication(-1)));
    }
}
