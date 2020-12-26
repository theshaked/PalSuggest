package com.example.palsuggest;

import android.util.Patterns;
import android.widget.EditText;



public class EditTextValidator {

    private final static String atLeastOneLetter  = ".*[a-zA-Z]+.*";
    private final static String positiveNumeric  = "^[1-9][0-9]*$" ;
    private final static String validUsername  = "^[a-z][a-z0-9]{3,19}$"; //start with letter and length is 4-20 numbers or chars
    private final static String validPassword  = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-z0-9]{6,})$*"; //Minimum eight characters, at least one letter and one number

    public static boolean IsUsernameValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(validUsername))
        {
            editText.setError("שם המשתמש חייב להתחיל באות לטינית ולהכיל בין 4 ל20 מספרים ואותיות לטיניות");
            return false;
        }
        return true;
    }

    public static boolean IsPasswordValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(validPassword))
        {
            editText.setError("סיסמה חייבת להכיל לפחות 8 אותיות ומספרים ולפחות מספר ואות לטינית אחת");
            return false;
        }
        return true;
    }

    public static boolean IsUserTextValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(atLeastOneLetter))
        {
            editText.setError(editText.getHint()+" חייב להכיל לפחות אות אחת ");
            return false;
        }
        return true;
    }

    public static boolean UserTextNotEmpty(EditText editText)
    {
        if (editText.getText() == null || (editText.getText().toString().isEmpty()))
        {
            editText.setError("בבקשה הכנס את " + editText.getHint());
            return false;
        }
        return true;
    }

    public static boolean IsUserPriceValid(EditText editTextPrice)
    {
        UserTextNotEmpty(editTextPrice);

        if (!editTextPrice.getText().toString().matches(positiveNumeric))
        {
            editTextPrice.setError(editTextPrice.getHint()+" חייב להכיל סכום מספרי חיובי");
            return false;
        }
        return true;
    }

    public static boolean IsUserLinkValid(EditText editTextLink)
    {
        if (UserTextNotEmpty(editTextLink) && !Patterns.WEB_URL.matcher(editTextLink.getText()).matches()) //Valid text with invalid link
        {
            editTextLink.setError("כתובת המוצר אינה חוקית");
            return false;
        }
        return true;
    }

}
