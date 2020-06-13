package com.nineya.malt.compress;

import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/6/14 4:34
 */
public class AnnotationWipeTest {
    @Test
    public void css(){
        AnnotationWipe annotationWipe = new AnnotationWipe();
        System.out.println(annotationWipe.css("asdasdsd /** */da sd"));
    }
}
