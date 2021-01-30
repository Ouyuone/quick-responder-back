package tech.ouyu.quickResponder.back.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-21 14:21
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
public class SchoolTimeVo implements Comparable<SchoolTimeVo>{
    private String week;
    private String desc;
    private List<Time> amTime=new ArrayList<>();
    private List<Time> pmTime=new ArrayList<>();
    private List<Time> evTime=new ArrayList<>();
    public void addAmTime(String severalLesson,String realSeveralLesson,boolean isChecked){
        amTime.add(new Time(realSeveralLesson,severalLesson,isChecked,0));
    }
    public void addPmTime(String severalLesson,String realSeveralLesson,boolean isChecked){
        pmTime.add(new Time(realSeveralLesson,severalLesson,isChecked,0));
    }
    public void addEvTime(String severalLesson,String realSeveralLesson,boolean isChecked){
        evTime.add(new Time(realSeveralLesson,severalLesson,isChecked,0));
    }

    @Override
    public int compareTo(SchoolTimeVo o) {
        return this.week.compareTo(o.getWeek());
    }

    enum Week{
        Mon("1","星期一"),
        Tues("2","星期二"),
        Wed("3","星期三"),
        Thurs("4","星期四"),
        Fri("5","星期五");
        private String week;
        private String desc;

        Week(String week, String desc) {
            this.week = week;
            this.desc = desc;
        }
        public static String findValue(String week){
            Week[] weeks = Week.values();
            for (Week week1 : weeks) {
                if(week1.week.equals(week)){
                    return week1.desc;
                }
            }
            return "";
        }
        public String getDesc() {
            return desc;
        }
    }
    public static String pullWeek(String week){
        return Week.findValue(week);
    }
    public void addTime(String time,String severalLesson,String realSeveralLesson,boolean isChecked){
        if(time.equals("AM")){
            addAmTime(severalLesson,realSeveralLesson,isChecked);
        }else if(time.equals("PM")){
            addPmTime(severalLesson,realSeveralLesson,isChecked);
        }else{
            addEvTime(severalLesson,realSeveralLesson,isChecked);
        }
    }
}

