package com.henripc.ray;

import java.util.ArrayList;
import java.util.List;

public class HittableList implements Hittable {
    public final List<Hittable> objects = new ArrayList<>();

    public HittableList() {}
    public HittableList(final Hittable object) {
        add(object);
    }

    public void clear() {
        this.objects.clear();
    }

    public void add(final Hittable object) {
        this.objects.add(object);
    }

    @Override
    public boolean hit(final Ray r, final double tMin, final double tMax, HitRecord rec) {
        final HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        double closestSoFar = tMax;

        for (final Hittable object : this.objects) {
            if (object.hit(r, tMin, closestSoFar, tempRec)) {
                hitAnything = true;
                closestSoFar = tempRec.t;

                rec.p = tempRec.p;
                rec.normal = tempRec.normal;
                rec.t = tempRec.t;
                rec.frontFace = tempRec.frontFace;
                rec.mat = tempRec.mat;
            }
        }

        return hitAnything;
    }

    @Override
    public boolean boundingBox(final double time0, final double time1, final AABB outputBox) {
        if (this.objects.isEmpty()) return false;

        final AABB tempBox = new AABB();
        boolean firstBox = true;

        for (Hittable object : this.objects) {
            if (!object.boundingBox(time0, time1, tempBox)) return false;

            if (firstBox) {
                outputBox.minimum = tempBox.getMin();
                outputBox.maximum = tempBox.getMax();
            } else {
                final AABB surroundingBox = AABB.surroundingBox(outputBox, tempBox);
                outputBox.minimum = surroundingBox.getMin();
                outputBox.maximum = surroundingBox.getMax();
            }

            firstBox = false;
        }

        return true;
    }    
}
