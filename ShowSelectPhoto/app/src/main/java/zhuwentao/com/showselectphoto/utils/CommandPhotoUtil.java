package zhuwentao.com.showselectphoto.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import zhuwentao.com.showselectphoto.adapter.GridAdapter;
import zhuwentao.com.showselectphoto.custom.CustomScrollGridView;

/**
 * 需要上传的图片操作类
 * <p/>
 * Created by zhuwentao on 2016-09-08.
 */
public class CommandPhotoUtil {


    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 存放图片的容器
     */
    private CustomScrollGridView mGridView;

    /**
     * GridView适配器
     */
    private GridAdapter gridAdapter;

    /**
     * 图片选择
     */
    private PhotoSystemOrShoot selectPhoto;

    /**
     * 构造函数
     *
     * @param mContext    上下文
     * @param gridView    GridView容器
     * @param gridAdapter 图片Adapter
     * @param selectPhoto 图片选择弹窗
     */
    public CommandPhotoUtil(Context mContext, CustomScrollGridView gridView, GridAdapter gridAdapter, PhotoSystemOrShoot selectPhoto) {
        this.mContext = mContext;
        this.mGridView = gridView;
        this.gridAdapter = gridAdapter;
        this.selectPhoto = selectPhoto;
        addPhoto();
    }

    /**
     * 一些图片操作
     */
    public void addPhoto() {

        // 点击图片
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 如果单击时删除按钮处在显示状态，则隐藏它
                if (gridAdapter.getClearImgShow()) {
                    gridAdapter.setClearImgShow(false);
                    gridAdapter.notifyDataSetChanged();
                } else {
                    if (gridAdapter.getCount() - 1 == position) {
                        // 判断是否达到了可添加图片最大数
                        if (!(gridAdapter.imageItemData.size() == gridAdapter.imageSum)) {
                            selectPhoto.showPopupSelect(mGridView);
                        }
                    } else {
                        popupViewGridPhoto(position);
                    }
                }

            }
        });

        // 长按显示删除按钮
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!(position == gridAdapter.imageItemData.size())) {
                    // 如果删除按钮已经显示了则不再设置
                    if (!gridAdapter.getClearImgShow()) {
                        gridAdapter.setClearImgShow(true);
                        gridAdapter.notifyDataSetChanged();
                    }
                }
                // 返回true，停止事件向下传播
                return true;
            }
        });
    }


    /**
     * 添加图片到适配器中
     *
     * @param bmppath 要添加的图片
     */
    public void showGridPhoto(String bmppath) {
        gridAdapter.setImagePath(bmppath);
        mGridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 查看大图
     * @param position 要查看图片的位置
     */
    public void popupViewGridPhoto(int position) {
        String path = gridAdapter.imageItemData.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "image/*");
        mContext.startActivity(intent);
    }

}
