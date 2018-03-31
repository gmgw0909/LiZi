package com.fancy.learncenter.bean;

import java.util.List;

/**
 * Created by Hyy on 2016/9/18.
 */
public class Student {


        private List<AdvertBean> advert;

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class AdvertBean {
            /**
             * advertInfoId : 23
             * bannerType : 3
             * bizCode : RoastingClubBanner
             * coverImg : http://img1.fancyedu.com//flc/formal/2017/11/BNVGSpRQEJ_1509706128.png
             * coverType : 1
             * endTime : 2018-02-11 18:48:03
             * extraAttributes :
             * param : type=Mall&URL=http://mall.fancyedu.com/rct/videolist
             * rank : 15
             * sort : 0
             * startTime : 2017-11-03 18:48:03
             * status : null
             * title : 小小科学家视频
             * trackingKey : ADVERTRoastingClubBanner_23
             * type : 1
             */

            private String advertInfoId;
            private String bannerType;
            private String bizCode;
            private String coverImg;
            private String coverType;
            private String endTime;
            private String extraAttributes;
            private String param;
            private String rank;
            private String sort;
            private String startTime;
            private Object status;
            private String title;
            private String trackingKey;
            private String type;

            public String getAdvertInfoId() {
                return advertInfoId;
            }

            public void setAdvertInfoId(String advertInfoId) {
                this.advertInfoId = advertInfoId;
            }

            public String getBannerType() {
                return bannerType;
            }

            public void setBannerType(String bannerType) {
                this.bannerType = bannerType;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getCoverImg() {
                return coverImg;
            }

            public void setCoverImg(String coverImg) {
                this.coverImg = coverImg;
            }

            public String getCoverType() {
                return coverType;
            }

            public void setCoverType(String coverType) {
                this.coverType = coverType;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getExtraAttributes() {
                return extraAttributes;
            }

            public void setExtraAttributes(String extraAttributes) {
                this.extraAttributes = extraAttributes;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTrackingKey() {
                return trackingKey;
            }

            public void setTrackingKey(String trackingKey) {
                this.trackingKey = trackingKey;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

}
