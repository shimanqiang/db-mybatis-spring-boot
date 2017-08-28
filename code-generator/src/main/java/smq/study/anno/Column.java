package smq.study.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by smq on 2017/8/27.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Column {
    /**
     * 列名称，覆盖@SQL注解
     * ""空字符串表示无效
     *
     * @return
     */
    String name() default "";

    /**
     * 类型
     *
     * @return
     */
    String type() default "";

    /**
     * 长度
     *
     * @return
     */
    int len() default 0;

    /**
     * 主键
     *
     * @return
     */
    boolean primaryKey() default false;


    /**
     * 默认值
     *
     * @return
     */
    String defaultValue() default "";


    /**
     * 是否为空
     *
     * @return
     */
    boolean notNull() default false;

    /**
     * smq列注释
     *
     * @return
     */
    String comment() default "";
}
