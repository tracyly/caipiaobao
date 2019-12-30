package com.fenghuang.caipiaobao.ui.home.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ Author  QinTian
 * @ Date  2019/12/29- 11:14
 * @ Describe
 */
public class GameList {


    /**
     * code : 1
     * msg :
     * time : 1577589317
     * typeList : {"1":"彩票","2":"红包","3":"真人","4":"棋牌"}
     * data : {"1":[{"type":1,"anchor_id":100005,"game_id":1,"name":"幸运飞艇","image":"http://cpbh5.com/uploads/20190930/259d0d8f95a2ba89794890a802438468.png","image_pc":"http://cpbh5.com/uploads/20191202/eb96347d71ab3cb4b3a201ee20a07663.jpg","live_status":1,"live_intro":"点击领取媳妇","online":1016,"lottery_id":9,"live_status_txt":"直播中"},{"type":1,"anchor_id":100047,"game_id":3,"name":"北京赛车","image":"http://cpbh5.com/uploads/20190930/5b177666b21236553f38f0db1b5874b4.png","image_pc":"http://cpbh5.com/uploads/20191202/6629a8450e76d36dceb50a3833cb137b.jpg","live_status":1,"live_intro":"Davis帅","online":766,"lottery_id":7,"live_status_txt":"直播中"},{"type":1,"anchor_id":100130,"game_id":5,"name":"澳洲幸运10","image":"http://cpbh5.com/uploads/20190930/91b658688e2ab5b2e1743c50d9702453.png","image_pc":"http://cpbh5.com/uploads/20191202/4e3131b0aad8473b4a13fabc370bfd6f.jpg","live_status":1,"live_intro":"一起来K歌","online":1643,"lottery_id":11,"live_status_txt":"直播中"},{"type":1,"anchor_id":100084,"game_id":6,"name":"澳洲幸运5","image":"http://cpbh5.com/uploads/20190930/46e57c9497f145fa718128655d7be57f.png","image_pc":"http://cpbh5.com/uploads/20191202/f06c800153e8dbf2be82b517d4f020ee.jpg","live_status":1,"live_intro":"长腿萝莉","online":676,"lottery_id":10,"live_status_txt":"直播中"},{"type":1,"anchor_id":100173,"game_id":4,"name":"重庆时时彩","image":"http://cpbh5.com/uploads/20190930/c68234c13858459ab7d7696db958164d.png","image_pc":"http://cpbh5.com/uploads/20191202/cf6ed6e32001ac0686378d48cad89e96.jpg","live_status":1,"live_intro":"学妹兔兔","online":814,"lottery_id":1,"live_status_txt":"直播中"},{"type":1,"anchor_id":100179,"game_id":12,"name":"香港彩","image":"http://cpbh5.com/uploads/20191202/4f2b61a4c82250bbf0b7b4fe71c0bf2f.png","image_pc":"http://cpbh5.com/uploads/20191202/9cef284b5a3b4279c03eefd5480d425c.jpg","live_status":0,"live_intro":"撒浪嘿呦~","online":1428,"lottery_id":8,"live_status_txt":"未开播"}],"2":[],"3":[{"type":3,"anchor_id":100109,"game_id":13,"name":"蜜桃成熟","image":"http://cpbh5.com/uploads/20191118/3fa37c30d5335b90165a0dec3d5fcf30.png","image_pc":"http://cpbh5.com/uploads/20191118/2e1a4a3730e637f938c2c68ad72dfe04.jpg","live_status":0,"live_intro":"如火热情","online":898,"lottery_id":null,"live_status_txt":"未开播"}],"4":[]}
     */

    private int code;
    private String msg;
    private int time;
    private TypeListBean typeList;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TypeListBean getTypeList() {
        return typeList;
    }

    public void setTypeList(TypeListBean typeList) {
        this.typeList = typeList;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class TypeListBean {
        /**
         * 1 : 彩票
         * 2 : 红包
         * 3 : 真人
         * 4 : 棋牌
         */

        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;
        @SerializedName("3")
        private String _$3;
        @SerializedName("4")
        private String _$4;

        public String get_$1() {
            return _$1;
        }

        public void set_$1(String _$1) {
            this._$1 = _$1;
        }

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }

        public String get_$3() {
            return _$3;
        }

        public void set_$3(String _$3) {
            this._$3 = _$3;
        }

        public String get_$4() {
            return _$4;
        }

        public void set_$4(String _$4) {
            this._$4 = _$4;
        }
    }

