package ir.hosseinrasti.app.jobjo.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Created by Hossein on 7/13/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD , ElementType.TYPE })
public @interface FragmentScoped {

}
