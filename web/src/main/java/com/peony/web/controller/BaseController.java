package com.peony.web.controller;

import com.peony.common.web.helper.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hk
 * @date 2019/10/24
 */
public class BaseController {

    AuthHelper authHelper;

    @Autowired
    public void setAuthHelper(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

}
