package demo.jyl.com.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import demo.jyl.com.mydemo.R;

/**
 * 底部EditText
 *
 * @author Jack Boy
 */
public class EditButtonView extends RelativeLayout implements View.OnClickListener {

    private OnButtonClickListener listener;
    private EditText editText;
    private Button btnSend;

    public EditButtonView(Context context) {
        super(context);
        setView(context);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    public EditButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setView(context);
    }

    public EditButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView(context);
    }

    private void setView(Context context) {
        addView(LayoutInflater.from(context).inflate(R.layout.edit_bottom_view_layout, null));
        btnSend = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.edittext);

        btnSend.setOnClickListener(this);
    }

    public String getContent(){
        return editText.getText().toString();
    }

    public EditText getEditText(){
        return editText;
    }

    public Button getBtnSend(){
        return btnSend;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if(listener != null) listener.onButtonClick();
                break;
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }
}
