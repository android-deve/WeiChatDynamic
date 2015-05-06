package demo.jyl.com.mydemo.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import demo.jyl.com.mydemo.R;
import demo.jyl.com.mydemo.model.Comment;
import demo.jyl.com.mydemo.model.Dynamic;

/**
 * 数据中心，存储获取数据
 * @author Jack Boy
 */
public class DataCenter {

    private static DataCenter instance;
    private Context context;

    private DataCenter(){};

    public static DataCenter getInstance(){
        if(instance == null){
            synchronized (DataCenter.class){
                instance = new DataCenter();
            }
        }
        return instance;
    }

    /**
     * 头像图片地址
     * @return
     */
    private String[] getLogoImages(){
        return context.getResources().getStringArray(R.array.user_logo);
    }

    /**
     * 获取动态图片
     * @return
     */
    private String[] getDynamicImages(){
        return context.getResources().getStringArray(R.array.images_array);
    }

    /**评论内容*/
    private String[] getComments(){
        return context.getResources().getStringArray(R.array.comment_array);
    }

    /**用户名*/
    private String[] getUserName(){
        return  context.getResources().getStringArray(R.array.user_name);
    }

    /**动态列表数据*/
    public List<Dynamic> getDynamicList(Context context){
        this.context = context;
        // 头像
        String[] logoUrl = DataCenter.getInstance().getLogoImages();
        // 动态图片
        String[] imags = DataCenter.getInstance().getDynamicImages();

        ArrayList<Dynamic> dynamics = new ArrayList<Dynamic>();
        for(int i = 0; i < 20; i ++){
            Dynamic dynamic = new Dynamic();
            dynamic.setLogoUrl(getRandomValue(logoUrl));// 随机取一张图片
            dynamic.setName(getRandomValue(getUserName()));//随机设置一个名字

            // 图片GridView数据
            List<String> imageList = new ArrayList<String>();
            for(int j = 0; j < new Random().nextInt(imags.length) + 1; j ++ ){
                imageList.add(getRandomValue(imags));// 随机取一张图片
            }

            // 评论ListView数据
            List<Comment> commentList = new ArrayList<Comment>();
            for(int index = 0; index < new Random().nextInt(getUserName().length); index++){
                Comment comment = new Comment();
                comment.setUsername(getRandomValue(getUserName()));
                comment.setContent(":" + getRandomValue(getComments()));
                commentList.add(comment);
            }

            dynamic.setImageList(imageList);
            dynamic.setCommentList(commentList);
            dynamics.add(dynamic);
        }
        return dynamics;
    }

    /**随机获取数组中的一个值*/
    public String getRandomValue(String[] values){
        return values[new Random().nextInt(values.length)];
    }
}
