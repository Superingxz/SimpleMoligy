package com.softgarden.baselibrary.utils;

import android.util.Log;

import com.alibaba.android.arouter.utils.TextUtils;
import com.softgarden.baselibrary.R;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc
 * Author feng
 * Date   2017/7/19.
 */

public class VerifyUtil {
    public static boolean checkListEmpty(List list, int resStr) {
        if (list == null || list.size() == 0) {
            ToastUtil.s(resStr);
            return true;
        }
        return false;
    }


    public static boolean checkEmpty(String content, int resStr) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.s(resStr);
            return true;
        }
        return false;
    }

    public static boolean checkPhone(String phone) {
        if (!phone.matches(RegularCons.REGEX_MOBILE_EXACT)) {
            ToastUtil.s(R.string.phone_format_not_correct);
            return false;
        }
        return true;
    }

    /**
     * 密码长度8-16位, 数字/英文/符号至少包含2种
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        boolean flag = true;
        if (password.length() < 6 || password.length() > 16) {
            flag = false;
        }

        int count = 0;
        if (password.matches(".*[0-9]+.*")) {
            Log.e("feng", "数字");
            count++;
        }

        if (password.matches(".*[a-zA-Z]+.*")) {
            Log.e("feng", "字母");
            count++;
        }

        if (password.matches(".*[^a-zA-Z_0-9]+.*")) {
            Log.e("feng", "符号");
            count++;
        }

        if (count < 2) {
            flag = false;
        }

        if (!flag) {
            ToastUtil.l(R.string.password_format);
        }
        return flag;
    }

    /**
     * 判断是否正确格式的身份证
     *
     * @param idCard
     * @return
     */
    public static boolean checkIDCard(String idCard) {
        if (idCard.matches("^(\\d){15}|(\\d{17}(\\d|x|X))$")) {
            return true;
        } else {
            ToastUtil.s("请输入正确的身份证号码");
            return false;
        }
    }

    /**
     * 判断是否正确的姓名
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        if (name.matches("[\\u4e00-\\u9fa5]{2,}")) {
            return true;
        } else {
            ToastUtil.s("请输入正确的姓名");
            return false;
        }
    }


    public static boolean checkEmail(String email) {
        if (email.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$")) {
            return true;
        } else {
            ToastUtil.s("请输入正确的邮箱");
            return false;
        }
    }

    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回0
     * 2.如果身份证长度不合法返回1
     * 3.如果第1-17位含有非数字的字符返回2
     * 4.如果第18位不是数字也不是x返回3
     * 5.如果身份证号的出生日期非法返回4
     *
     * @since 0.0.1
     */
    public static boolean checkIdCard(String idNum) {
        // 中国公民身份证格式：长度为15或18位，最后一位可以为字母
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        // 格式验证
        if (!idNumPattern.matcher(idNum).matches())
            return false;

        // 合法性验证

        int year = 0;
        int month = 0;
        int day = 0;

        if (idNum.length() == 15) {

            // 一代身份证

            System.out.println("一代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf("19" + birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));

            }

        } else if (idNum.length() == 18) {

            // 二代身份证

            System.out.println("二代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf(birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));
            }

        }

        // 年份判断，100年前至今

        Calendar cal = Calendar.getInstance();

        // 当前年份
        int currentYear = cal.get(Calendar.YEAR);

        if (year <= currentYear - 100 || year > currentYear)
            return false;

        // 月份判断
        if (month < 1 || month > 12)
            return false;

        // 日期判断

        // 计算月份天数

        int dayCount = 31;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayCount = 31;
                break;
            case 2:
                // 2月份判断是否为闰年
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    dayCount = 29;
                    break;
                } else {
                    dayCount = 28;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                dayCount = 30;
                break;
        }

        System.out.println(String.format("生日：%d年%d月%d日", year, month, day));

        System.out.println(month + "月份有：" + dayCount + "天");

        if (day < 1 || day > dayCount)
            return false;

        return true;
    }
}







