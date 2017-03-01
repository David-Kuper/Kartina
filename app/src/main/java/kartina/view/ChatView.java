package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import kartina.R;

/**
 * Created by David on 2016/11/11.
 */

public class ChatView extends FrameLayout {
    private EditText editText;
    private Button sendBtn;
    private ChatViewListener chatViewListener;


    public ChatView(Context context) {
        this(context,null);
    }

    public ChatView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setListener();
    }

    private void initView(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.comment_reply_view,this,false);
        editText = (EditText) view.findViewById(R.id.reply);
        sendBtn = (Button) view.findViewById(R.id.sendBtn);
        addView(view);
    }

    private void setListener(){
        if (sendBtn == null)
            return;
        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                if (content.equals("")){
                    Toast.makeText(getContext(),"发送内容不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    if (chatViewListener != null){
                        chatViewListener.OnSendMsg(content);
                        boolean flag = sendMsg(content);
                        if (flag){
                            chatViewListener.OnSendSuccess();
                        }else {
                            chatViewListener.OnSendFailed();
                        }
                    }
                }
            }
        });
    }

    private boolean sendMsg(String content){
        boolean result = false;
        return result;
    }

    public void setChatViewListener(ChatViewListener chatViewListener) {
        if (chatViewListener == null)
            return;
        this.chatViewListener = chatViewListener;
    }

    public interface ChatViewListener{
        void OnSendMsg(String content);
        void OnSendSuccess();
        void OnSendFailed();
    }
}
