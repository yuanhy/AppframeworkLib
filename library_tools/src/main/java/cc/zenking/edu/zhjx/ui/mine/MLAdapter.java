package cc.zenking.edu.zhjx.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanhy.library_tools.image.GlideUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;

import static cc.zenking.edu.zhjx.R.color.color_000000;
import static cc.zenking.edu.zhjx.R.color.color_f2f2f2;

public class MLAdapter extends BaseAdapter {

    public ArrayList<Child> dataSourse=new ArrayList<>();
    //    转换器将XML文件装换成对象
    private LayoutInflater mlInflater;
    Context context;
  public   MLAdapter(Context c) {
      context=c;
      mlInflater = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return dataSourse.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
          view = (View)mlInflater.inflate(R.layout.mlperson_cell,null);
          ImageView imageView = view.findViewById(R.id.personIcon);
          TextView text = view.findViewById(R.id.personText);
          view.setTag(new Holder(imageView,text));
        }

        Holder holder = (Holder) view.getTag();
        TextView nameLabel = holder.nameLabel;
        nameLabel.setTextColor(Color.parseColor("#333333"));

        if (i == dataSourse.size()){
            GlideUtil.getGlideImageViewUtil().setUserIco(context,R.drawable.child_add,holder.imageView);
            nameLabel.setText("添加孩子");
            nameLabel.setTextColor(Color.BLACK);
            return view;
        }
        GlideUtil.getGlideImageViewUtil().setUserIco(context,dataSourse.get(i).portrait,holder.imageView);
        nameLabel.setText(dataSourse.get(i).name);
        return view;
    }

    private  class Holder{
        ImageView imageView;
        TextView nameLabel;
        public  Holder(ImageView image, TextView text){
            imageView = image;
            nameLabel = text;
        }

    }

    public void setDataSourse(ArrayList<Child> dataSourse) {
      this.dataSourse=dataSourse;

        notifyDataSetChanged();

    }
}

