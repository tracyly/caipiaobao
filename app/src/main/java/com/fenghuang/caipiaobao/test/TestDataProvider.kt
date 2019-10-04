package com.fenghuang.caipiaobao.test

/**
 * @author Pinger
 * @since 2019/1/14 14:51
 */
object TestDataProvider {

    fun providerTestBeanList(): ArrayList<TestBean> {
        return arrayListOf(
                TestBean("我是测试标题", "我是测试内容", "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg"),
                TestBean("我是测试标题", "我是测试内容", "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg"),
                TestBean("我是测试标题", "我是测试内容", "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg"),
                TestBean("我是测试标题", "我是测试内容", "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg"),
                TestBean("我是测试标题", "我是测试内容", "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg")
        )
    }

    fun providerImage(): String {
        return "http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg"
    }

    fun providerLogo(): String {
        return "http://img.cndesign.com/upload/news/20180921/7c3d2a0710844809aae008ab330accc6.jpg"
    }

}