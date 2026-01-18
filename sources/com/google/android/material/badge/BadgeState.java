package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class BadgeState {
    private static final String BADGE_RESOURCE_TAG = "badge";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    final float badgeRadius;
    final float badgeWidePadding;
    final float badgeWithTextRadius;
    private final State currentState;
    private final State overridingState;

    BadgeState(Context context, int badgeResId, int defStyleAttr, int defStyleRes, State storedState) {
        String access$200;
        int access$300;
        int access$400;
        int access$600;
        int intValue;
        int intValue2;
        int intValue3;
        int intValue4;
        int intValue5;
        int intValue6;
        Locale locale;
        State state = new State();
        this.currentState = state;
        storedState = storedState == null ? new State() : storedState;
        if (badgeResId != 0) {
            State.access$002(storedState, badgeResId);
        }
        TypedArray a = generateTypedArray(context, State.access$000(storedState), defStyleAttr, defStyleRes);
        Resources res = context.getResources();
        this.badgeRadius = a.getDimensionPixelSize(R.styleable.Badge_badgeRadius, res.getDimensionPixelSize(R.dimen.mtrl_badge_radius));
        this.badgeWidePadding = a.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, res.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
        this.badgeWithTextRadius = a.getDimensionPixelSize(R.styleable.Badge_badgeWithTextRadius, res.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius));
        State.access$102(state, State.access$100(storedState) == -2 ? 255 : State.access$100(storedState));
        if (State.access$200(storedState) == null) {
            access$200 = context.getString(R.string.mtrl_badge_numberless_content_description);
        } else {
            access$200 = State.access$200(storedState);
        }
        State.access$202(state, access$200);
        if (State.access$300(storedState) == 0) {
            access$300 = R.plurals.mtrl_badge_content_description;
        } else {
            access$300 = State.access$300(storedState);
        }
        State.access$302(state, access$300);
        if (State.access$400(storedState) == 0) {
            access$400 = R.string.mtrl_exceed_max_badge_number_content_description;
        } else {
            access$400 = State.access$400(storedState);
        }
        State.access$402(state, access$400);
        State.access$502(state, Boolean.valueOf(State.access$500(storedState) == null || State.access$500(storedState).booleanValue()));
        if (State.access$600(storedState) == -2) {
            access$600 = a.getInt(R.styleable.Badge_maxCharacterCount, 4);
        } else {
            access$600 = State.access$600(storedState);
        }
        State.access$602(state, access$600);
        if (State.access$700(storedState) != -2) {
            State.access$702(state, State.access$700(storedState));
        } else if (a.hasValue(R.styleable.Badge_number)) {
            State.access$702(state, a.getInt(R.styleable.Badge_number, 0));
        } else {
            State.access$702(state, -1);
        }
        if (State.access$800(storedState) == null) {
            intValue = readColorFromAttributes(context, a, R.styleable.Badge_backgroundColor);
        } else {
            intValue = State.access$800(storedState).intValue();
        }
        State.access$802(state, Integer.valueOf(intValue));
        if (State.access$900(storedState) != null) {
            State.access$902(state, State.access$900(storedState));
        } else if (a.hasValue(R.styleable.Badge_badgeTextColor)) {
            State.access$902(state, Integer.valueOf(readColorFromAttributes(context, a, R.styleable.Badge_badgeTextColor)));
        } else {
            TextAppearance textAppearance = new TextAppearance(context, R.style.TextAppearance_MaterialComponents_Badge);
            State.access$902(state, Integer.valueOf(textAppearance.getTextColor().getDefaultColor()));
        }
        if (State.access$1000(storedState) == null) {
            intValue2 = a.getInt(R.styleable.Badge_badgeGravity, 8388661);
        } else {
            intValue2 = State.access$1000(storedState).intValue();
        }
        State.access$1002(state, Integer.valueOf(intValue2));
        if (State.access$1100(storedState) == null) {
            intValue3 = a.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
        } else {
            intValue3 = State.access$1100(storedState).intValue();
        }
        State.access$1102(state, Integer.valueOf(intValue3));
        if (State.access$1100(storedState) == null) {
            intValue4 = a.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
        } else {
            intValue4 = State.access$1200(storedState).intValue();
        }
        State.access$1202(state, Integer.valueOf(intValue4));
        if (State.access$1300(storedState) == null) {
            intValue5 = a.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, State.access$1100(state).intValue());
        } else {
            intValue5 = State.access$1300(storedState).intValue();
        }
        State.access$1302(state, Integer.valueOf(intValue5));
        if (State.access$1400(storedState) == null) {
            intValue6 = a.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, State.access$1200(state).intValue());
        } else {
            intValue6 = State.access$1400(storedState).intValue();
        }
        State.access$1402(state, Integer.valueOf(intValue6));
        State.access$1502(state, Integer.valueOf(State.access$1500(storedState) == null ? 0 : State.access$1500(storedState).intValue()));
        State.access$1602(state, Integer.valueOf(State.access$1600(storedState) != null ? State.access$1600(storedState).intValue() : 0));
        a.recycle();
        if (State.access$1700(storedState) == null) {
            if (Build.VERSION.SDK_INT >= 24) {
                locale = Locale.getDefault(Locale.Category.FORMAT);
            } else {
                locale = Locale.getDefault();
            }
            State.access$1702(state, locale);
        } else {
            State.access$1702(state, State.access$1700(storedState));
        }
        this.overridingState = storedState;
    }

    private TypedArray generateTypedArray(Context context, int badgeResId, int defStyleAttr, int defStyleRes) {
        AttributeSet attrs = null;
        int style = 0;
        if (badgeResId != 0) {
            attrs = DrawableUtils.parseDrawableXml(context, badgeResId, "badge");
            style = attrs.getStyleAttribute();
        }
        if (style == 0) {
            style = defStyleRes;
        }
        return ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Badge, defStyleAttr, style, new int[0]);
    }

    State getOverridingState() {
        return this.overridingState;
    }

    boolean isVisible() {
        return State.access$500(this.currentState).booleanValue();
    }

    void setVisible(boolean visible) {
        State.access$502(this.overridingState, Boolean.valueOf(visible));
        State.access$502(this.currentState, Boolean.valueOf(visible));
    }

    boolean hasNumber() {
        return State.access$700(this.currentState) != -1;
    }

    int getNumber() {
        return State.access$700(this.currentState);
    }

    void setNumber(int number) {
        State.access$702(this.overridingState, number);
        State.access$702(this.currentState, number);
    }

    void clearNumber() {
        setNumber(-1);
    }

    int getAlpha() {
        return State.access$100(this.currentState);
    }

    void setAlpha(int alpha) {
        State.access$102(this.overridingState, alpha);
        State.access$102(this.currentState, alpha);
    }

    int getMaxCharacterCount() {
        return State.access$600(this.currentState);
    }

    void setMaxCharacterCount(int maxCharacterCount) {
        State.access$602(this.overridingState, maxCharacterCount);
        State.access$602(this.currentState, maxCharacterCount);
    }

    int getBackgroundColor() {
        return State.access$800(this.currentState).intValue();
    }

    void setBackgroundColor(int backgroundColor) {
        State.access$802(this.overridingState, Integer.valueOf(backgroundColor));
        State.access$802(this.currentState, Integer.valueOf(backgroundColor));
    }

    int getBadgeTextColor() {
        return State.access$900(this.currentState).intValue();
    }

    void setBadgeTextColor(int badgeTextColor) {
        State.access$902(this.overridingState, Integer.valueOf(badgeTextColor));
        State.access$902(this.currentState, Integer.valueOf(badgeTextColor));
    }

    int getBadgeGravity() {
        return State.access$1000(this.currentState).intValue();
    }

    void setBadgeGravity(int badgeGravity) {
        State.access$1002(this.overridingState, Integer.valueOf(badgeGravity));
        State.access$1002(this.currentState, Integer.valueOf(badgeGravity));
    }

    int getHorizontalOffsetWithoutText() {
        return State.access$1100(this.currentState).intValue();
    }

    void setHorizontalOffsetWithoutText(int offset) {
        State.access$1102(this.overridingState, Integer.valueOf(offset));
        State.access$1102(this.currentState, Integer.valueOf(offset));
    }

    int getVerticalOffsetWithoutText() {
        return State.access$1200(this.currentState).intValue();
    }

    void setVerticalOffsetWithoutText(int offset) {
        State.access$1202(this.overridingState, Integer.valueOf(offset));
        State.access$1202(this.currentState, Integer.valueOf(offset));
    }

    int getHorizontalOffsetWithText() {
        return State.access$1300(this.currentState).intValue();
    }

    void setHorizontalOffsetWithText(int offset) {
        State.access$1302(this.overridingState, Integer.valueOf(offset));
        State.access$1302(this.currentState, Integer.valueOf(offset));
    }

    int getVerticalOffsetWithText() {
        return State.access$1400(this.currentState).intValue();
    }

    void setVerticalOffsetWithText(int offset) {
        State.access$1402(this.overridingState, Integer.valueOf(offset));
        State.access$1402(this.currentState, Integer.valueOf(offset));
    }

    int getAdditionalHorizontalOffset() {
        return State.access$1500(this.currentState).intValue();
    }

    void setAdditionalHorizontalOffset(int offset) {
        State.access$1502(this.overridingState, Integer.valueOf(offset));
        State.access$1502(this.currentState, Integer.valueOf(offset));
    }

    int getAdditionalVerticalOffset() {
        return State.access$1600(this.currentState).intValue();
    }

    void setAdditionalVerticalOffset(int offset) {
        State.access$1602(this.overridingState, Integer.valueOf(offset));
        State.access$1602(this.currentState, Integer.valueOf(offset));
    }

    CharSequence getContentDescriptionNumberless() {
        return State.access$200(this.currentState);
    }

    void setContentDescriptionNumberless(CharSequence contentDescriptionNumberless) {
        State.access$202(this.overridingState, contentDescriptionNumberless);
        State.access$202(this.currentState, contentDescriptionNumberless);
    }

    int getContentDescriptionQuantityStrings() {
        return State.access$300(this.currentState);
    }

    void setContentDescriptionQuantityStringsResource(int stringsResource) {
        State.access$302(this.overridingState, stringsResource);
        State.access$302(this.currentState, stringsResource);
    }

    int getContentDescriptionExceedsMaxBadgeNumberStringResource() {
        return State.access$400(this.currentState);
    }

    void setContentDescriptionExceedsMaxBadgeNumberStringResource(int stringsResource) {
        State.access$402(this.overridingState, stringsResource);
        State.access$402(this.currentState, stringsResource);
    }

    Locale getNumberLocale() {
        return State.access$1700(this.currentState);
    }

    void setNumberLocale(Locale locale) {
        State.access$1702(this.overridingState, locale);
        State.access$1702(this.currentState, locale);
    }

    private static int readColorFromAttributes(Context context, TypedArray a, int index) {
        return MaterialResources.getColorStateList(context, a, index).getDefaultColor();
    }

    public static final class State implements Parcelable {
        private static final int BADGE_NUMBER_NONE = -1;
        public static final Parcelable.Creator CREATOR = new 1();
        private static final int NOT_SET = -2;
        private Integer additionalHorizontalOffset;
        private Integer additionalVerticalOffset;
        private int alpha;
        private Integer backgroundColor;
        private Integer badgeGravity;
        private int badgeResId;
        private Integer badgeTextColor;
        private int contentDescriptionExceedsMaxBadgeNumberRes;
        private CharSequence contentDescriptionNumberless;
        private int contentDescriptionQuantityStrings;
        private Integer horizontalOffsetWithText;
        private Integer horizontalOffsetWithoutText;
        private Boolean isVisible;
        private int maxCharacterCount;
        private int number;
        private Locale numberLocale;
        private Integer verticalOffsetWithText;
        private Integer verticalOffsetWithoutText;

        static /* synthetic */ int access$000(State x0) {
            return x0.badgeResId;
        }

        static /* synthetic */ int access$002(State x0, int x1) {
            x0.badgeResId = x1;
            return x1;
        }

        static /* synthetic */ int access$100(State x0) {
            return x0.alpha;
        }

        static /* synthetic */ Integer access$1000(State x0) {
            return x0.badgeGravity;
        }

        static /* synthetic */ Integer access$1002(State x0, Integer x1) {
            x0.badgeGravity = x1;
            return x1;
        }

        static /* synthetic */ int access$102(State x0, int x1) {
            x0.alpha = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1100(State x0) {
            return x0.horizontalOffsetWithoutText;
        }

        static /* synthetic */ Integer access$1102(State x0, Integer x1) {
            x0.horizontalOffsetWithoutText = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1200(State x0) {
            return x0.verticalOffsetWithoutText;
        }

        static /* synthetic */ Integer access$1202(State x0, Integer x1) {
            x0.verticalOffsetWithoutText = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1300(State x0) {
            return x0.horizontalOffsetWithText;
        }

        static /* synthetic */ Integer access$1302(State x0, Integer x1) {
            x0.horizontalOffsetWithText = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1400(State x0) {
            return x0.verticalOffsetWithText;
        }

        static /* synthetic */ Integer access$1402(State x0, Integer x1) {
            x0.verticalOffsetWithText = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1500(State x0) {
            return x0.additionalHorizontalOffset;
        }

        static /* synthetic */ Integer access$1502(State x0, Integer x1) {
            x0.additionalHorizontalOffset = x1;
            return x1;
        }

        static /* synthetic */ Integer access$1600(State x0) {
            return x0.additionalVerticalOffset;
        }

        static /* synthetic */ Integer access$1602(State x0, Integer x1) {
            x0.additionalVerticalOffset = x1;
            return x1;
        }

        static /* synthetic */ Locale access$1700(State x0) {
            return x0.numberLocale;
        }

        static /* synthetic */ Locale access$1702(State x0, Locale x1) {
            x0.numberLocale = x1;
            return x1;
        }

        static /* synthetic */ CharSequence access$200(State x0) {
            return x0.contentDescriptionNumberless;
        }

        static /* synthetic */ CharSequence access$202(State x0, CharSequence x1) {
            x0.contentDescriptionNumberless = x1;
            return x1;
        }

        static /* synthetic */ int access$300(State x0) {
            return x0.contentDescriptionQuantityStrings;
        }

        static /* synthetic */ int access$302(State x0, int x1) {
            x0.contentDescriptionQuantityStrings = x1;
            return x1;
        }

        static /* synthetic */ int access$400(State x0) {
            return x0.contentDescriptionExceedsMaxBadgeNumberRes;
        }

        static /* synthetic */ int access$402(State x0, int x1) {
            x0.contentDescriptionExceedsMaxBadgeNumberRes = x1;
            return x1;
        }

        static /* synthetic */ Boolean access$500(State x0) {
            return x0.isVisible;
        }

        static /* synthetic */ Boolean access$502(State x0, Boolean x1) {
            x0.isVisible = x1;
            return x1;
        }

        static /* synthetic */ int access$600(State x0) {
            return x0.maxCharacterCount;
        }

        static /* synthetic */ int access$602(State x0, int x1) {
            x0.maxCharacterCount = x1;
            return x1;
        }

        static /* synthetic */ int access$700(State x0) {
            return x0.number;
        }

        static /* synthetic */ int access$702(State x0, int x1) {
            x0.number = x1;
            return x1;
        }

        static /* synthetic */ Integer access$800(State x0) {
            return x0.backgroundColor;
        }

        static /* synthetic */ Integer access$802(State x0, Integer x1) {
            x0.backgroundColor = x1;
            return x1;
        }

        static /* synthetic */ Integer access$900(State x0) {
            return x0.badgeTextColor;
        }

        static /* synthetic */ Integer access$902(State x0, Integer x1) {
            x0.badgeTextColor = x1;
            return x1;
        }

        public State() {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = true;
        }

        State(Parcel in) {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = true;
            this.badgeResId = in.readInt();
            this.backgroundColor = in.readSerializable();
            this.badgeTextColor = in.readSerializable();
            this.alpha = in.readInt();
            this.number = in.readInt();
            this.maxCharacterCount = in.readInt();
            this.contentDescriptionNumberless = in.readString();
            this.contentDescriptionQuantityStrings = in.readInt();
            this.badgeGravity = in.readSerializable();
            this.horizontalOffsetWithoutText = in.readSerializable();
            this.verticalOffsetWithoutText = in.readSerializable();
            this.horizontalOffsetWithText = in.readSerializable();
            this.verticalOffsetWithText = in.readSerializable();
            this.additionalHorizontalOffset = in.readSerializable();
            this.additionalVerticalOffset = in.readSerializable();
            this.isVisible = in.readSerializable();
            this.numberLocale = in.readSerializable();
        }

        class 1 implements Parcelable.Creator {
            1() {
            }

            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            public State[] newArray(int size) {
                return new State[size];
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.badgeResId);
            dest.writeSerializable(this.backgroundColor);
            dest.writeSerializable(this.badgeTextColor);
            dest.writeInt(this.alpha);
            dest.writeInt(this.number);
            dest.writeInt(this.maxCharacterCount);
            CharSequence charSequence = this.contentDescriptionNumberless;
            dest.writeString(charSequence == null ? null : charSequence.toString());
            dest.writeInt(this.contentDescriptionQuantityStrings);
            dest.writeSerializable(this.badgeGravity);
            dest.writeSerializable(this.horizontalOffsetWithoutText);
            dest.writeSerializable(this.verticalOffsetWithoutText);
            dest.writeSerializable(this.horizontalOffsetWithText);
            dest.writeSerializable(this.verticalOffsetWithText);
            dest.writeSerializable(this.additionalHorizontalOffset);
            dest.writeSerializable(this.additionalVerticalOffset);
            dest.writeSerializable(this.isVisible);
            dest.writeSerializable(this.numberLocale);
        }
    }
}
