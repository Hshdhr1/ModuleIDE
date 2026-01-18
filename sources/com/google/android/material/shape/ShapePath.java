package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ShapePath {
    protected static final float ANGLE_LEFT = 180.0f;
    private static final float ANGLE_UP = 270.0f;
    private boolean containsIncompatibleShadowOp;

    @Deprecated
    public float currentShadowAngle;

    @Deprecated
    public float endShadowAngle;

    @Deprecated
    public float endX;

    @Deprecated
    public float endY;
    private final List operations = new ArrayList();
    private final List shadowCompatOperations = new ArrayList();

    @Deprecated
    public float startX;

    @Deprecated
    public float startY;

    public static abstract class PathOperation {
        protected final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    public ShapePath() {
        reset(0.0f, 0.0f);
    }

    public ShapePath(float startX, float startY) {
        reset(startX, startY);
    }

    public void reset(float startX, float startY) {
        reset(startX, startY, 270.0f, 0.0f);
    }

    public void reset(float startX, float startY, float shadowStartAngle, float shadowSweepAngle) {
        setStartX(startX);
        setStartY(startY);
        setEndX(startX);
        setEndY(startY);
        setCurrentShadowAngle(shadowStartAngle);
        setEndShadowAngle((shadowStartAngle + shadowSweepAngle) % 360.0f);
        this.operations.clear();
        this.shadowCompatOperations.clear();
        this.containsIncompatibleShadowOp = false;
    }

    public void lineTo(float x, float y) {
        PathLineOperation operation = new PathLineOperation();
        PathLineOperation.access$002(operation, x);
        PathLineOperation.access$102(operation, y);
        this.operations.add(operation);
        LineShadowOperation shadowOperation = new LineShadowOperation(operation, getEndX(), getEndY());
        addShadowCompatOperation(shadowOperation, shadowOperation.getAngle() + 270.0f, shadowOperation.getAngle() + 270.0f);
        setEndX(x);
        setEndY(y);
    }

    public void quadToPoint(float controlX, float controlY, float toX, float toY) {
        PathQuadOperation operation = new PathQuadOperation();
        PathQuadOperation.access$200(operation, controlX);
        PathQuadOperation.access$300(operation, controlY);
        PathQuadOperation.access$400(operation, toX);
        PathQuadOperation.access$500(operation, toY);
        this.operations.add(operation);
        this.containsIncompatibleShadowOp = true;
        setEndX(toX);
        setEndY(toY);
    }

    public void cubicToPoint(float controlX1, float controlY1, float controlX2, float controlY2, float toX, float toY) {
        PathCubicOperation operation = new PathCubicOperation(controlX1, controlY1, controlX2, controlY2, toX, toY);
        this.operations.add(operation);
        this.containsIncompatibleShadowOp = true;
        setEndX(toX);
        setEndY(toY);
    }

    public void addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        PathArcOperation operation = new PathArcOperation(left, top, right, bottom);
        PathArcOperation.access$600(operation, startAngle);
        PathArcOperation.access$700(operation, sweepAngle);
        this.operations.add(operation);
        ArcShadowOperation arcShadowOperation = new ArcShadowOperation(operation);
        float endAngle = startAngle + sweepAngle;
        boolean drawShadowInsideBounds = sweepAngle < 0.0f;
        addShadowCompatOperation(arcShadowOperation, drawShadowInsideBounds ? (startAngle + 180.0f) % 360.0f : startAngle, drawShadowInsideBounds ? (180.0f + endAngle) % 360.0f : endAngle);
        setEndX(((left + right) * 0.5f) + (((right - left) / 2.0f) * ((float) Math.cos(Math.toRadians(startAngle + sweepAngle)))));
        setEndY(((top + bottom) * 0.5f) + (((bottom - top) / 2.0f) * ((float) Math.sin(Math.toRadians(startAngle + sweepAngle)))));
    }

    public void applyToPath(Matrix transform, Path path) {
        int size = this.operations.size();
        for (int i = 0; i < size; i++) {
            PathOperation operation = (PathOperation) this.operations.get(i);
            operation.applyToPath(transform, path);
        }
    }

    ShadowCompatOperation createShadowCompatOperation(Matrix transform) {
        addConnectingShadowIfNecessary(getEndShadowAngle());
        Matrix transformCopy = new Matrix(transform);
        return new 1(new ArrayList(this.shadowCompatOperations), transformCopy);
    }

    class 1 extends ShadowCompatOperation {
        final /* synthetic */ List val$operations;
        final /* synthetic */ Matrix val$transformCopy;

        1(List list, Matrix matrix) {
            this.val$operations = list;
            this.val$transformCopy = matrix;
        }

        public void draw(Matrix matrix, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            for (ShadowCompatOperation op : this.val$operations) {
                op.draw(this.val$transformCopy, shadowRenderer, shadowElevation, canvas);
            }
        }
    }

    private void addShadowCompatOperation(ShadowCompatOperation shadowOperation, float startShadowAngle, float endShadowAngle) {
        addConnectingShadowIfNecessary(startShadowAngle);
        this.shadowCompatOperations.add(shadowOperation);
        setCurrentShadowAngle(endShadowAngle);
    }

    boolean containsIncompatibleShadowOp() {
        return this.containsIncompatibleShadowOp;
    }

    private void addConnectingShadowIfNecessary(float nextShadowAngle) {
        if (getCurrentShadowAngle() == nextShadowAngle) {
            return;
        }
        float shadowSweep = ((nextShadowAngle - getCurrentShadowAngle()) + 360.0f) % 360.0f;
        if (shadowSweep > 180.0f) {
            return;
        }
        PathArcOperation pathArcOperation = new PathArcOperation(getEndX(), getEndY(), getEndX(), getEndY());
        PathArcOperation.access$600(pathArcOperation, getCurrentShadowAngle());
        PathArcOperation.access$700(pathArcOperation, shadowSweep);
        this.shadowCompatOperations.add(new ArcShadowOperation(pathArcOperation));
        setCurrentShadowAngle(nextShadowAngle);
    }

    float getStartX() {
        return this.startX;
    }

    float getStartY() {
        return this.startY;
    }

    float getEndX() {
        return this.endX;
    }

    float getEndY() {
        return this.endY;
    }

    private float getCurrentShadowAngle() {
        return this.currentShadowAngle;
    }

    private float getEndShadowAngle() {
        return this.endShadowAngle;
    }

    private void setStartX(float startX) {
        this.startX = startX;
    }

    private void setStartY(float startY) {
        this.startY = startY;
    }

    private void setEndX(float endX) {
        this.endX = endX;
    }

    private void setEndY(float endY) {
        this.endY = endY;
    }

    private void setCurrentShadowAngle(float currentShadowAngle) {
        this.currentShadowAngle = currentShadowAngle;
    }

    private void setEndShadowAngle(float endShadowAngle) {
        this.endShadowAngle = endShadowAngle;
    }

    static abstract class ShadowCompatOperation {
        static final Matrix IDENTITY_MATRIX = new Matrix();

        public abstract void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas);

        ShadowCompatOperation() {
        }

        public final void draw(ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            draw(IDENTITY_MATRIX, shadowRenderer, shadowElevation, canvas);
        }
    }

    static class LineShadowOperation extends ShadowCompatOperation {
        private final PathLineOperation operation;
        private final float startX;
        private final float startY;

        public LineShadowOperation(PathLineOperation operation, float startX, float startY) {
            this.operation = operation;
            this.startX = startX;
            this.startY = startY;
        }

        public void draw(Matrix transform, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            float height = PathLineOperation.access$100(this.operation) - this.startY;
            float width = PathLineOperation.access$000(this.operation) - this.startX;
            RectF rect = new RectF(0.0f, 0.0f, (float) Math.hypot(height, width), 0.0f);
            Matrix edgeTransform = new Matrix(transform);
            edgeTransform.preTranslate(this.startX, this.startY);
            edgeTransform.preRotate(getAngle());
            shadowRenderer.drawEdgeShadow(canvas, edgeTransform, rect, shadowElevation);
        }

        float getAngle() {
            return (float) Math.toDegrees(Math.atan((PathLineOperation.access$100(this.operation) - this.startY) / (PathLineOperation.access$000(this.operation) - this.startX)));
        }
    }

    static class ArcShadowOperation extends ShadowCompatOperation {
        private final PathArcOperation operation;

        public ArcShadowOperation(PathArcOperation operation) {
            this.operation = operation;
        }

        public void draw(Matrix transform, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            float startAngle = PathArcOperation.access$800(this.operation);
            float sweepAngle = PathArcOperation.access$900(this.operation);
            RectF rect = new RectF(PathArcOperation.access$1000(this.operation), PathArcOperation.access$1100(this.operation), PathArcOperation.access$1200(this.operation), PathArcOperation.access$1300(this.operation));
            shadowRenderer.drawCornerShadow(canvas, transform, rect, shadowElevation, startAngle, sweepAngle);
        }
    }

    public static class PathLineOperation extends PathOperation {
        private float x;
        private float y;

        static /* synthetic */ float access$000(PathLineOperation x0) {
            return x0.x;
        }

        static /* synthetic */ float access$002(PathLineOperation x0, float x1) {
            x0.x = x1;
            return x1;
        }

        static /* synthetic */ float access$100(PathLineOperation x0) {
            return x0.y;
        }

        static /* synthetic */ float access$102(PathLineOperation x0, float x1) {
            x0.y = x1;
            return x1;
        }

        public void applyToPath(Matrix transform, Path path) {
            Matrix inverse = this.matrix;
            transform.invert(inverse);
            path.transform(inverse);
            path.lineTo(this.x, this.y);
            path.transform(transform);
        }
    }

    public static class PathQuadOperation extends PathOperation {

        @Deprecated
        public float controlX;

        @Deprecated
        public float controlY;

        @Deprecated
        public float endX;

        @Deprecated
        public float endY;

        static /* synthetic */ void access$200(PathQuadOperation x0, float x1) {
            x0.setControlX(x1);
        }

        static /* synthetic */ void access$300(PathQuadOperation x0, float x1) {
            x0.setControlY(x1);
        }

        static /* synthetic */ void access$400(PathQuadOperation x0, float x1) {
            x0.setEndX(x1);
        }

        static /* synthetic */ void access$500(PathQuadOperation x0, float x1) {
            x0.setEndY(x1);
        }

        public void applyToPath(Matrix transform, Path path) {
            Matrix inverse = this.matrix;
            transform.invert(inverse);
            path.transform(inverse);
            path.quadTo(getControlX(), getControlY(), getEndX(), getEndY());
            path.transform(transform);
        }

        private float getEndX() {
            return this.endX;
        }

        private void setEndX(float endX) {
            this.endX = endX;
        }

        private float getControlY() {
            return this.controlY;
        }

        private void setControlY(float controlY) {
            this.controlY = controlY;
        }

        private float getEndY() {
            return this.endY;
        }

        private void setEndY(float endY) {
            this.endY = endY;
        }

        private float getControlX() {
            return this.controlX;
        }

        private void setControlX(float controlX) {
            this.controlX = controlX;
        }
    }

    public static class PathArcOperation extends PathOperation {
        private static final RectF rectF = new RectF();

        @Deprecated
        public float bottom;

        @Deprecated
        public float left;

        @Deprecated
        public float right;

        @Deprecated
        public float startAngle;

        @Deprecated
        public float sweepAngle;

        @Deprecated
        public float top;

        static /* synthetic */ float access$1000(PathArcOperation x0) {
            return x0.getLeft();
        }

        static /* synthetic */ float access$1100(PathArcOperation x0) {
            return x0.getTop();
        }

        static /* synthetic */ float access$1200(PathArcOperation x0) {
            return x0.getRight();
        }

        static /* synthetic */ float access$1300(PathArcOperation x0) {
            return x0.getBottom();
        }

        static /* synthetic */ void access$600(PathArcOperation x0, float x1) {
            x0.setStartAngle(x1);
        }

        static /* synthetic */ void access$700(PathArcOperation x0, float x1) {
            x0.setSweepAngle(x1);
        }

        static /* synthetic */ float access$800(PathArcOperation x0) {
            return x0.getStartAngle();
        }

        static /* synthetic */ float access$900(PathArcOperation x0) {
            return x0.getSweepAngle();
        }

        public PathArcOperation(float left, float top, float right, float bottom) {
            setLeft(left);
            setTop(top);
            setRight(right);
            setBottom(bottom);
        }

        public void applyToPath(Matrix transform, Path path) {
            Matrix inverse = this.matrix;
            transform.invert(inverse);
            path.transform(inverse);
            RectF rectF2 = rectF;
            rectF2.set(getLeft(), getTop(), getRight(), getBottom());
            path.arcTo(rectF2, getStartAngle(), getSweepAngle(), false);
            path.transform(transform);
        }

        private float getLeft() {
            return this.left;
        }

        private float getTop() {
            return this.top;
        }

        private float getRight() {
            return this.right;
        }

        private float getBottom() {
            return this.bottom;
        }

        private void setLeft(float left) {
            this.left = left;
        }

        private void setTop(float top) {
            this.top = top;
        }

        private void setRight(float right) {
            this.right = right;
        }

        private void setBottom(float bottom) {
            this.bottom = bottom;
        }

        private float getStartAngle() {
            return this.startAngle;
        }

        private float getSweepAngle() {
            return this.sweepAngle;
        }

        private void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        private void setSweepAngle(float sweepAngle) {
            this.sweepAngle = sweepAngle;
        }
    }

    public static class PathCubicOperation extends PathOperation {
        private float controlX1;
        private float controlX2;
        private float controlY1;
        private float controlY2;
        private float endX;
        private float endY;

        public PathCubicOperation(float controlX1, float controlY1, float controlX2, float controlY2, float endX, float endY) {
            setControlX1(controlX1);
            setControlY1(controlY1);
            setControlX2(controlX2);
            setControlY2(controlY2);
            setEndX(endX);
            setEndY(endY);
        }

        public void applyToPath(Matrix transform, Path path) {
            Matrix inverse = this.matrix;
            transform.invert(inverse);
            path.transform(inverse);
            path.cubicTo(this.controlX1, this.controlY1, this.controlX2, this.controlY2, this.endX, this.endY);
            path.transform(transform);
        }

        private float getControlX1() {
            return this.controlX1;
        }

        private void setControlX1(float controlX1) {
            this.controlX1 = controlX1;
        }

        private float getControlY1() {
            return this.controlY1;
        }

        private void setControlY1(float controlY1) {
            this.controlY1 = controlY1;
        }

        private float getControlX2() {
            return this.controlX2;
        }

        private void setControlX2(float controlX2) {
            this.controlX2 = controlX2;
        }

        private float getControlY2() {
            return this.controlY1;
        }

        private void setControlY2(float controlY2) {
            this.controlY2 = controlY2;
        }

        private float getEndX() {
            return this.endX;
        }

        private void setEndX(float endX) {
            this.endX = endX;
        }

        private float getEndY() {
            return this.endY;
        }

        private void setEndY(float endY) {
            this.endY = endY;
        }
    }
}
