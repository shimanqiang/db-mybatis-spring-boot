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
     * 默认值
     *
     * @return
     */
    String defaultValue() default "";

    /**
     * smq列注释
     *
     * @return
     */
    String desc() default "";
}
