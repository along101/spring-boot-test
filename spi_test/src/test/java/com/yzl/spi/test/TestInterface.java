package com.yzl.spi.test;

import com.yzl.spi.Scope;
import com.yzl.spi.Spi;

/**
 * Created by along on 2017/12/4.
 */
@Spi(scope = Scope.PROTOTYPE)
public interface TestInterface {
}
