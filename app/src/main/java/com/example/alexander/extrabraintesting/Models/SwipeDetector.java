package com.example.alexander.extrabraintesting.Models;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeDetector implements OnTouchListener {

    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None, // when no action was detected
        Click
    }

    // Const variables
    private static final int MIN_DISTANCE = 100;
    // Variables
    private float downX, downY;
    // Set first action to none on init.
    private Action mSwipeDetected = Action.None;

    // Current action
    public Action getAction() {
        return mSwipeDetected;
    }

    // On touch method calculating user gesture
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            // On press down on screen
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false; // Allow other events like Click to be processed, in case it's not a swipe
            }
            // On press up from screen
            case MotionEvent.ACTION_UP: {

                // Get X and Y from event/user gesture
                float upX = event.getX();
                float upY = event.getY();

                // Calculate deltaX and deltaY
                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // Horizontal swipe detection
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left-to-Right Swipe
                    if (deltaX < 0) {

                        mSwipeDetected = Action.LR;
                        return false;
                    }
                    // Right-to-Left Swipe
                    if (deltaX > 0) {
                        mSwipeDetected = Action.RL;
                        return false;
                    }
                }
                else

                    // Vertical swipe detection
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // Top-to-Bottom Swipe
                        if (deltaY < 0) {
                            // TODO: Check if hit top of list. If so, add new time-entry?
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        // Bottom-to-Top Swipe
                        if (deltaY > 0) {
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                // If none above, its a simple click.
                mSwipeDetected = Action.Click;
                return false;
            }
        }
        return false;
    }

}
