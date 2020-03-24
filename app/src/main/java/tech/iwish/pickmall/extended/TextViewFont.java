package tech.iwish.pickmall.extended;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import tech.iwish.pickmall.R;


public class TextViewFont extends TextView {

    public TextViewFont(Context context) {
        super(context);
        changefont(null);
    }

    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        changefont(attrs);

    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changefont(attrs);
    }

//    public TextViewFont(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    public void changefont(AttributeSet attributeSet){
        Typeface typeface =Typeface.createFromAsset(getContext().getAssets(),"SF-Compact-Text-Medium.otf");
            if(attributeSet != null){
                TypedArray aa = getContext().obtainStyledAttributes(attributeSet, R.styleable.ProximaFonts);
                String fontvalue = aa.getString(R.styleable.ProximaFonts_fonttype);
                try {
                    if (fontvalue != null) {
                        switch (fontvalue) {
                            case "1":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-Compact-Text-Medium.otf");
                                break;
                            case "2":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-Compact-Text-MediumItalic.otf");
                                break;
                            case "3":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-Compact-Text-Regular.otf");
                                break;
                            case "4":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-Compact-Text-Semibold.otf");
                                break;
                            case "5":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Display-Black.otf");
                                break;
                            case "6":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Display-Light.otf");
                                break;
                            case "7":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Display-Regular.otf");
                                break;
                            case "8":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Display-Ultralight.otf");
                                break;
                            case "9":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Text-MediumItalic.otf");
                                break;
                            case "10":
                                typeface = Typeface.createFromAsset(getContext().getAssets(), "SF-UI-Text-RegularItalic.otf");
                                break;

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setTypeface(typeface);

                aa.recycle();

            }
    }
}