    public static class DataBean {
        @SerializedName("1")
        private List<_$1Bean> _$1;
        @SerializedName("2")
        private List<?> _$2;
        @SerializedName("3")
        private List<_$3Bean> _$3;
        @SerializedName("4")
        private List<?> _$4;

        public List<_$1Bean> get_$1() {
            return _$1;
        }

        public void set_$1(List<_$1Bean> _$1) {
            this._$1 = _$1;
        }

        public List<?> get_$2() {
            return _$2;
        }

        public void set_$2(List<?> _$2) {
            this._$2 = _$2;
        }

        public List<_$3Bean> get_$3() {
            return _$3;
        }

        public void set_$3(List<_$3Bean> _$3) {
            this._$3 = _$3;
        }

        public List<?> get_$4() {
            return _$4;
        }

        public void set_$4(List<?> _$4) {
            this._$4 = _$4;
        }

        public static class _$1Bean {
            /**
             * type : 1
             * anchor_id : 100005
             * game_id : 1
             * name : 幸运飞艇
             * image : http://cpbh5.com/uploads/20190930/259d0d8f95a2ba89794890a802438468.png
             * image_pc : http://cpbh5.com/uploads/20191202/eb96347d71ab3cb4b3a201ee20a07663.jpg
             * live_status : 1
             * live_intro : 点击领取媳妇
             * online : 1016
             * lottery_id : 9
             * live_status_txt : 直播中
             */

            private int type;
            private int anchor_id;
            private int game_id;
            private String name;
            private String image;
            private String image_pc;
            private int live_status;
            private String live_intro;
            private int online;
            private int lottery_id;
            private String live_status_txt;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAnchor_id() {
                return anchor_id;
            }

            public void setAnchor_id(int anchor_id) {
                this.anchor_id = anchor_id;
            }

            public int getGame_id() {
                return game_id;
            }

            public void setGame_id(int game_id) {
                this.game_id = game_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImage_pc() {
                return image_pc;
            }

            public void setImage_pc(String image_pc) {
                this.image_pc = image_pc;
            }

            public int getLive_status() {
                return live_status;
            }

            public void setLive_status(int live_status) {
                this.live_status = live_status;
            }

            public String getLive_intro() {
                return live_intro;
            }

            public void setLive_intro(String live_intro) {
                this.live_intro = live_intro;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getLottery_id() {
                return lottery_id;
            }

            public void setLottery_id(int lottery_id) {
                this.lottery_id = lottery_id;
            }

            public String getLive_status_txt() {
                return live_status_txt;
            }

            public void setLive_status_txt(String live_status_txt) {
                this.live_status_txt = live_status_txt;
            }
        }

        public static class _$3Bean {
            /**
             * type : 3
             * anchor_id : 100109
             * game_id : 13
             * name : 蜜桃成熟
             * image : http://cpbh5.com/uploads/20191118/3fa37c30d5335b90165a0dec3d5fcf30.png
             * image_pc : http://cpbh5.com/uploads/20191118/2e1a4a3730e637f938c2c68ad72dfe04.jpg
             * live_status : 0
             * live_intro : 如火热情
             * online : 898
             * lottery_id : null
             * live_status_txt : 未开播
             */

            private int type;
            private int anchor_id;
            private int game_id;
            private String name;
            private String image;
            private String image_pc;
            private int live_status;
            private String live_intro;
            private int online;
            private Object lottery_id;
            private String live_status_txt;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAnchor_id() {
                return anchor_id;
            }

            public void setAnchor_id(int anchor_id) {
                this.anchor_id = anchor_id;
            }

            public int getGame_id() {
                return game_id;
            }

            public void setGame_id(int game_id) {
                this.game_id = game_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getImage_pc() {
                return image_pc;
            }

            public void setImage_pc(String image_pc) {
                this.image_pc = image_pc;
            }

            public int getLive_status() {
                return live_status;
            }

            public void setLive_status(int live_status) {
                this.live_status = live_status;
            }

            public String getLive_intro() {
                return live_intro;
            }

            public void setLive_intro(String live_intro) {
                this.live_intro = live_intro;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public Object getLottery_id() {
                return lottery_id;
            }

            public void setLottery_id(Object lottery_id) {
                this.lottery_id = lottery_id;
            }

            public String getLive_status_txt() {
                return live_status_txt;
            }

            public void setLive_status_txt(String live_status_txt) {
                this.live_status_txt = live_status_txt;
            }
        }
    }
}
