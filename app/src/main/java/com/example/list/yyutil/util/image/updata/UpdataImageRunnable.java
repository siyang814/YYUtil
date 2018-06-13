package com.example.list.yyutil.util.image.updata;

import com.example.list.yyutil.util.image.updata.model.ImageEnty;
import com.example.list.yyutil.util.internet.CallBack;
import com.example.list.yyutil.util.internet.MyTask;

import java.util.HashMap;

/**
 * Created by yy on 2018/5/17.
 */

public class UpdataImageRunnable implements Runnable {

    private ImageEnty model;
    private String userId;
//    private int count = 0;


    public UpdataImageRunnable(ImageEnty model, String userId ) {
        this.model = model;
        this.userId = userId;
    }

    @Override
    public void run() {

        try {

            HashMap<String, Object> map = new HashMap<>();
            map.put("user_id", userId);
            map.put("thumb", model.getImageInfoString());
            map.put("ext", model.getImgName());

            new OkhttpSendImage().PostImage(model.url, map, new DynamicUpdataModel(), new CallBack() {

                @Override
                public void handler(MyTask mTask) {
                    DynamicUpdataModel info = (DynamicUpdataModel) mTask.getResultModel();
                    if ( info == null )
                    {
                        model.setERROR();
                        return;
                    }
//                    count += 1;
//                    Common.log1("count="+count);
                    model.setImageUrl(info.fileurl);
                    model.setOK();

                }

                @Override
                public void handlerError(MyTask mTask) {
                    super.handlerError(mTask);
                    model.setERROR();
                }

                @Override
                public void handlerCancel(MyTask mTask) {
                    super.handlerCancel(mTask);
                    model.setERROR();
                }
            });



//            MyRequestManager.getInstance().requestPost(C.UPDATA_IMAGE_LIST, map, new DynamicUpdataModel(), );

        }catch ( Exception e)
        {
            model.setERROR();
        }

    }




}
