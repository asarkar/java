package name.abhijitsarkar.java.java8impatient.miscellaneous;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(TestCases.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCase {
    int param();

    int expected();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestCases {
    TestCase[] value();
}
