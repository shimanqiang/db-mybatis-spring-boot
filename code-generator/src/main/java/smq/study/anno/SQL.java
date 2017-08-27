package smq.study.anno;

import java.lang.annotation.*;

/**
 * Created by smq on 2017/8/27.
 * sql description annotation
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface SQL {
    /**
     * table prefix
     *
     * @return
     */
    String tablePrefix() default "t_";

    /**
     * column prefix
     *
     * @return
     */
    String columnPrefix() default "f_";


    /**
     * charset
     *
     * @return
     */
    String charset() default "utf8";


    /**
     * description table
     *
     * @return
     */
    String desc() default "";

    /**
     * AUTO_INCREMENT
     *
     * @return
     */
    int autoIncrement() default 1;


    /**
     * db type
     *
     * @return
     */
    DB dbType() default DB.MYSQL;


    /**
     * db engine
     *
     * @return
     */
    DBEngine dbEngine() default DBEngine.MyISAM;


    enum DB {
        MYSQL, ORACLE, PostgreSQL, DB2
    }

    enum DBEngine {
        MyISAM, InnoDB
    }
}
