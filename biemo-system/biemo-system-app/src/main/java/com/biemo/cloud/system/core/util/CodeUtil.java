package com.biemo.cloud.system.core.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 生成业务的编码
 *
 *
 * @Date 2019-09-25 16:37
 */
public class CodeUtil {

    /**
     * 生成业务编码时公司的前缀
     */
    private static final String COMPANY_CODE_PREFIX = "COMP";

    /**
     * 生成业务编码时部门的前缀
     */
    private static final String DEPT_CODE_PREFIX = "DEPT";

    /**
     * 生成业务编码时用户的前缀
     */
    private static final String USER_CODE_PREFIX = "USER";

    /**
     * 生成业务编码时职务的前缀
     */
    private static final String DUTY_CODE_PREFIX = "DUTY";

    /**
     * 获取企业的业务编码
     *
     *
     * @Date 2019/10/13 10:33
     */
    public static String getEntCode(String name) {
        return COMPANY_CODE_PREFIX + DateUtil.format(new Date(), "yyyyMMdd") + PinYin.getPinYinHeadChar(name);
    }

    /**
     * 获取部门的业务编码
     *
     *
     * @Date 2019/10/13 10:33
     */
    public static String getDeptCode(String name) {
        return DEPT_CODE_PREFIX + DateUtil.format(new Date(), "yyyyMMdd") + PinYin.getPinYinHeadChar(name);
    }

    /**
     * 获取人员的业务编码
     *
     *
     * @Date 2019/10/13 10:33
     */
    public static String getUserCode(String name, String sex, Date birthday) {
        return USER_CODE_PREFIX + DateUtil.format(new Date(), "yyyyMMdd") + PinYin.getPinYinHeadChar(name) + sex.toUpperCase() + DateUtil.format(birthday, "yyyyMMdd");
    }

    /**
     * 获取职务的业务编码
     *
     *
     * @Date 2019/10/13 10:33
     */
    public static String getDutyCode(String name) {
        return DUTY_CODE_PREFIX + DateUtil.format(new Date(), "yyyyMMdd") + PinYin.getPinYinHeadChar(name);
    }

    public static void main(String[] args) {
        System.out.println(getEntCode("北京公司"));
        System.out.println(getDeptCode("技术部"));
        System.out.println(getUserCode("张三", "M", DateUtil.parseDate("1993-01-01")));
    }

}
