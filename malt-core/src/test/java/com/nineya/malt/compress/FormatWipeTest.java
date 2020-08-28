package com.nineya.malt.compress;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/28
 */
public class FormatWipeTest {
    @Test
    public void multiSemicolon(){
        String code = "int a = 10;;;;";
        Assert.assertEquals(FormatWipe.multiSemicolon(code), "int a = 10;");
    }

    @Test
    public void lineTrim(){
        String code = "  Te \n st world.    ";
        Assert.assertEquals(FormatWipe.lineTrim(code), "Test world.");
    }
}
