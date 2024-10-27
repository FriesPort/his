package com.example.bed.utils;

import com.example.bed.entity.Numbers;

public class NumberGenerate {

    /**
     Campus用0000四个数字表示院区
     Office用000三个数字表示科室
     Ward用00两个数字表示病区
     Floor用00两个数字表示楼层
     Room用00两个数字表示房间
     Bed用00两个数字表示床位
     //院区编号
     String campus="0000";
     //科室编号
     String office="000";
     //病区编号
     String ward="00";
     //楼层编号
     String floor="00";
     //房间编号
     String room="000";
     //病床号码
     String bednumber="00";
     */

    //根据床位所在信息构建一个床位编号生成器
    public static String nextNumbers(Numbers value) {
        //Campus的Numbers规则定义
        String campus = "0000";
        if (value.getCampus().equals("第一院区") ) {
            campus = "0001";
        } else if (value.getCampus().equals("第二院区")) {
            campus = "0002";
        } else if (value.getCampus().equals("第三院区")) {
            campus = "0003";
        } else if (value.getCampus().equals("第四院区")) {
            campus = "0004";
        } else if (value.getCampus().equals("第五院区")) {
            campus = "0005";
        }//----------------------------------------------------------------
        //Office的Numbers规则定义
        String office = "000";
        if (value.getOffice().equals("内科")) {
            office = "001";
        } else if (value.getOffice().equals("外科")) {
            office = "002";
        } else if (value.getOffice().equals("儿科")){
            office = "003";
        } else if (value.getOffice().equals("妇产科")) {
            office = "004";
        } else if (value.getOffice().equals("眼科")) {
            office = "005";
        } else if (value.getOffice().equals("口腔科")) {
            office = "006";
        } else if (value.getOffice().equals("耳鼻喉科")) {
            office = "007";
        } else if (value.getOffice().equals("皮肤科")) {
            office = "008";
        } else if (value.getOffice().equals("中医科")) {
            office = "009";
        } else if (value.getOffice().equals("精神科")) {
            office = "010";
        } else if (value.getOffice().equals("传染科")) {
            office = "011";
        } else if (value.getOffice().equals("康复科")) {
            office = "012";
        } else if (value.getOffice().equals("急诊科")) {
            office = "013";
        } else if (value.getOffice().equals("护理科")) {
            office = "014";
        }//----------------------------------------------------------------
        //Ward的Numbers规则定义
        String ward = "00";
        if (value.getWard().equals("一病区")) {
            ward = "01";
        } else if (value.getWard().equals("二病区")) {
            ward = "02";
        } else if (value.getWard().equals("三病区")) {
            ward = "03";
        } else if (value.getWard().equals("四病区")) {
            ward = "04";
        } else if (value.getWard().equals("五病区")) {
            ward = "05";
        } else if (value.getWard().equals("六病区")) {
            ward = "06";
        }//----------------------------------------------------------------
        //Floor的Numbers规则定义
        String floor = "00";
        if (value.getFloor().equals("一楼")) {
            floor = "01";
        } else if (value.getFloor().equals("二楼")) {
            floor = "02";
        } else if (value.getFloor().equals("三楼")) {
            floor = "03";
        } else if (value.getFloor().equals("四楼")) {
            floor = "04";
        } else if (value.getFloor().equals("五楼")) {
            floor = "05";
        } else if (value.getFloor().equals("六楼")) {
            floor = "06";
        } else if (value.getFloor().equals("七楼")) {
            floor = "07";
        } else if (value.getFloor().equals("八楼")) {
            floor = "08";
        } else if (value.getFloor().equals("九楼")) {
            floor = "09";
        }//----------------------------------------------------------------
        //Room的Numbers规则定义
        String room = "000";
        if (value.getRoom().equals("1号病房")){
            room = "001";
        } else if (value.getRoom().equals("2号病房")) {
            room = "002";
        } else if (value.getRoom().equals("3号病房")) {
            room = "003";
        } else if (value.getRoom().equals("4号病房")) {
            room = "004";
        } else if (value.getRoom().equals("5号病房")) {
            room = "005";
        } else if (value.getRoom().equals("6号病房")) {
            room = "006";
        } else if (value.getRoom().equals("7号病房")) {
            room = "007";
        } else if (value.getRoom().equals("8号病房")) {
            room = "008";
        } else if (value.getRoom().equals("9号病房")) {
            room = "009";
        }//--------------------------------------------------------------
        //Bed的Numbers规则定义
        String bednumber = "00";
        StringBuilder numbers;
        if (value.getBednumber().equals("1号床")) {
            bednumber = "01";
        } else if (value.getBednumber().equals("2号床")) {
            bednumber = "02";
        } else if (value.getBednumber().equals("3号床")) {
            bednumber = "03";
        } else if (value.getBednumber().equals("4号床")) {
            bednumber = "04";
        } else if (value.getBednumber().equals("5号床")) {
            bednumber = "05";
        } else if (value.getBednumber().equals("6号床")) {
            bednumber = "06";
        } else if (value.getBednumber().equals("7号床")) {
            bednumber = "07";
        } else if (value.getBednumber().equals("8号床")) {
            bednumber = "08";
        } else if (value.getBednumber().equals("9号床")) {
            bednumber = "09";
        }  //--------------------------------------------------------------
        //返回拼接好的字符串
        //根据规则��接��位编号
        //例如：0001001010101
        //拼接字符串
        numbers = new StringBuilder();
        numbers.append(campus).append(office).append(ward)
                .append(floor).append(room).append(bednumber);
        //返回拼接好的字符串
        return numbers.toString();
    }
    //根据传入的床位编号拆分编号的信息放在一个容器中返回
    public static String[] splitNumbers(String numbers){
        //检查传进来的numbers是否符合标准，用正则表达式检测
        if (!numbers.matches("^[0-9]{16}$")) {
            return null;
        }
        String[] regex = new String[6];
        regex[0] = numbers.substring(0,4);
        regex[1] = numbers.substring(4,7);
        regex[2] = numbers.substring(7,9);
        regex[3] = numbers.substring(9,11);
        regex[4] = numbers.substring(11,14);
        regex[5] = numbers.substring(14,16);
        //返回拆分好的字符串
        return regex;
    }
}

