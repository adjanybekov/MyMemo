package com.blogspot.adjanybekov.ZhurnalZametok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Textfield extends AppCompatActivity {
    EditText title ;
    EditText text;
    MyMemo memo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textfield);

        title = (EditText) findViewById(R.id.IdTitleEditText);
        text = (EditText) findViewById(R.id.IdTextEdit);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            memo = new MyMemo(bundle.getString("TitleToEdit"),bundle.getString("TextToEdit"));
            //memo = new MyMemo(bundle.getString("TitleToEdit"),bundle.getString("TextToEdit"));
            if(!(bundle.getString("TitleToEdit") + bundle.getString("TextToEdit")).isEmpty()) {
                title.setText(bundle.getString("TitleToEdit"));
                text.setText(bundle.getString("TextToEdit"));
            }
        }


        };
        //memo = new MyMemo(bundle.getString("TitleToEdit"),bundle.getString("TextToEdit"));

    public void saveClicked(View view){

        MyDBHandler myDBHandler = MyDBHandler.getInstance(this);
        myDBHandler.open();

        if(memo == null) {
            // Add new memo
            MyMemo temp = new MyMemo();
            temp.setText(text.getText().toString());
            temp.setTitle(title.getText().toString());
            myDBHandler.save(temp);
        } else {
            // Update the existing memo
            memo.setText(text.getText().toString());
            memo.setTitle(title.getText().toString());
            myDBHandler.update(memo);
        }
        myDBHandler.close();
        this.finish();
    }


    public void cancelClicked(View view){
            this.finish();
    }
}
