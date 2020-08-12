package com.example.fitnessgym.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.PagerAdapter;


import com.example.fitnessgym.Activity.MainActivity;
import com.example.fitnessgym.Model.ModelSliderImg;
import com.example.fitnessgym.R;

import java.util.ArrayList;

/**
 * Created by Altayeb on 10/25/2018.
 */
public class SlideShow_adapter_main extends PagerAdapter {
    private Context context;
    LayoutInflater inflater;



    public ArrayList<ModelSliderImg> modelSliderImgArrayList;
    int detail;


    public SlideShow_adapter_main(Context context, ArrayList<ModelSliderImg> arrayList) {
        this.context = context;

        this.modelSliderImgArrayList=arrayList;
        //this.detail=det;
      }





    @Override
    public int getCount() {

            return modelSliderImgArrayList.size();
        }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.main_slider_view,container,false);
        TextView textView_title = view.findViewById(R.id.textView_title);
        TextView textView_body = view.findViewById(R.id.textView_body);
        ImageView imgView=view.findViewById(R.id.imgView);
        AppCompatButton appCompatButton=view.findViewById(R.id.slider_btn);

        ModelSliderImg modelSlideShowImg = new ModelSliderImg();
        modelSlideShowImg = modelSliderImgArrayList.get(position);

        textView_title.setText(modelSlideShowImg.getTitle());
        textView_body.setText(modelSlideShowImg.getBody());
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("page","classes");
                context.startActivity(intent);
            }
        });


//            Glide.with(context).load(modelSlideShowImg.getImg_url())
//                    .into(imgView);


            container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
    
}
