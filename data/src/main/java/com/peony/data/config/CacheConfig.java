package com.peony.data.config;

import java.lang.annotation.*;

/**
 * Description: 缓存注解
 * Date: 2020年9月17日
 * Time: 下午4:55:16
 *
 * @author li.tiancheng
 * @version 1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheConfig {
    /**
     * 命名空间
     * 增删改方法可以加多个namespace用,分隔
     * 查询方法只能加一个namespace,加多个也没啥用
     *
     * @return
     */
    public String namespace();

    /**
     * @return Description: 生成缓存的key
     * Date: 2020年9月17日
     * Time: 下午4:55:37
     */
    public String key();

    /**
     * 失效时间,单位:秒
     *
     * @return
     */
    public int time();

    /**
     * Description: 清除缓存的命名空间
     *
     * @return Date: 2015年9月29日
     * Time: 下午4:56:29
     *
     */
    public boolean deleteNamespace();

    /**
     * Description: 是否用md5生成key,效率慢,只在参数中有list,map等集合导致key超过128位时才使用
     *
     * @return Date: 2015年9月29日
     * Time: 下午4:57:03
     *
     */
    public boolean useMD5();

    public int TIME_MONTH = 60 * 60 * 24 * 30;
    public int TIME_DAY = 60 * 60 * 24;
    public int TIME_HOUR = 60 * 60;
    public int TIME_MINUTE = 60;

    //共用命名空间
    public static final String NS_FUNCTION = "FunctionServiceImpl";
    public static final String NS_RESOURCE_GROUP = "ResourceGroupServiceImpl";
    public static final String NS_ROLE_PERMISSION = "RolePermissionConfig";
    public static final String NS_DATA_PERMISSION = "DataPermissionConfig";
    public static final String NS_COLUMN = "ColumnServiceImpl";
    public static final String NS_CHANNEL = NS_COLUMN;
    public static final String NS_WEBSITE = NS_COLUMN;
    public static final String NS_INFORMATION = "InformationServiceImpl";
    public static final String NS_GOVERNMENT_SEND = NS_INFORMATION;
    public static final String NS_REPLY = "ReplyServiceImpl";

    public static final String NS_ROLE_AND_USER = "RoleAndUser";
    public static final String NS_ROLE_AND_FUNCTION = "RoleAndFunction";
    public static final String NS_USER_AND_RESOURCE_GROUP = "UserAndResourceGroup";
    public static final String NS_RESOURCE_AND_RESOURCE_GROUP = "ResourceAndResourceGroup";
    public static final String NS_ORGANIZATION_AND_USER = "OrganizationAndUser";
    public static final String NS_AREA_AND_ORGANIZATION = "AreaAndOrganization";
    public static final String NS_COLUMN_AND_INFORMATION = "ColumnAndInformation";
}
