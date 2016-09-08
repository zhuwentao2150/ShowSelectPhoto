package zhuwentao.com.showselectphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import zhuwentao.com.showselectphoto.adapter.GridAdapter;
import zhuwentao.com.showselectphoto.custom.CustomScrollGridView;
import zhuwentao.com.showselectphoto.utils.CommandPhotoUtil;
import zhuwentao.com.showselectphoto.utils.PhotoSystemOrShoot;

public class MainActivity extends AppCompatActivity {

    /**
     * 存放图片的容器
     */
    private CustomScrollGridView mGridView;

    /**
     * GridView适配器
     */
    private GridAdapter gridAdapter;

    /**
     * 管理图片操作
     */
    private CommandPhotoUtil commandPhoto;

    /**
     * 选择图片来源
     */
    private PhotoSystemOrShoot selectPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        addPlus();

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mGridView = (CustomScrollGridView) findViewById(R.id.gv_all_photo);
    }

    /**
     * 实例化组件
     */
    private void addPlus() {
        gridAdapter = new GridAdapter(MainActivity.this, 12);
        mGridView.setAdapter(gridAdapter);

        // 选择图片获取途径
        selectPhoto = new PhotoSystemOrShoot(MainActivity.this) {
            @Override
            public void onStartActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }
        };
        commandPhoto = new CommandPhotoUtil(MainActivity.this, mGridView, gridAdapter, selectPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 获取照片返回
        if (selectPhoto != null) {
            String photoPath = selectPhoto.getPhotoResultPath(requestCode, resultCode, data);
            if (!TextUtils.isEmpty(photoPath)) {
                commandPhoto.showGridPhoto(photoPath);
            }
        }
    }
}
