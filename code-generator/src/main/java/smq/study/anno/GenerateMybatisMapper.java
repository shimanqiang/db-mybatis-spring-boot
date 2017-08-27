package smq.study.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)/*class leave*/
//@Retention(RetentionPolicy.SOURCE)/*source scope*/
@Retention(RetentionPolicy.CLASS)/*class scope*/
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
