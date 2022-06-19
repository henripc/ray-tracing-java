package com.henripc.ray;

import java.util.HashMap;
import java.util.Map;

public class HitRecord {
    // private Vector p;
    // private Vector normal;
    // private double t;
    // private boolean frontFace;

    public final Map<String, Object> fieldsMap = new HashMap<>();

    public HitRecord() {
        fieldsMap.put("p", null);
        fieldsMap.put("normal", null);
        fieldsMap.put("t", null);
        fieldsMap.put("frontFace", null);
    }

    public void setFaceNormal(final Ray r, final Vector outwardNormal) {
        this.fieldsMap.put("frontFace", Vector.dot(r.getDirection(), outwardNormal) < 0);
        final Vector normal = (boolean) this.fieldsMap.get("frontFace") ? outwardNormal : outwardNormal.scalarMultiplication(-1);
        this.fieldsMap.put("normal", normal);
    }
}
