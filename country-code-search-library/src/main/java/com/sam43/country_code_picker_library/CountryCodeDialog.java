package com.sam43.country_code_picker_library;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by hbb20 on 11/1/16.
 */
class CountryCodeDialog {
    private static final Field
            sEditorField,
            sCursorDrawableField,
            sCursorDrawableResourceField;
    static BottomSheetDialog modal;
    static Context context;

    static {
        Field editorField = null;
        Field cursorDrawableField = null;
        Field cursorDrawableResourceField = null;
        boolean exceptionThrown = false;
        try {
            cursorDrawableResourceField = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableResourceField.setAccessible(true);
            final Class<?> drawableFieldClass;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldClass = TextView.class;
            } else {
                editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                drawableFieldClass = editorField.getType();
            }
            cursorDrawableField = drawableFieldClass.getDeclaredField("mCursorDrawable");
            cursorDrawableField.setAccessible(true);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            sEditorField = null;
            sCursorDrawableField = null;
            sCursorDrawableResourceField = null;
        } else {
            sEditorField = editorField;
            sCursorDrawableField = cursorDrawableField;
            sCursorDrawableResourceField = cursorDrawableResourceField;
        }
    }
    public static void openCountryCodeModal(final CountryCodePicker codePicker, final String countryNameCode) {
        context = codePicker.getContext();
        codePicker.refreshCustomMasterList();
        codePicker.refreshPreferredCountries();
        List<CCPCountry> masterCountries = CCPCountry.getCustomMasterCountryList(context, codePicker);

        modal = new BottomSheetDialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View sheet = inflater.inflate(R.layout.layout_country_list_modal, null);
        modal.setContentView(sheet);

        //keyboard
        if (codePicker.isSearchAllowed() && codePicker.isDialogKeyboardAutoPopup()) {
            modal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            modal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }


        //dialog views
        RecyclerView recyclerView_countryDialog = modal.findViewById(R.id.recycler_countryDialog);
        final TextView textViewTitle = modal.findViewById(R.id.textView_title);
        RelativeLayout rlQueryHolder = modal.findViewById(R.id.rl_query_holder);
        LinearLayout llSearchBoxLayout = modal.findViewById(R.id.search_box_layout);
        final TextInputEditText editText_search = modal.findViewById(R.id.editText_search);
        TextView textView_noResult = modal.findViewById(R.id.textView_noresult);
        RelativeLayout rlHolder = modal.findViewById(R.id.rl_holder);
        ImageView imgDismiss = modal.findViewById(R.id.img_dismiss);

        // type faces
        //set type faces
        try {
            if (codePicker.getDialogTypeFace() != null) {
                if (codePicker.getDialogTypeFaceStyle() != CountryCodePicker.DEFAULT_UNSET) {
                    textView_noResult.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle());
                    editText_search.setTypeface(codePicker.getDialogTypeFace(), codePicker.getDialogTypeFaceStyle());
                } else {
                    textView_noResult.setTypeface(codePicker.getDialogTypeFace());
                    editText_search.setTypeface(codePicker.getDialogTypeFace());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //dialog background color
        if (codePicker.getDialogBackgroundColor() != 0) {
            rlHolder.setBackgroundColor(codePicker.getDialogBackgroundColor());
        }

        if (codePicker.getDialogBackgroundResId() != 0) {
            rlHolder.setBackgroundResource(codePicker.getDialogBackgroundResId());
        }

        //close button visibility
        if (codePicker.isShowCloseIcon()) {
            imgDismiss.setVisibility(View.VISIBLE);
            imgDismiss.setOnClickListener(view -> modal.dismiss());
        } else {
            imgDismiss.setVisibility(View.GONE);
        }

        if (codePicker.getModalSearchBoxBackgroundColor() != 0)
            llSearchBoxLayout.setBackgroundColor(codePicker.getModalSearchBoxBackgroundColor());

        //clear button color and title color
        if (codePicker.getDialogTextColor() != 0) {
            int textColor = codePicker.getDialogTextColor();
            //imgClearQuery.setColorFilter(textColor);
            imgDismiss.setColorFilter(textColor);
            //textViewTitle.setTextColor(textColor);
            textView_noResult.setTextColor(textColor);
            editText_search.setTextColor(textColor);
            editText_search.setHintTextColor(Color.argb(100, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }


        //editText properties and tint
        if (codePicker.getDialogSearchEditTextTintColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.setBackgroundTintList(ColorStateList.valueOf(codePicker.getDialogSearchEditTextTintColor()));
                setCursorColor(editText_search, codePicker.getModalSearchBoxBackgroundColor());
            }
        }

        if (codePicker.getModalSearchEditTextColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.setTextColor(codePicker.getModalSearchEditTextColor());
                setCursorColor(editText_search, codePicker.getDialogSearchEditTextTintColor());
            }
        }

        if (codePicker.getModalSearchEditTextHintColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.setHintTextColor(codePicker.getModalSearchEditTextHintColor());
            }
        }

/*        if (codePicker.getModalSearchEditTextCursorColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText_search.setBackgroundTintList(ColorStateList.valueOf(codePicker.getDialogSearchEditTextTintColor()));
                setCursorColor(editText_search, codePicker.getDialogSearchEditTextTintColor());
            }
        }*/


        //add messages to views
        editText_search.setHint(codePicker.getSearchHintText());
        textView_noResult.setText(codePicker.getNoResultACK());

        //this will make dialog compact
        if (!codePicker.isSearchAllowed()) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView_countryDialog.getLayoutParams();
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            recyclerView_countryDialog.setLayoutParams(params);
        }

        final CountryCodeAdapter cca = new CountryCodeAdapter(context, masterCountries, codePicker, rlQueryHolder, editText_search, textView_noResult, modal);
        recyclerView_countryDialog.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_countryDialog.setAdapter(cca);

        //fast scroller
        FastScroller fastScroller = modal.findViewById(R.id.fastscroll);
        fastScroller.setRecyclerView(recyclerView_countryDialog);
        if (codePicker.isShowFastScroller()) {
            if (codePicker.getFastScrollerBubbleColor() != 0) {
                fastScroller.setBubbleColor(codePicker.getFastScrollerBubbleColor());
            }

            if (codePicker.getFastScrollerHandleColor() != 0) {
                fastScroller.setHandleColor(codePicker.getFastScrollerHandleColor());
            }

            if (codePicker.getFastScrollerBubbleTextAppearance() != 0) {
                try {
                    fastScroller.setBubbleTextAppearance(codePicker.getFastScrollerBubbleTextAppearance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            fastScroller.setVisibility(View.GONE);
        }

        modal.setOnDismissListener(dialogInterface -> {
            hideKeyboard(context);
            if (codePicker.getDialogEventsListener() != null) {
                codePicker.getDialogEventsListener().onCcpDialogDismiss(dialogInterface);
            }
        });

        modal.setOnCancelListener(dialogInterface -> {
            hideKeyboard(context);
            if (codePicker.getDialogEventsListener() != null) {
                codePicker.getDialogEventsListener().onCcpDialogCancel(dialogInterface);
            }
        });

        //auto scroll to mentioned countryNameCode
        if (countryNameCode != null) {
            boolean isPreferredCountry = false;
            if (codePicker.preferredCountries != null) {
                for (CCPCountry preferredCountry : codePicker.preferredCountries) {
                    if (preferredCountry.nameCode.equalsIgnoreCase(countryNameCode)) {
                        isPreferredCountry = true;
                        break;
                    }
                }
            }

            //if selection is from preferred countries then it should show all (or maximum) preferred countries.
            // don't scroll if it was one of those preferred countries
            if (!isPreferredCountry) {
                int preferredCountriesOffset = 0;
                if (codePicker.preferredCountries != null && codePicker.preferredCountries.size() > 0) {
                    preferredCountriesOffset = codePicker.preferredCountries.size() + 1; //+1 is for divider
                }
                for (int i = 0; i < masterCountries.size(); i++) {
                    if (masterCountries.get(i).nameCode.equalsIgnoreCase(countryNameCode)) {
                        recyclerView_countryDialog.scrollToPosition(i + preferredCountriesOffset);
                        break;
                    }
                }
            }
        }

        modal.show();
        if (codePicker.getDialogEventsListener() != null) {
            codePicker.getDialogEventsListener().onCcpDialogOpen(modal);
        }
    }


    private static void hideKeyboard(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    static void setCursorColor(EditText editText, int color) {
        if (sCursorDrawableField == null) {
            return;
        }
        try {
            final Drawable drawable = getDrawable(editText.getContext(),
                    sCursorDrawableResourceField.getInt(editText));
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            sCursorDrawableField.set(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN
                    ? editText : sEditorField.get(editText), new Drawable[]{drawable, drawable});
        } catch (Exception ignored) {

        }
    }

    static void clear() {
        if (modal != null) {
            modal.dismiss();
        }
        modal = null;
        context = null;
    }

    private static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }
}
