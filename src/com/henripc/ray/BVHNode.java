package com.henripc.ray;

import java.util.Comparator;
import java.util.List;

public class BVHNode implements Hittable {
    public Hittable left;
    public Hittable right;
    public AABB box;

    public BVHNode(final HittableList list, final double time0, final double time1) {
        this(list.objects, 0, list.objects.size(), time0, time1);
    }

    public BVHNode(final List<Hittable> srcObjects, final int start, final int end, final double time0, final double time1) {
        final List<Hittable> objects = srcObjects;  // Create a modifiable list of the source scene objects

        final int axis = RtWeekend.randomInt(0, 2);
        final BoxCompare comparator = axis == 0 ? new BoxCompare(0)
                                    : axis == 1 ? new BoxCompare(1)
                                                : new BoxCompare(2);
        
        final int objectSpan = end - start;

        if (objectSpan == 1) {
            this.left = this.right = objects.get(start);
        } else if (objectSpan == 2) {
            if (comparator.compare(objects.get(start), objects.get(start + 1)) <= 0) {
                this.left = objects.get(start);
                this.right = objects.get(start + 1);
            } else {
                this.left = objects.get(start + 1);
                this.right = objects.get(start);
            }
        } else {
            objects.subList(start, end).sort(comparator);
            final int mid = start + objectSpan / 2;
            this.left = new BVHNode(objects, start, mid, time0, time1);
            this.right = new BVHNode(objects, mid, end, time0, time1);
        }

        final AABB boxLeft = new AABB();
        final AABB boxRight = new AABB();

        if (!this.left.boundingBox(time0, time1, boxLeft) || ! this.right.boundingBox(time0, time1, boxRight)) {
            System.err.println("No bounding box in BVHNode constructor.");
        }

        this.box = AABB.surroundingBox(boxLeft, boxRight);
    }

    @Override
    public boolean hit(final Ray r, final double tMin, final double tMax, final HitRecord rec) {
        if (!this.box.hit(r, tMin, tMax)) return false;

        final boolean hitLeft = this.left.hit(r, tMin, tMax, rec);
        final boolean hitRight = this.right.hit(r, tMin, hitLeft ? rec.t : tMax, rec);

        return hitLeft || hitRight;
    }

    @Override
    public boolean boundingBox(final double time0, final double time1, final AABB outputBox) {
        outputBox.minimum = this.box.getMin();
        outputBox.maximum = this.box.getMax();

        return true;
    }

    public class BoxCompare implements Comparator<Hittable> {
        private final int axis;

        public BoxCompare(final int axis) {
            this.axis = axis;
        } 

        @Override
        public int compare(final Hittable o1, final Hittable o2) {
            final AABB boxA = new AABB();
            final AABB boxB = new AABB();

            if (!o1.boundingBox(0, 0, boxA) || !o2.boundingBox(0, 0, boxB)) {
                System.err.println("No bounding box in BVHNode constructor.");
            }

            return boxA.minimum.e[this.axis] < boxB.minimum.e[this.axis] ? -1 :
                   boxA.minimum.e[this.axis] > boxB.minimum.e[this.axis] ? 1 : 0;
        }
    }
}

