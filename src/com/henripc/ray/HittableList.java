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
            }
        }

        return hitAnything;
    }    
}
