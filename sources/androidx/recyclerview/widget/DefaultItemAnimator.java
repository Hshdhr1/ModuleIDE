package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    private ArrayList mPendingRemovals = new ArrayList();
    private ArrayList mPendingAdditions = new ArrayList();
    private ArrayList mPendingMoves = new ArrayList();
    private ArrayList mPendingChanges = new ArrayList();
    ArrayList mAdditionsList = new ArrayList();
    ArrayList mMovesList = new ArrayList();
    ArrayList mChangesList = new ArrayList();
    ArrayList mAddAnimations = new ArrayList();
    ArrayList mMoveAnimations = new ArrayList();
    ArrayList mRemoveAnimations = new ArrayList();
    ArrayList mChangeAnimations = new ArrayList();

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Iterator it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) it.next();
                animateRemoveImpl(holder);
            }
            this.mPendingRemovals.clear();
            if (movesPending) {
                ArrayList<MoveInfo> moves = new ArrayList<>();
                moves.addAll(this.mPendingMoves);
                this.mMovesList.add(moves);
                this.mPendingMoves.clear();
                Runnable mover = new 1(moves);
                if (removalsPending) {
                    View view = ((MoveInfo) moves.get(0)).holder.itemView;
                    ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
                } else {
                    mover.run();
                }
            }
            if (changesPending) {
                ArrayList<ChangeInfo> changes = new ArrayList<>();
                changes.addAll(this.mPendingChanges);
                this.mChangesList.add(changes);
                this.mPendingChanges.clear();
                Runnable changer = new 2(changes);
                if (removalsPending) {
                    RecyclerView.ViewHolder holder2 = ((ChangeInfo) changes.get(0)).oldHolder;
                    ViewCompat.postOnAnimationDelayed(holder2.itemView, changer, getRemoveDuration());
                } else {
                    changer.run();
                }
            }
            if (additionsPending) {
                ArrayList<RecyclerView.ViewHolder> additions = new ArrayList<>();
                additions.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(additions);
                this.mPendingAdditions.clear();
                Runnable adder = new 3(additions);
                if (removalsPending || movesPending || changesPending) {
                    long removeDuration = removalsPending ? getRemoveDuration() : 0L;
                    long moveDuration = movesPending ? getMoveDuration() : 0L;
                    long changeDuration = changesPending ? getChangeDuration() : 0L;
                    long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
                    View view2 = ((RecyclerView.ViewHolder) additions.get(0)).itemView;
                    ViewCompat.postOnAnimationDelayed(view2, adder, totalDelay);
                    return;
                }
                adder.run();
            }
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ ArrayList val$moves;

        1(ArrayList arrayList) {
            this.val$moves = arrayList;
        }

        public void run() {
            Iterator it = this.val$moves.iterator();
            while (it.hasNext()) {
                MoveInfo moveInfo = (MoveInfo) it.next();
                DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
            }
            this.val$moves.clear();
            DefaultItemAnimator.this.mMovesList.remove(this.val$moves);
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ ArrayList val$changes;

        2(ArrayList arrayList) {
            this.val$changes = arrayList;
        }

        public void run() {
            Iterator it = this.val$changes.iterator();
            while (it.hasNext()) {
                ChangeInfo change = (ChangeInfo) it.next();
                DefaultItemAnimator.this.animateChangeImpl(change);
            }
            this.val$changes.clear();
            DefaultItemAnimator.this.mChangesList.remove(this.val$changes);
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ ArrayList val$additions;

        3(ArrayList arrayList) {
            this.val$additions = arrayList;
        }

        public void run() {
            Iterator it = this.val$additions.iterator();
            while (it.hasNext()) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) it.next();
                DefaultItemAnimator.this.animateAddImpl(holder);
            }
            this.val$additions.clear();
            DefaultItemAnimator.this.mAdditionsList.remove(this.val$additions);
        }
    }

    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration()).alpha(0.0f).setListener(new 4(holder, animation, view)).start();
    }

    class 4 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        4(RecyclerView.ViewHolder viewHolder, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$holder = viewHolder;
            this.val$animation = viewPropertyAnimator;
            this.val$view = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchRemoveStarting(this.val$holder);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener((Animator.AnimatorListener) null);
            this.val$view.setAlpha(1.0f);
            DefaultItemAnimator.this.dispatchRemoveFinished(this.val$holder);
            DefaultItemAnimator.this.mRemoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        resetAnimation(holder);
        holder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(holder);
        return true;
    }

    void animateAddImpl(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mAddAnimations.add(holder);
        animation.alpha(1.0f).setDuration(getAddDuration()).setListener(new 5(holder, view, animation)).start();
    }

    class 5 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        5(RecyclerView.ViewHolder viewHolder, View view, ViewPropertyAnimator viewPropertyAnimator) {
            this.val$holder = viewHolder;
            this.val$view = view;
            this.val$animation = viewPropertyAnimator;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchAddStarting(this.val$holder);
        }

        public void onAnimationCancel(Animator animator) {
            this.val$view.setAlpha(1.0f);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener((Animator.AnimatorListener) null);
            DefaultItemAnimator.this.dispatchAddFinished(this.val$holder);
            DefaultItemAnimator.this.mAddAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int fromX2 = fromX + ((int) holder.itemView.getTranslationX());
        int fromY2 = fromY + ((int) holder.itemView.getTranslationY());
        resetAnimation(holder);
        int deltaX = toX - fromX2;
        int deltaY = toY - fromY2;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            view.setTranslationX(-deltaX);
        }
        if (deltaY != 0) {
            view.setTranslationY(-deltaY);
        }
        this.mPendingMoves.add(new MoveInfo(holder, fromX2, fromY2, toX, toY));
        return true;
    }

    void animateMoveImpl(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            view.animate().translationX(0.0f);
        }
        if (deltaY != 0) {
            view.animate().translationY(0.0f);
        }
        ViewPropertyAnimator animation = view.animate();
        this.mMoveAnimations.add(holder);
        animation.setDuration(getMoveDuration()).setListener(new 6(holder, deltaX, view, deltaY, animation)).start();
    }

    class 6 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ int val$deltaX;
        final /* synthetic */ int val$deltaY;
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        6(RecyclerView.ViewHolder viewHolder, int i, View view, int i2, ViewPropertyAnimator viewPropertyAnimator) {
            this.val$holder = viewHolder;
            this.val$deltaX = i;
            this.val$view = view;
            this.val$deltaY = i2;
            this.val$animation = viewPropertyAnimator;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchMoveStarting(this.val$holder);
        }

        public void onAnimationCancel(Animator animator) {
            if (this.val$deltaX != 0) {
                this.val$view.setTranslationX(0.0f);
            }
            if (this.val$deltaY != 0) {
                this.val$view.setTranslationY(0.0f);
            }
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener((Animator.AnimatorListener) null);
            DefaultItemAnimator.this.dispatchMoveFinished(this.val$holder);
            DefaultItemAnimator.this.mMoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        float prevTranslationX = oldHolder.itemView.getTranslationX();
        float prevTranslationY = oldHolder.itemView.getTranslationY();
        float prevAlpha = oldHolder.itemView.getAlpha();
        resetAnimation(oldHolder);
        int deltaX = (int) ((toX - fromX) - prevTranslationX);
        int deltaY = (int) ((toY - fromY) - prevTranslationY);
        oldHolder.itemView.setTranslationX(prevTranslationX);
        oldHolder.itemView.setTranslationY(prevTranslationY);
        oldHolder.itemView.setAlpha(prevAlpha);
        if (newHolder != null) {
            resetAnimation(newHolder);
            newHolder.itemView.setTranslationX(-deltaX);
            newHolder.itemView.setTranslationY(-deltaY);
            newHolder.itemView.setAlpha(0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    void animateChangeImpl(ChangeInfo changeInfo) {
        RecyclerView.ViewHolder holder = changeInfo.oldHolder;
        View view = holder == null ? null : holder.itemView;
        RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        View newView = newHolder != null ? newHolder.itemView : null;
        if (view != null) {
            ViewPropertyAnimator oldViewAnim = view.animate().setDuration(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX(changeInfo.toX - changeInfo.fromX);
            oldViewAnim.translationY(changeInfo.toY - changeInfo.fromY);
            oldViewAnim.alpha(0.0f).setListener(new 7(changeInfo, oldViewAnim, view)).start();
        }
        if (newView != null) {
            ViewPropertyAnimator newViewAnimation = newView.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimation.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new 8(changeInfo, newViewAnimation, newView)).start();
        }
    }

    class 7 extends AnimatorListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
        final /* synthetic */ View val$view;

        7(ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$changeInfo = changeInfo;
            this.val$oldViewAnim = viewPropertyAnimator;
            this.val$view = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.oldHolder, true);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$oldViewAnim.setListener((Animator.AnimatorListener) null);
            this.val$view.setAlpha(1.0f);
            this.val$view.setTranslationX(0.0f);
            this.val$view.setTranslationY(0.0f);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.oldHolder, true);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    class 8 extends AnimatorListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ View val$newView;
        final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

        8(ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$changeInfo = changeInfo;
            this.val$newViewAnimation = viewPropertyAnimator;
            this.val$newView = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.newHolder, false);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$newViewAnimation.setListener((Animator.AnimatorListener) null);
            this.val$newView.setAlpha(1.0f);
            this.val$newView.setTranslationX(0.0f);
            this.val$newView.setTranslationY(0.0f);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.newHolder, false);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.newHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    private void endChangeAnimation(List list, RecyclerView.ViewHolder item) {
        for (int i = list.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = (ChangeInfo) list.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                list.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder == item) {
            changeInfo.oldHolder = null;
            oldItem = true;
        } else {
            return false;
        }
        item.itemView.setAlpha(1.0f);
        item.itemView.setTranslationX(0.0f);
        item.itemView.setTranslationY(0.0f);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(RecyclerView.ViewHolder item) {
        View view = item.itemView;
        view.animate().cancel();
        for (int i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo moveInfo = (MoveInfo) this.mPendingMoves.get(i);
            if (moveInfo.holder == item) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            view.setAlpha(1.0f);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            view.setAlpha(1.0f);
            dispatchAddFinished(item);
        }
        for (int i2 = this.mChangesList.size() - 1; i2 >= 0; i2--) {
            ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i2);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) {
                this.mChangesList.remove(i2);
            }
        }
        for (int i3 = this.mMovesList.size() - 1; i3 >= 0; i3--) {
            ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i3);
            int j = moves.size() - 1;
            while (true) {
                if (j >= 0) {
                    MoveInfo moveInfo2 = (MoveInfo) moves.get(j);
                    if (moveInfo2.holder != item) {
                        j--;
                    } else {
                        view.setTranslationY(0.0f);
                        view.setTranslationX(0.0f);
                        dispatchMoveFinished(item);
                        moves.remove(j);
                        if (moves.isEmpty()) {
                            this.mMovesList.remove(i3);
                        }
                    }
                }
            }
        }
        for (int i4 = this.mAdditionsList.size() - 1; i4 >= 0; i4--) {
            ArrayList<RecyclerView.ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i4);
            if (additions.remove(item)) {
                view.setAlpha(1.0f);
                dispatchAddFinished(item);
                if (additions.isEmpty()) {
                    this.mAdditionsList.remove(i4);
                }
            }
        }
        if (this.mRemoveAnimations.remove(item)) {
        }
        if (this.mAddAnimations.remove(item)) {
        }
        if (this.mChangeAnimations.remove(item)) {
        }
        if (this.mMoveAnimations.remove(item)) {
        }
        dispatchFinishedWhenDone();
    }

    private void resetAnimation(RecyclerView.ViewHolder holder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        holder.itemView.animate().setInterpolator(sDefaultInterpolator);
        endAnimation(holder);
    }

    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int count = this.mPendingMoves.size();
        for (int i = count - 1; i >= 0; i--) {
            MoveInfo item = (MoveInfo) this.mPendingMoves.get(i);
            View view = item.holder.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            dispatchMoveFinished(item.holder);
            this.mPendingMoves.remove(i);
        }
        int count2 = this.mPendingRemovals.size();
        for (int i2 = count2 - 1; i2 >= 0; i2--) {
            dispatchRemoveFinished((RecyclerView.ViewHolder) this.mPendingRemovals.get(i2));
            this.mPendingRemovals.remove(i2);
        }
        int count3 = this.mPendingAdditions.size();
        for (int i3 = count3 - 1; i3 >= 0; i3--) {
            RecyclerView.ViewHolder item2 = (RecyclerView.ViewHolder) this.mPendingAdditions.get(i3);
            item2.itemView.setAlpha(1.0f);
            dispatchAddFinished(item2);
            this.mPendingAdditions.remove(i3);
        }
        int count4 = this.mPendingChanges.size();
        for (int i4 = count4 - 1; i4 >= 0; i4--) {
            endChangeAnimationIfNecessary((ChangeInfo) this.mPendingChanges.get(i4));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int listCount = this.mMovesList.size();
            for (int i5 = listCount - 1; i5 >= 0; i5--) {
                ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i5);
                int count5 = moves.size();
                for (int j = count5 - 1; j >= 0; j--) {
                    MoveInfo moveInfo = (MoveInfo) moves.get(j);
                    View view2 = moveInfo.holder.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    dispatchMoveFinished(moveInfo.holder);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(moves);
                    }
                }
            }
            int listCount2 = this.mAdditionsList.size();
            for (int i6 = listCount2 - 1; i6 >= 0; i6--) {
                ArrayList<RecyclerView.ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i6);
                int count6 = additions.size();
                for (int j2 = count6 - 1; j2 >= 0; j2--) {
                    RecyclerView.ViewHolder item3 = (RecyclerView.ViewHolder) additions.get(j2);
                    item3.itemView.setAlpha(1.0f);
                    dispatchAddFinished(item3);
                    additions.remove(j2);
                    if (additions.isEmpty()) {
                        this.mAdditionsList.remove(additions);
                    }
                }
            }
            int listCount3 = this.mChangesList.size();
            for (int i7 = listCount3 - 1; i7 >= 0; i7--) {
                ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i7);
                int count7 = changes.size();
                for (int j3 = count7 - 1; j3 >= 0; j3--) {
                    endChangeAnimationIfNecessary((ChangeInfo) changes.get(j3));
                    if (changes.isEmpty()) {
                        this.mChangesList.remove(changes);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(List list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            ((RecyclerView.ViewHolder) list.get(i)).itemView.animate().cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }
}
