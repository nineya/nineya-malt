package com.nineya.malt.compress;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/28
 */
public class AnnotationWipeTest {

    @Test
    public void commonMultiLine(){
        String code = "/**\n" +
            " * @author linsongwang\n" +
            " * @date 2020/8/28\n" +
            " */Test world.";
        Assert.assertEquals(AnnotationWipe.commonMultiLine(code), "Test world.");
    }

    @Test
    public void singleLine(){
        String code = "// 注释\nTest world.";
        Assert.assertEquals(AnnotationWipe.singleList(code), "\nTest world.");
    }
}
