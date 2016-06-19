package com.blogspot.adjanybekov.test7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    MyMemo m1;
    List<MyMemo> memoList;
    MyDBHandler dBaccess;
    Button goToTextButton;
    ListView memoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToTextButton = (Button) findViewById(R.id.IdAddButton);


        memoList = new ArrayList<>();
         m1 = new MyMemo("title1sdvafbsdfbfdbsfdbfdbsfdsfdbsfdbsdfbsfdbsfbdsfdsbsdfb","text1");
        memoList.add(m1);

        this.dBaccess = MyDBHandler.getInstance(this);

        //this line of code cases an error
        this.memoView = (ListView) findViewById(R.id.IdListView);

        memoView.setOnItemClickListener(
                new ListView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyMemo memo = (MyMemo)(parent.getItemAtPosition(position));
                        onItemClicked(memo);
                    }
                }
        );
        goToTextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAddClicked();
                    }
                }
        );


    }

    @Override
    protected void onResume() {
        super.onResume();
        dBaccess.open();

        this.memoList = dBaccess.getAllMyMemos();
        for(MyMemo memo: memoList){
            System.out.println("InLoop"+memo.getTitle());
        }
        MemoAdapter memoAdapter = new MemoAdapter(this,memoList);
        this.memoView.setAdapter(memoAdapter);
        dBaccess.close();
    }

    public void onItemClicked(MyMemo memo){
        Intent goToTextActivity = new Intent(this, Textfield.class);
        goToTextActivity.putExtra("TitleToEdit", memo.getTitle());
        goToTextActivity.putExtra("TextToEdit", memo.getText());
        startActivity(goToTextActivity);
    }

    public void onAddClicked(){
        Intent goToTextActivity = new Intent(this, Textfield.class);
        startActivity(goToTextActivity);
    }






    public class MemoAdapter extends ArrayAdapter<MyMemo> {


        ImageView delMemo;
        MyMemo memo;
        //MyDBHandler dBaccess;
        MainActivity mainActivity = new MainActivity();
        public MemoAdapter(Context context, List<MyMemo> resource) {
            super(context, 0, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater myInflater = LayoutInflater.from(getContext());

            View customView = myInflater.inflate(R.layout.memo_item, parent ,false);
            delMemo = (ImageView) customView.findViewById(R.id.IdDelView);


            //inside mamoview set Text view and image

            memo = getItem(position);
            String titleStr = getItem(position).getTitle();
            System.out.println(titleStr+"---------------------------------");
            TextView titleView = (TextView) customView.findViewById(R.id.IdMemoView);
            final ImageView delImage = (ImageView) customView.findViewById(R.id.IdDelView);



            titleView.setText(titleStr);
            delImage.setImageResource(R.drawable.delete);
            delMemo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                dBaccess.open();
                                dBaccess.delete(memo);
                                dBaccess.close();

                                ArrayAdapter<MyMemo> adapter = (ArrayAdapter<MyMemo>) memoView.getAdapter();
                                adapter.remove(memo);
                                adapter.notifyDataSetChanged();
                        }
                    }
            );



            return customView;
        }
    }

}