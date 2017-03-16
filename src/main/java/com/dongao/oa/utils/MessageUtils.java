package com.dongao.oa.utils;

import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.Message;
import com.dongao.oa.pojo.PositionUserOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yangjie on 2016/9/30.
 */
@Component
public class MessageUtils {
    private static SystemSettings systemSettings;

    public SystemSettings getSystemSettings() {
        return systemSettings;
    }
    @Autowired
    public void setSystemSettings(SystemSettings systemSettings) {
        MessageUtils.systemSettings = systemSettings;
    }

}
