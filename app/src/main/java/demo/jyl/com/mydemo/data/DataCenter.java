package demo.jyl.com.mydemo.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import demo.jyl.com.mydemo.R;
import demo.jyl.com.mydemo.model.Comment;
import demo.jyl.com.mydemo.model.Dynamic;

/**
 * �������ģ��洢��ȡ����
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
     * ͷ��ͼƬ��ַ
     * @return
     */
    private String[] getLogoImages(){
        return context.getResources().getStringArray(R.array.user_logo);
    }

    /**
     * ��ȡ��̬ͼƬ
     * @return
     */
    private String[] getDynamicImages(){
        return context.getResources().getStringArray(R.array.images_array);
    }

    /**��������*/
    private String[] getComments(){
        return context.getResources().getStringArray(R.array.comment_array);
    }

    /**�û���*/
    private String[] getUserName(){
        return  context.getResources().getStringArray(R.array.user_name);
    }

    /**��̬�б�����*/
    public List<Dynamic> getDynamicList(Context context){
        this.context = context;
        // ͷ��
        String[] logoUrl = DataCenter.getInstance().getLogoImages();
        // ��̬ͼƬ
        String[] imags = DataCenter.getInstance().getDynamicImages();

        ArrayList<Dynamic> dynamics = new ArrayList<Dynamic>();
        for(int i = 0; i < 20; i ++){
            Dynamic dynamic = new Dynamic();
            dynamic.setLogoUrl(getRandomValue(logoUrl));// ���ȡһ��ͼƬ
            dynamic.setName(getRandomValue(getUserName()));//�������һ������

            // ͼƬGridView����
            List<String> imageList = new ArrayList<String>();
            for(int j = 0; j < new Random().nextInt(imags.length) + 1; j ++ ){
                imageList.add(getRandomValue(imags));// ���ȡһ��ͼƬ
            }

            // ����ListView����
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

    /**�����ȡ�����е�һ��ֵ*/
    public String getRandomValue(String[] values){
        return values[new Random().nextInt(values.length)];
    }
}
