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
public @interface PrimaryKey {
}
