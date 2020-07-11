package am.android.gpscorrect;
/**
 * ��ͼ����ת�� google,baidu,gps
 * @author lw
 * @Time 2015��4��16��18:19:16
 * 
 * */

/******����ϵ˵��******/
//    WGS-84 - GPSӲ��(�ȸ����)            - Google Earth
//    BD-09  - �ٶȵ�ͼ                                                           - Baidu Map  
//    GCJ-02 - �ȸ��ͼ���ߵµ�ͼ����Ѷ��ͼ                   - Google Map,AMap,Tencent Map
/******����ϵ˵��******/


public class CoordinateConversion
{
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    
    private static final double pi = 3.14159265358979324;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    
    /**
     * gg_lat γ��
     * gg_lon ����
     * GCJ-02ת��BD-09
     * Google��ͼ��γ��ת�ٶȵ�ͼ��γ��
     * */
    public static Point gcj_bd_encrypt(double gg_lat, double gg_lon)
    {
        Point point=new Point();
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi); 
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        point.setLat(bd_lat);
        point.setLng(bd_lon);
        return point;
    }
    
    /**
     * wgLat γ��
     * wgLon ����
     * BD-09ת��GCJ-02
     * �ٶ�תgoogle
     * */
    public static Point bd_gcj_encrypt(double bd_lat, double bd_lon)
    {
        Point point=new Point();
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
        double theta =Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
        double gg_lon = z * Math.cos(theta);  
        double gg_lat = z * Math.sin(theta);  
        point.setLat(gg_lat);
        point.setLng(gg_lon);
        return point;
    }
    
    
    
    /**
     * wgLat γ��
     * wgLon ����
     * WGS-84 �� GCJ-02 ��ת������ GPS ��ƫ��
     * */
    public static Point wgs_gcj_encrypt(double wgLat, double wgLon) 
    {
        Point point=new Point();
        if (outOfChina(wgLat, wgLon)) 
        {
            point.setLat(wgLat);
            point.setLng(wgLon);
            return point;
        }
        
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double lat = wgLat + dLat;
        double lon = wgLon + dLon;
        point.setLat(lat);
        point.setLng(lon);
        return point;
    }
    
    
    /**
     * wgLat γ��
     * wgLon ����
     * GCJ-02 �� WGS-84 ��ת������ GPS ��ƫ��
     * */
    public static Point gcj_wgs_encrypt(double gcjLat,double  gcjLon)
    {
      
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta;
        double dLon = initDelta;

        double mLat = gcjLat - dLat;
        double mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat;
        double pLon = gcjLon + dLon;

        double wgsLat;
        double wgsLon;
        int i = 0;

        while (true)
        {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;

            Point tmp = wgs_gcj_encrypt(wgsLat, wgsLon);

            dLat = tmp.getLat() - gcjLat;
            dLon = tmp.getLng() - gcjLon;

            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
            {
                break;
            }

            if (dLat > 0)
            {
                pLat = wgsLat;
            }
            else
            {
                mLat = wgsLat;
            }

            if (dLon > 0)
            {
                pLon = wgsLon;
            }
            else
            {
                mLon = wgsLon;
            }

            if (++i > 10000)
            {
                break;
            } 
        }

        Point wgsPoint = new Point(wgsLat,wgsLon);

        return wgsPoint;

    }


    /**
     * wgLat γ��
     * wgLon ����
     * BD-02 �� WGS-84 ��ת������ GPS ��ƫ��
     * */
    public static Point bd_wgs_encrypt(double bdLat, double bdLon)
    {
        Point ggPoint = bd_gcj_encrypt(bdLat,bdLon);

        double gcjLat = ggPoint.getLat();
        double gcjLon = ggPoint.getLng();

        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta;
        double dLon = initDelta;

        double mLat = gcjLat - dLat;
        double mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat;
        double pLon = gcjLon + dLon;

        double wgsLat;
        double wgsLon;
        int i = 0;

        while (true)
        {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;

            Point tmp = wgs_gcj_encrypt(wgsLat, wgsLon);

            dLat = tmp.getLat() - gcjLat;
            dLon = tmp.getLng() - gcjLon;

            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
            {
                break;
            }

            if (dLat > 0)
            {
                pLat = wgsLat;
            }
            else
            {
                mLat = wgsLat;
            }

            if (dLon > 0)
            {
                pLon = wgsLon;
            }
            else
            {
                mLon = wgsLon;
            }

            if (++i > 10000)
            {
                break;
            }
        }

        Point wgsPoint = new Point(wgsLat, wgsLon);
        return wgsPoint;

    }
    
    
    
    
    
    public static void transform(double wgLat, double wgLon, double[] latlng) 
    {
        if (outOfChina(wgLat, wgLon))
        {
            latlng[0] = wgLat;
            latlng[1] = wgLon;
            return;
        }
        
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        latlng[0] = wgLat + dLat;
        latlng[1] = wgLon + dLon;
    }

    private static boolean outOfChina(double lat, double lon) 
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y)
    {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
}