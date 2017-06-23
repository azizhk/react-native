/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react.views.scroll;

import javax.annotation.Nullable;

import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * This Class helps to calculate the velocity for all ScrollView. The x and y velocity
 * will later on send to ReactScrollViewHelper for further use.
 *
 * Created by wenjiam on 6/20/17.
 */
public class VelocityHelper {

  private @Nullable VelocityTracker mVelocityTracker;
  private float mXVelocity;
  private float mYVelocity;

  /**
   * Call from a ScrollView in onTouchEvent.
   * Calculating the velocity for END_DRAG movement and send them back to react ScrollResponder.js
   * */
  public void calculateVelocity(MotionEvent ev) {
    int action = ev.getAction() & MotionEvent.ACTION_MASK;
    if (mVelocityTracker == null) {
      mVelocityTracker = mVelocityTracker.obtain();
    }

    switch (action) {
      case MotionEvent.ACTION_MOVE: {
        mVelocityTracker.addMovement(ev);
        break;
      }
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL: {
        // Calculate velocity on END_DRAG
        mVelocityTracker.computeCurrentVelocity(1); // points/millisecond
        mXVelocity = mVelocityTracker.getXVelocity();
        mYVelocity = mVelocityTracker.getYVelocity();

        mVelocityTracker.recycle();
        break;
      }
    }
  }

  /* Needs to call ACTION_UP/CANCEL to update the mXVelocity */
  public float getXVelocity() { return mXVelocity; }

  /* Needs to call ACTION_UP/CANCEL to update the mYVelocity */
  public float getYVelocity() { return mYVelocity; }
}
