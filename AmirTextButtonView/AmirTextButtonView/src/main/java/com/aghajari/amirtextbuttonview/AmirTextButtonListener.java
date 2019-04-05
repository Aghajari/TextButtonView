package com.aghajari.amirtextbuttonview;

public interface AmirTextButtonListener {

    /**
     * Data : new text data if state is STATE_TEXTSHOW
     */
    void onStateChanged (int newState,AmirTextData Data);
    boolean canAcceptText (CharSequence text);
}
