# AmirTextButtonView-Android-Java
<img src="https://github.com/Aghajari/AmirTextButtonView-Android-Java/blob/master/animation.gif" width=300 title="Screen">

flat button + edit text view. (subscribe button

- Customize Theme,Colors,Texts,Sizes,...
- Customize EditText/Icon
- Customize Subscribe/Unsubscribe button
- Customize Animations
- add multiple texts (Ex. Thanks for subscribing)
- Keyboard Manager

...

**Version 1.02**
# Whats New ?

Version 1.01 :
- buttonClick event
- ShowTexts programmatically
- toggle Buttons programmatically
- get icon view
- set edittext margin (left/right)
- isAnimationRunning method
- bugs fixed

## Listener
```
    /** Data : new text data if state is STATE_TEXTSHOW */
    void onStateChanged (int newState,AmirTextData Data)

    /**
     * icon click will call this event. return true if can submit text.
     */
    boolean canAcceptText (CharSequence text)

    /**
     * STATE_BUTTON (ButtonClick)
     * STATE_BUTTON2 (Button2Click)
     * STATE_EDITTEXT (IconClick)
     *
     * return true if can do next step!
     */
    boolean buttonClick (int state)
```

## Example
```
        textbtn = findViewById(R.id.txtbtn);
        
        //set listener
        textbtn.setListener(new AmirTextButtonListener() {
            @Override
            public void onStateChanged(int newState, AmirTextData Data) {}

            @Override
            public boolean canAcceptText(CharSequence text) {
                if (text.toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"email is empty!",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                return true;
            }

            @Override
            public boolean buttonClick(int state) {
                return true;
            }
        });
        
        // set button data
        textbtn.setButtonData(new AmirTextData("SUBSCRIBE",300)); '300 -> Animation duration
        
        //set button background
        textbtn.setRadius(textbtn.dp(60));
        GradientDrawable GD = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.rgb(4,168,198),Color.rgb(0,152,240)});
        GD.setShape(GradientDrawable.RECTANGLE);
        GD.setCornerRadius(textbtn.getRadius());
        textbtn.setButtonBackground(GD);
        
        // set view background
        GradientDrawable CD = new GradientDrawable();
        CD.setColor(Color.WHITE);
        CD.setShape(GradientDrawable.RECTANGLE);
        CD.setCornerRadius(textbtn.getRadius());
        textbtn.setDefaultBackground(CD);
        
        // add icon
        Drawable image;
        image = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.send));
        DrawableCompat.setTint(DrawableCompat.wrap(image),Color.WHITE);
        textbtn.setIcon(image);
        textbtn.setIconWidth(textbtn.dp(20),textbtn.dp(48));


        textbtn.setEditTextData("enter your email...",Color.GRAY,16,Color.BLACK, Typeface.DEFAULT);
        textbtn.setEditTextMargin(textbtn.dp(18));

        textbtn.addText(new AmirTextData("THANKS FOR SUBSCRIBING",250,1000));
        //textbtn.addText(new AmirTextData("THANKS FOR SUBSCRIBING2",250,1000));

        textbtn.setKeyboardManager(false,true); //only hide keyboard with submit

        textbtn.build(); //create views
        textbtn.setBaseElevation(textbtn.dp(8)); //set shadow size
```

## Author
Amir Hossein Aghajari

Telegram @KingAmir272
