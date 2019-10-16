package cc.zenking.edu.zhjx.ui.messge;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanhy.library_tools.image.GlideUtil;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.ui.mine.MLAdapter;


public class MLMessageAdapter extends BaseAdapter {

    public ArrayList<MLMessageModel> dataSourse=new ArrayList<>();
    private LayoutInflater mlInflater;
    Context context;

    public  MLMessageAdapter(Context c){
        context=c;
        mlInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return dataSourse.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = (View) mlInflater.inflate(R.layout.mlmessagecell, null);
            ImageView imageView = view.findViewById(R.id.messageIcon);
            TextView textView =  view.findViewById(R.id.messageTitle);
            TextView content = view.findViewById(R.id.messageContent);
            TextView time = view.findViewById(R.id.messageTime);
            view.setTag(new Holder(imageView,textView,content,time));
        }
        Holder holder = (Holder) view.getTag();
        holder.nameLabel.setText(dataSourse.get(i).title);
        holder.contentLabel.setText(dataSourse.get(i).content);
        holder.timeLabel.setText(dataSourse.get(i).time);
        GlideUtil.getGlideImageViewUtil().setUserIco(context,dataSourse.get(i).pic,holder.imageView);
        return view;
    }

    private  class Holder{
        ImageView imageView;
        TextView nameLabel;
        TextView contentLabel;
        TextView timeLabel;
        public  Holder(ImageView image,TextView title,TextView content,TextView time){
            imageView = image;
            nameLabel = title;
            contentLabel = content;
            timeLabel = time;
        }

    }

    public void setDataSourse(ArrayList<MLMessageModel> dataSourse) {

        this.dataSourse = dataSourse;
        notifyDataSetChanged();
    }
}
