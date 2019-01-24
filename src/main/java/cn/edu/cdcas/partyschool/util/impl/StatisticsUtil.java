package cn.edu.cdcas.partyschool.util.impl;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 数据库中字段与显示字段的转换工具
 *     1：单选，2：多选，3：判断，4：填空，5：解答
 * @Date 2019/1/22 11:56
 * @Created by YR
 */
public class StatisticsUtil {
    final static private List<String> typeList=new ArrayList<String>();
    static {
        typeList.add(0,"无");
        typeList.add(1,"单选");
        typeList.add(2,"多选");
        typeList.add(3,"判断");
        typeList.add(4,"填空");
        typeList.add(5,"解答");
    }
    public static String getTypeString(int type)
    {
        return typeList.get(type);
    }
}
