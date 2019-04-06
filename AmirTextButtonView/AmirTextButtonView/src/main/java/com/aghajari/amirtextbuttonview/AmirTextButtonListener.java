package com.aghajari.amirtextbuttonview;

/**
 * @author Amir Hossein Aghajari
 */
public interface AmirTextButtonListener {

    /**
     * Data : new text data if state is STATE_TEXTSHOW
     */
    void onStateChanged (int newState,AmirTextData Data);

    /**
     * icon click will call this event. return true if can submit text.
     */
    boolean canAcceptText (CharSequence text);

    /**
     * STATE_BUTTON (ButtonClick)
     * STATE_BUTTON2 (Button2Click)
     * STATE_EDITTEXT (IconClick)
     *
     * return true if can do next step!
     */
    boolean buttonClick (int state);

}
