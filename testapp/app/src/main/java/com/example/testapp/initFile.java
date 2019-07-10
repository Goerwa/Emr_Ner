//package com.example.testapp;
//
///**
// * 将数据库拷贝到相应目录
// */
//private void initFile() {
//        //判断数据库是否拷贝到相应的目录下
//        if (new File(DB_PATH + DB_NAME).exists() == false) {
//        File dir = new File(DB_PATH);
//        if (!dir.exists()) {
//        dir.mkdir();
//        }
//
//        //复制文件
//        try {
//        InputStream is = getBaseContext().getAssets().open(DB_NAME);
//        OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
//
//        //用来复制文件
//        byte[] buffer = new byte[1024];
//        //保存已经复制的长度
//        int length;
//
//        //开始复制
//        while ((length = is.read(buffer)) > 0) {
//        os.write(buffer, 0, length);
//        }
//
//        //刷新
//        os.flush();
//        //关闭
//        os.close();
//        is.close();
//
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//
//        }
//}
