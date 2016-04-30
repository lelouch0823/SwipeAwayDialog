package com.labo.kaji.swipeawaydialog;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author kakajika
 * @since 15/08/15.
 */
public class SwipeAwayDialogFragment extends DialogFragment {

    private boolean mSwipeable = true;
    private boolean mSwipeLayoutGenerated = false;

    /**
     * Set whether dialog can be swiped away.
     */
    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }

    /**
     * Get whether dialog can be swiped away.
     */
    public boolean isSwipeable() {
        return mSwipeable;
    }

    /**
     * Called when dialog is swiped away to dismiss.
     * @return false to prevent dismissing
     */
    public boolean onSwipedAway() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            ViewGroup decorView = (ViewGroup)window.getDecorView();
            View content = decorView.getChildAt(0);
            decorView.removeView(content);

            SwipeableFrameLayout layout = new SwipeableFrameLayout(getActivity());
            layout.addView(content);
            decorView.addView(layout);

            SwipeDismissTouchListener listener = new SwipeDismissTouchListener(decorView, "layout", new SwipeDismissTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Object token) {
                    return isCancelable() && mSwipeable;
                }

                @Override
                public void onDismiss(View view, Object token) {
                    if (onSwipedAway()) {
                        dismiss();
                    }
                }
            });
            layout.setSwipeDismissTouchListener(listener);
            layout.setOnTouchListener(listener);
            layout.setClickable(true);
            mSwipeLayoutGenerated = true;
        }
    }

}
