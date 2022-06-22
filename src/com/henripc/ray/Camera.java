package com.henripc.ray;

public class Camera {
    private final Point3 origin;
    private final Vector lowerLeftCorner;
    private final Vector horizontal;
    private final Vector vertical;
    private final Vector u;
    private final Vector v;
    private final Vector w;
    private final double lensRadius;

    public Camera(final Point3 lookFrom, final Point3 lookAt, final Vec3 vUp,
                  final double vFov, final double aspectRatio,      // vFov vertical field-of-view in degrees
                  final double aperture, final double focusDist) {
        final double theta = RtWeekend.degreesToRadians(vFov);
        final double h = Math.tan(theta / 2);
        final double viewportHeight = 2 * h;
        final double viewportWidth = aspectRatio * viewportHeight;
        
        this.w = Vector.unitVector(Vector.sumOfVectors(lookFrom, lookAt.scalarMultiplication(-1)));
        this.u = Vector.unitVector(Vector.cross(vUp, w));
        this.v = Vector.cross(w, u);

        this.origin = lookFrom;
        this.horizontal = u.scalarMultiplication(focusDist * viewportWidth);
        this.vertical = v.scalarMultiplication(focusDist * viewportHeight);
        this.lowerLeftCorner = Vector.sumOfVectors(this.origin,
                                                   this.horizontal.scalarMultiplication(-1.0 / 2),
                                                   this.vertical.scalarMultiplication(-1.0 / 2),
                                                   w.scalarMultiplication(-focusDist));

        this.lensRadius = aperture / 2;
    }

    public Ray getRay(final double s, final double t) {
        final Vector rd = Vector.randomInUnitDisk().scalarMultiplication(this.lensRadius);
        final Vector offset = Vector.sumOfVectors(u.scalarMultiplication(rd.x()), v.scalarMultiplication(rd.y()));
        
        final Vector point = Vector.sumOfVectors(this.origin, offset);
        return new Ray(point, Vector.sumOfVectors(this.lowerLeftCorner,
                                                  this.horizontal.scalarMultiplication(s),
                                                  this.vertical.scalarMultiplication(t),
                                                  this.origin.scalarMultiplication(-1),
                                                  offset.scalarMultiplication(-1)));
    }
}
