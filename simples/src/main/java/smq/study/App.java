package smq.study;

import smq.study.anno.Column;
import smq.study.anno.GenerateMybatisMapper;
import smq.study.anno.SQL;

import java.util.Date;

/**
 * Hello world!
 */
//@GenerateMybatisMapper
@SQL(columnPrefix = "a_")
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    @Column(primaryKey = true)
    Integer id;

    @Column(defaultValue = "tom",comment = "名字",type = "char" ,len = 200)
    private String name;

    @Column(notNull = true)
    private int ageUper;

    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
