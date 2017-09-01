package com.xqd.meizhi.utils;

/**
 * Created by Administrator on 2017/9/1.
 */

public class CalulateTwoLanLon {

    private static final double EARTH_RADIUS = 6378.137;//地球半径,单位千米

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lat1      第一个纬度
     * @param lng1第一个经度
     * @param lat2第二个纬度
     * @param lng2第二个经度
     * @return 两个经纬度的距离
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;

    }

//    private String ConvertDistanceToLogLat(float distance, String logLatPtStr, double angle)
//    {
//        String logLat = null;
//        String[] temp_Arrary = logLatPtStr.Split(',');
//        double lng1 = ConvertLongiToDouble(temp_Arrary[0].Replace("(",""));
//        double lat1 = ConvertLongiToDouble(temp_Arrary[1].Replace(")",""));
//        double lon = lng1 + (distance * Math.Sin(angle* Math.PI / 180)) / (111 * Math.Cos(lat1 * Math.PI / 180));//将距离转换成经度的计算公式
//        double lat = lat1 + (distance * Math.Cos(angle* Math.PI / 180)) / 111;//将距离转换成纬度的计算公式
//        String logStr = ConvertLogLatToString(lon);
//        String latStr = ConvertLogLatToString(lat);
//        logLat = "(" + logStr + "," + latStr + ")";
//        return logLat;
//    }
//
//    //将double值转换成度分秒字符串
//    private String ConvertLogLatToString(double lon)
//    {
//        String resut = null;
//        String temp = lon.ToString();
//        String[] du = temp.Split('.');
//        resut += du[0] + "°";
//        double fen = (lon - Convert.ToDouble(du[0])) * 60;
//        String[] fen_Arrary = fen.ToString().Split('.');
//        resut += fen_Arrary[0] + "′";
//        if (fen_Arrary.Length > 1)
//        {
//            double second = Math.Round((fen - Convert.ToDouble(fen_Arrary[0])) * 60, 0);
//            resut += second.ToString() + "″";
//        }
//        return resut;
//    }

}
