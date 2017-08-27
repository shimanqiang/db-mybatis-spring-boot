package smq.study.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)/*class leave*/
@Retention(RetentionPolicy.CLASS)/*source scope*/
@Documented
@Inherited
public @interface GenerateMybatisMapper {
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
}
