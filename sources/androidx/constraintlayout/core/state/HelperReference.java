package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public class HelperReference extends ConstraintReference implements Facade {
    private HelperWidget mHelperWidget;
    protected ArrayList mReferences;
    protected final State mState;
    final State.Helper mType;

    public HelperReference(State state, State.Helper type) {
        super(state);
        this.mReferences = new ArrayList();
        this.mState = state;
        this.mType = type;
    }

    public State.Helper getType() {
        return this.mType;
    }

    public HelperReference add(Object... objects) {
        Collections.addAll(this.mReferences, objects);
        return this;
    }

    public void setHelperWidget(HelperWidget helperWidget) {
        this.mHelperWidget = helperWidget;
    }

    public HelperWidget getHelperWidget() {
        return this.mHelperWidget;
    }

    public ConstraintWidget getConstraintWidget() {
        return getHelperWidget();
    }

    public void apply() {
    }
}
